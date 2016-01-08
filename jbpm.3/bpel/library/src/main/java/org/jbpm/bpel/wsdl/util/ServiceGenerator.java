/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the JBPM BPEL PUBLIC LICENSE AGREEMENT as
 * published by JBoss Inc.; either version 1.0 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.jbpm.bpel.wsdl.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Import;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPFault;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.wsdl.Constants;
import com.ibm.wsdl.extensions.soap.SOAPConstants;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.def.BpelVisitorSupport;
import org.jbpm.bpel.def.ImportsDefinition;
import org.jbpm.bpel.def.Scope;
import org.jbpm.bpel.integration.client.SoapBindConstants;
import org.jbpm.bpel.integration.def.PartnerLinkDefinition;
import org.jbpm.bpel.wsdl.PartnerLinkType.Role;
import org.jbpm.bpel.xml.DefaultProblemHandler;
import org.jbpm.bpel.xml.ProblemHandler;
import org.jbpm.jpdl.xml.Problem;

/**
 * @author Alejandro Guízar
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:06:10 $
 */
public class ServiceGenerator {

  private String outputDirectory;
  private String serviceFile = "service.wsdl";
  private String bindingFile = "binding.wsdl";

  private ProblemHandler problemHandler;

  private Set writtenInterfaceFiles = new HashSet();

  private static final String ADDRESS_LOCATION_URI = "REPLACE_WITH_ACTUAL_URI";
  static final Log log = LogFactory.getLog(ServiceGenerator.class);

  /**
   * Gets the directory where to place output files.
   * @return the directory pathname
   */
  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }

  /**
   * Gets the binding file name, relative to the output directory.
   * @return the file pathname
   */
  public String getBindingFile() {
    return bindingFile;
  }

  public void setBindingFile(String bindingFile) {
    this.bindingFile = bindingFile;
  }

  /**
   * Gets the service file name, relative to the output directory.
   * @return the file pathname
   */
  public String getServiceFile() {
    return serviceFile;
  }

  public void setServiceFile(String serviceFile) {
    this.serviceFile = serviceFile;
  }

  public ProblemHandler getProblemHandler() {
    if (problemHandler == null) {
      problemHandler = new DefaultProblemHandler();
    }
    return problemHandler;
  }

  public void setProblemHandler(ProblemHandler problemHandler) {
    this.problemHandler = problemHandler;
  }

  public void generatePortComponents(BpelDefinition process) {
    writtenInterfaceFiles.clear();
    // generate the binding and service documents
    PortBuilder portBuilder = new PortBuilder();
    portBuilder.visit(process);
    Map bindingDefinitions = portBuilder.getBindingDefinitions();
    Definition serviceDefinition = portBuilder.getServiceDefinition();
    // determine the base name and extension for binding files
    int dotIndex = bindingFile.lastIndexOf('.');
    String base = bindingFile.substring(0, dotIndex);
    String extension = bindingFile.substring(dotIndex + 1);
    // write the binding files
    WSDLWriter writer = WsdlUtil.getFactory().newWSDLWriter();
    Iterator it = bindingDefinitions.values().iterator();
    for (int i = 1; it.hasNext(); i++) {
      Definition bindingDefinition = (Definition) it.next();
      String location = base + i + '.' + extension;
      // import the binding definition in the service definition
      Import bindingImport = serviceDefinition.createImport();
      bindingImport.setDefinition(bindingDefinition);
      bindingImport.setNamespaceURI(bindingDefinition.getTargetNamespace());
      bindingImport.setLocationURI(location);
      serviceDefinition.addImport(bindingImport);
      // write the binding wsdl to the output dir
      try {
        WsdlUtil.writeFile(writer, bindingDefinition, new File(outputDirectory,
            location));
        log.info("wrote binding definition: " + location);
      }
      catch (WSDLException e) {
        Problem problem = new Problem(Problem.LEVEL_ERROR,
            "could not write binding wsdl: " + location, e);
        getProblemHandler().add(problem);
        return;
      }
    }
    // write the service wsdl to the output dir
    try {
      WsdlUtil.writeFile(writer, serviceDefinition, new File(outputDirectory,
          serviceFile));
      log.info("wrote service definition: " + serviceFile);
    }
    catch (WSDLException e) {
      Problem problem = new Problem(Problem.LEVEL_ERROR,
          "could not write service wsdl: " + serviceFile, e);
      getProblemHandler().add(problem);
    }
  }

  protected Definition createDefinition(String targetNamespace) {
    Definition def = WsdlUtil.getFactory().newDefinition();
    def.setTargetNamespace(targetNamespace);
    def.addNamespace("tns", targetNamespace);
    def.addNamespace("soap", SOAPConstants.NS_URI_SOAP);
    def.addNamespace(null, Constants.NS_URI_WSDL);
    return def;
  }

  protected Definition generateServiceDefinition(String processNamespace) {
    return createDefinition(processNamespace);
  }

  protected String generateServiceLocalName(String processName) {
    return processName + "Service";
  }

  protected Definition generateBindingDefinition(QName portTypeName,
      ImportsDefinition imports, Map bindingDefinitions) throws WSDLException {
    String targetNamespace = portTypeName.getNamespaceURI();
    // retrieve a binding wsdl for the port type namespace
    Definition bindingDefinition = (Definition) bindingDefinitions.get(targetNamespace);
    if (bindingDefinition == null) {
      // create a definition to contain the binding
      bindingDefinition = createDefinition(targetNamespace);
      bindingDefinitions.put(targetNamespace, bindingDefinition);
    }
    // get the import that declares the port type
    org.jbpm.bpel.def.Import processImport = imports.getDeclaringImport(portTypeName,
        Constants.Q_ELEM_PORT_TYPE);
    if (processImport == null) {
      Problem problem = new Problem(Problem.LEVEL_ERROR,
          "declaring import not found: portType=" + portTypeName);
      getProblemHandler().add(problem);
    }
    else if (!containsInterfaceImport(bindingDefinition, processImport)) {
      // import the interface definition in the binding definition
      Import interfaceImport = generateInterfaceImport(bindingDefinition,
          processImport);
      bindingDefinition.addImport(interfaceImport);
    }
    return bindingDefinition;
  }

  protected boolean containsInterfaceImport(Definition def,
      org.jbpm.bpel.def.Import processImport) {
    List interfaceImports = def.getImports(processImport.getNamespace());
    if (interfaceImports != null) {
      String location = processImport.getLocation();
      Iterator interfaceImportIt = interfaceImports.iterator();
      while (interfaceImportIt.hasNext()) {
        Import interfaceImport = (Import) interfaceImportIt.next();
        if (interfaceImport.getLocationURI().equals(location)) return true;
      }
    }
    return false;
  }

  protected Import generateInterfaceImport(Definition def,
      org.jbpm.bpel.def.Import processImport) throws WSDLException {
    // create import and set its properties
    Import interfaceImport = def.createImport();
    interfaceImport.setNamespaceURI(processImport.getNamespace());
    interfaceImport.setLocationURI(processImport.getLocation());
    interfaceImport.setDefinition((Definition) processImport.getDocument());
    // write definition, if read from a relative location
    writeImportedDefinition(outputDirectory, interfaceImport);
    return interfaceImport;
  }

  protected void writeImportedDefinition(String baseDirectory, Import imp)
      throws WSDLException {
    String locationURI = imp.getLocationURI();
    // easy way out: location is absolute, so there's no need to write a copy
    if (URI.create(locationURI).isAbsolute()) return;
    // write the interface wsdl file to the output directory
    WSDLWriter writer = WsdlUtil.getFactory().newWSDLWriter();
    Definition interfaceDefinition = imp.getDefinition();
    File interfaceFile = new File(baseDirectory, locationURI);
    if (!writtenInterfaceFiles.contains(interfaceFile)) {
      writtenInterfaceFiles.add(interfaceFile);
      WsdlUtil.writeFile(writer, interfaceDefinition, interfaceFile);
      log.info("wrote interface definition: locationURI=" + locationURI
          + ", basePath=" + baseDirectory);
    }
    // now deal with imported documents
    baseDirectory = interfaceFile.getParent();
    Iterator importListIt = interfaceDefinition.getImports()
        .values()
        .iterator();
    while (importListIt.hasNext()) {
      List importList = (List) importListIt.next();
      Iterator importIt = importList.iterator();
      while (importIt.hasNext()) {
        imp = (Import) importIt.next();
        writeImportedDefinition(baseDirectory, imp);
      }
    }
  }

  protected String generateBindingLocalName(String portTypeLocalName) {
    return portTypeLocalName + "Binding";
  }

  protected Binding generateBinding(Definition def, PortType portType)
      throws WSDLException {
    // wsdl binding
    Binding binding = def.createBinding();
    binding.setPortType(portType);
    // the binding is fully specified, mark it as defined
    binding.setUndefined(false);
    // soap binding
    ExtensionRegistry extRegistry = def.getExtensionRegistry();
    SOAPBinding soapBinding = (SOAPBinding) extRegistry.createExtension(Binding.class,
        SOAPConstants.Q_ELEM_SOAP_BINDING);
    soapBinding.setStyle(SoapBindConstants.RPC_STYLE);
    soapBinding.setTransportURI(SoapBindConstants.HTTP_TRANSPORT_URI);
    binding.addExtensibilityElement(soapBinding);
    // operations
    Iterator operationIt = portType.getOperations().iterator();
    while (operationIt.hasNext()) {
      Operation operation = (Operation) operationIt.next();
      BindingOperation bindingOperation = generateBindingOperation(def,
          operation);
      binding.addBindingOperation(bindingOperation);
    }
    return binding;
  }

  protected BindingOperation generateBindingOperation(Definition def,
      Operation operation) throws WSDLException {
    // binding operation
    BindingOperation bindOperation = def.createBindingOperation();
    bindOperation.setOperation(operation);
    bindOperation.setName(operation.getName());
    // soap operation
    SOAPOperation soapOper = (SOAPOperation) def.getExtensionRegistry()
        .createExtension(BindingOperation.class,
            SOAPConstants.Q_ELEM_SOAP_OPERATION);
    soapOper.setSoapActionURI(generateSoapAction(def, operation));
    bindOperation.addExtensibilityElement(soapOper);
    // binding input
    BindingInput bindInput = generateBindingInput(def, operation);
    bindOperation.setBindingInput(bindInput);
    // request-response operations have an output and zero or more faults
    if (operation.getStyle().equals(OperationType.REQUEST_RESPONSE)) {
      BindingOutput bindOutput = generateBindingOutput(def, operation);
      bindOperation.setBindingOutput(bindOutput);
      // faults
      Iterator faultIt = operation.getFaults().values().iterator();
      while (faultIt.hasNext()) {
        Fault fault = (Fault) faultIt.next();
        BindingFault bindFault = generateBindingFault(def, fault);
        bindOperation.addBindingFault(bindFault);
      }
    }
    return bindOperation;
  }

  protected String generateSoapAction(Definition def, Operation operation) {
    String soapAction;
    try {
      // target namespace
      String targetNamespace = def.getTargetNamespace();
      URI targetNamespaceURI = new URI(targetNamespace);
      // operation name
      String operationName = operation.getName();
      URI operationURI = new URI(operationName);
      // is this a URN (as opposed to a URL)?
      if (targetNamespaceURI.isOpaque()) {
        // no way to resolve against an URN, so just append the operation name
        soapAction = targetNamespace + ':' + operationName;
      }
      else {
        soapAction = targetNamespaceURI.resolve(operationURI).toString();
      }
    }
    catch (URISyntaxException e) {
      // either the target namespace or the operation name is not a URI -
      // generate an empty action
      soapAction = "";
    }
    return soapAction;
  }

  protected BindingInput generateBindingInput(Definition def,
      Operation operation) throws WSDLException {
    BindingInput bindInput = def.createBindingInput();
    // input soap body
    SOAPBody soapBody = (SOAPBody) def.getExtensionRegistry()
        .createExtension(BindingInput.class, SOAPConstants.Q_ELEM_SOAP_BODY);
    soapBody.setUse(SoapBindConstants.LITERAL_USE);
    soapBody.setNamespaceURI(generateSoapBodyNamespace(def, operation));
    bindInput.addExtensibilityElement(soapBody);
    return bindInput;
  }

  protected BindingOutput generateBindingOutput(Definition def,
      Operation operation) throws WSDLException {
    // binding output
    BindingOutput bindOutput = def.createBindingOutput();
    // output soap body
    SOAPBody soapBody = (SOAPBody) def.getExtensionRegistry()
        .createExtension(BindingOutput.class, SOAPConstants.Q_ELEM_SOAP_BODY);
    soapBody.setUse(SoapBindConstants.LITERAL_USE);
    soapBody.setNamespaceURI(generateSoapBodyNamespace(def, operation));
    bindOutput.addExtensibilityElement(soapBody);
    return bindOutput;
  }

  protected BindingFault generateBindingFault(Definition def, Fault fault)
      throws WSDLException {
    String faultName = fault.getName();
    // binding fault
    BindingFault bindFault = def.createBindingFault();
    bindFault.setName(faultName);
    // soap fault
    SOAPFault soapFault = (SOAPFault) def.getExtensionRegistry()
        .createExtension(BindingFault.class, SOAPConstants.Q_ELEM_SOAP_FAULT);
    soapFault.setName(faultName);
    soapFault.setUse(SoapBindConstants.LITERAL_USE);
    bindFault.addExtensibilityElement(soapFault);
    return bindFault;
  }

  protected String generateSoapBodyNamespace(Definition def, Operation operation) {
    return def.getTargetNamespace();
  }

  protected String generatePortName(Service service, String partnerLinkName) {
    String portName = partnerLinkName + "Port";
    Map ports = service.getPorts();
    // the port name might already be taken
    if (ports.containsKey(portName)) {
      portName = generateName(portName, ports.keySet());
    }
    return portName;
  }

  protected Port generatePort(Definition def, Binding binding)
      throws WSDLException {
    // port
    Port port = def.createPort();
    port.setBinding(binding);
    // namespace declaration for binding name
    String bindingNamespace = binding.getQName().getNamespaceURI();
    Map namespaces = def.getNamespaces();
    if (!namespaces.containsValue(bindingNamespace)) {
      String prefix = generateName("ns", namespaces.keySet());
      def.addNamespace(prefix, bindingNamespace);
    }
    // soap address
    SOAPAddress soapAddress = (SOAPAddress) def.getExtensionRegistry()
        .createExtension(Port.class, SOAPConstants.Q_ELEM_SOAP_ADDRESS);
    soapAddress.setLocationURI(ADDRESS_LOCATION_URI);
    port.addExtensibilityElement(soapAddress);
    return port;
  }

  private static String generateName(String baseText, Set existingNames) {
    StringBuffer nameBuffer = new StringBuffer(baseText);
    int baseLength = baseText.length();
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      // append a natural number to the base text
      String altName = nameBuffer.append(i).toString();
      // check there is no collision with existing names
      if (!existingNames.contains(altName)) return altName;
      // remove appended number
      nameBuffer.setLength(baseLength);
    }
    throw new RuntimeException("could not generate name: base=" + baseText);
  }

  protected class PortBuilder extends BpelVisitorSupport {

    private Definition serviceDefinition;
    private Service service;
    private ImportsDefinition imports;
    private Map bindingDefinitions = new HashMap();

    /** {@inheritDoc} */
    public void visit(BpelDefinition process) {
      // definition to contain the service
      serviceDefinition = generateServiceDefinition(process.getTargetNamespace());
      // service
      service = serviceDefinition.createService();
      QName serviceName = new QName(serviceDefinition.getTargetNamespace(),
          generateServiceLocalName(process.getName()));
      service.setQName(serviceName);
      serviceDefinition.addService(service);
      // save imports for later use
      imports = process.getImports();
      // dive in the process
      propagate(process);
    }

    /** {@inheritDoc} */
    public void visit(Scope scope) {
      Iterator partnerLinkIt = scope.getPartnerLinks().values().iterator();
      while (partnerLinkIt.hasNext()) {
        PartnerLinkDefinition partnerLink = (PartnerLinkDefinition) partnerLinkIt.next();
        try {
          visit(partnerLink);
        }
        catch (WSDLException e) {
          Problem problem = new Problem(Problem.LEVEL_ERROR,
              "could not generate port: partnerLink=" + partnerLink.getName(),
              e);
          getProblemHandler().add(problem);
        }
      }
      // dive in the scope
      propagate(scope);
    }

    public Definition getServiceDefinition() {
      return serviceDefinition;
    }

    public Map getBindingDefinitions() {
      return bindingDefinitions;
    }

    protected void visit(PartnerLinkDefinition partnerLink)
        throws WSDLException {
      Role myRole = partnerLink.getMyRole();
      // if the process has no role, there is nothing to generate
      if (myRole == null) return;
      PortType portType = myRole.getPortType();
      QName portTypeName = portType.getQName();
      // definition to contain the binding
      Definition bindingDefinition = generateBindingDefinition(portTypeName,
          imports,
          bindingDefinitions);
      // binding
      QName bindingName = new QName(portTypeName.getNamespaceURI(),
          generateBindingLocalName(portTypeName.getLocalPart()));
      Binding binding = bindingDefinition.getBinding(bindingName);
      if (binding == null) {
        binding = generateBinding(bindingDefinition, portType);
        binding.setQName(bindingName);
        bindingDefinition.addBinding(binding);
      }
      // port
      Port port = generatePort(serviceDefinition, binding);
      String portName = generatePortName(service, partnerLink.getName());
      port.setName(portName);
      service.addPort(port);
    }
  }
}
