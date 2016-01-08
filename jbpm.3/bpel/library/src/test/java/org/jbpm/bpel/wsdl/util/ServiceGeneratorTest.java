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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Import;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPFault;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.jbpm.bpel.def.BpelDefinition;
import org.jbpm.bpel.xml.BpelReader;
import org.jbpm.bpel.xml.ImportWsdlLocator;
import org.xml.sax.InputSource;

import com.ibm.wsdl.extensions.soap.SOAPConstants;

public class ServiceGeneratorTest extends TestCase {
  
  private BpelDefinition bpelDefinition = new BpelDefinition();
  private ServiceGenerator serviceGenerator = new ServiceGenerator();
  
  private Map bindingDefs = new HashMap();
  private Definition serviceDef;

  private static final String NS_ATM_FRONT = "urn:samples:atm";
  private static final String NS_ATM_PROCESS = "urn:samples:atmProcess";
  
  private static String outputDir = System.getProperty("java.io.tmpdir");

  public ServiceGeneratorTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    String processLocation = ServiceGeneratorTest.class.getResource("process.bpel").toExternalForm();
    // read wsdl
    org.jbpm.bpel.def.Import pltsImport = new org.jbpm.bpel.def.Import();
    pltsImport.setNamespace("urn:samples:atmProcess");
    pltsImport.setLocation("process.wsdl");
    pltsImport.setType(org.jbpm.bpel.def.Import.Type.WSDL);
    BpelReader bpelReader = BpelReader.getInstance();
    bpelReader.readWsdlDocument(pltsImport, new ImportWsdlLocator(processLocation));
    bpelDefinition.getImports().addImport(pltsImport);
    // read bpel
    bpelReader.read(bpelDefinition, new InputSource(processLocation));
    // generate the binding and service documents
    serviceGenerator.setOutputDirectory(outputDir);
    serviceGenerator.generatePortComponents(bpelDefinition);
    // read the binding definitions
    String bindingFileName = serviceGenerator.getBindingFile();
    int dotIndex = bindingFileName.lastIndexOf('.');
    String base = bindingFileName.substring(0, dotIndex);
    String extension = bindingFileName.substring(dotIndex + 1);
    WSDLReader wsdlReader = WsdlUtil.getFactory().newWSDLReader(); 
    for (int i = 1; i < Integer.MAX_VALUE; i++) {
      String location = base + i + '.' + extension; 
      File bindingFile = new File(outputDir, location);
      if (!bindingFile.canRead()) break;
      Definition bindingDef = wsdlReader.readWSDL(bindingFile.getPath());
      bindingDefs.put(bindingDef.getTargetNamespace(), bindingDef);
    }
    // read the service definition
    serviceDef = wsdlReader.readWSDL(new File(outputDir, serviceGenerator.getServiceFile()).getPath());
  }
  
  protected void tearDown() throws Exception {
    deleteDefinitionDocuments(serviceDef);
  }
  
  private static void deleteDefinitionDocuments(Definition def) {
    // delete the definition document first
    String docPath = URI.create(def.getDocumentBaseURI()).getPath();
    new File(docPath).delete();
    // now deal with imported documents
    Iterator importListIt = def.getImports().values().iterator();
    while (importListIt.hasNext()) {
      List importList = (List) importListIt.next();
      Iterator importIt = importList.iterator();
      while (importIt.hasNext()) {
        Import imp = (Import) importIt.next();
        deleteDefinitionDocuments(imp.getDefinition());
      }
    }
  }

  public void testGenerateBindingDefinitions() {
    assertEquals(1, bindingDefs.size()); 
    assertNotNull(bindingDefs.get(NS_ATM_FRONT)); 
  }
  
  public void testGenerateBindingDefinition() {
    Definition bindingDef = getAtmBindingDefinition(); 
    List interfaceImps = bindingDef.getImports(NS_ATM_PROCESS);
    assertEquals(1, interfaceImps.size());
    Import interfaceImp = (Import) interfaceImps.get(0);
    assertEquals(NS_ATM_PROCESS, interfaceImp.getNamespaceURI());
    assertEquals("process.wsdl", interfaceImp.getLocationURI());
  }
  
  public void testGenerateInterfaceImport() {
    Definition bindingDef = getAtmBindingDefinition(); 
    Iterator interfaceImpIt = bindingDef.getImports(NS_ATM_PROCESS).iterator();
    while (interfaceImpIt.hasNext()) {
      Import interfaceImp = (Import) interfaceImpIt.next();
      testWriteImportedDefinition(outputDir, interfaceImp);
    } 
  }
  
  private void testWriteImportedDefinition(String baseDir, Import imp) {
    File outputFile = new File(baseDir, imp.getLocationURI()); 
    assertTrue(outputFile.exists());
    // deal with imported documents
    baseDir = outputFile.getParent();
    Iterator importListIt = imp.getDefinition().getImports().values().iterator();
    while (importListIt.hasNext()) {
      List importList = (List) importListIt.next();
      Iterator importIt = importList.iterator();
      while (importIt.hasNext()) {
        imp = (Import) importIt.next();
        testWriteImportedDefinition(baseDir, imp);
      }
    }
  }
  
  public void testGenerateBindings() {
    Definition bindingDef = getAtmBindingDefinition();
    Map bindings = bindingDef.getBindings();
    assertEquals(1, bindings.size());
    assertNotNull(bindings.get(new QName(NS_ATM_FRONT, "atmBinding")));
  }
  
  public void testGenerateBinding() {
    Binding atmBinding = getAtmBinding();
    assertEquals(new QName(NS_ATM_FRONT, "atm"), atmBinding.getPortType().getQName());
    SOAPBinding soapBind = (SOAPBinding) WsdlUtil.getExtension(
        atmBinding.getExtensibilityElements(), SOAPConstants.Q_ELEM_SOAP_BINDING);
    assertEquals("rpc", soapBind.getStyle());
    assertEquals("http://schemas.xmlsoap.org/soap/http", soapBind.getTransportURI());
  }
  
  public void testGenerateBindingOperations() {
    List bindOps = getAtmBinding().getBindingOperations();
    assertEquals(3, bindOps.size());
    assertEquals("logon", ((BindingOperation) bindOps.get(0)).getName());
    assertEquals("deposit", ((BindingOperation) bindOps.get(1)).getName());
    assertEquals("withdraw", ((BindingOperation) bindOps.get(2)).getName());
  }
  
  public void testGenerateBindingOperation() {
    List bindOps = getAtmBinding().getBindingOperations();
    BindingOperation logonOp = (BindingOperation) bindOps.get(0);
    SOAPOperation soapOp = (SOAPOperation) WsdlUtil.getExtension(
        logonOp.getExtensibilityElements(), SOAPConstants.Q_ELEM_SOAP_OPERATION);
    assertEquals("", soapOp.getSoapActionURI());
  }
  
  public void testGenerateBindingInput() {
    BindingOperation logonOp = (BindingOperation) getAtmBinding().getBindingOperations().get(0);
    BindingInput bindInput = logonOp.getBindingInput();
    SOAPBody soapBody = (SOAPBody) WsdlUtil.getExtension(
        bindInput.getExtensibilityElements(), SOAPConstants.Q_ELEM_SOAP_BODY);
    assertEquals("literal", soapBody.getUse());
    assertEquals(NS_ATM_FRONT, soapBody.getNamespaceURI());
    assertNull(logonOp.getBindingOutput());
    assertTrue(logonOp.getBindingFaults().isEmpty());
  }
  
  public void testGenerateBindingOutput() {
    BindingOperation depositOp = (BindingOperation) getAtmBinding().getBindingOperations().get(1);
    assertNotNull(depositOp.getBindingInput());
    BindingOutput bindOutput = depositOp.getBindingOutput();
    SOAPBody soapBody = (SOAPBody) WsdlUtil.getExtension(
        bindOutput.getExtensibilityElements(), SOAPConstants.Q_ELEM_SOAP_BODY);
    assertEquals("literal", soapBody.getUse());
    assertEquals(NS_ATM_FRONT, soapBody.getNamespaceURI());
    assertTrue(depositOp.getBindingFaults().isEmpty());
  }
  
  public void testGenerateBindingFaults() {
    BindingOperation withdrawOp = (BindingOperation) getAtmBinding().getBindingOperations().get(2);
    assertNotNull(withdrawOp.getBindingInput());
    assertNotNull(withdrawOp.getBindingOutput());
    Map bindFaults = withdrawOp.getBindingFaults();
    assertEquals(1, bindFaults.size());
    assertNotNull(bindFaults.get("notEnoughFunds"));
  }

  public void testGenerateBindingFault() {
    BindingOperation withdrawOp = (BindingOperation) getAtmBinding().getBindingOperations().get(2);
    BindingFault bindFault = (BindingFault) withdrawOp.getBindingFault("notEnoughFunds");
    SOAPFault soapFault = (SOAPFault) WsdlUtil.getExtension(
        bindFault.getExtensibilityElements(), SOAPConstants.Q_ELEM_SOAP_FAULT);
    assertEquals("notEnoughFunds", soapFault.getName());
    assertEquals("literal", soapFault.getUse());
  }
  
  public void testGenerateServiceDefinition() {
    assertEquals(NS_ATM_PROCESS, serviceDef.getTargetNamespace());
    List bindingImps = serviceDef.getImports(NS_ATM_FRONT);
    assertEquals(1, bindingImps.size());
    Import bindingImp = (Import) bindingImps.get(0);
    assertEquals(NS_ATM_FRONT, bindingImp.getNamespaceURI());
    assertEquals("binding1.wsdl", bindingImp.getLocationURI());
  }
  
  public void testGenerateService() {
    Service atmService = getAtmService();
    Map ports = atmService.getPorts();
    assertEquals(1, ports.size());
    assertNotNull(ports.get("frontEndPort"));
  }
  
  public void testGeneratePort() {
    Service atmService = getAtmService();
    Port frontEndPort = atmService.getPort("frontEndPort");
    assertEquals(new QName(NS_ATM_FRONT, "atmBinding"), frontEndPort.getBinding().getQName());
    SOAPAddress soapAddress = (SOAPAddress) WsdlUtil.getExtension(
        frontEndPort.getExtensibilityElements(), SOAPConstants.Q_ELEM_SOAP_ADDRESS);
    assertEquals("REPLACE_WITH_ACTUAL_URI", soapAddress.getLocationURI());
  }

  private Definition getAtmBindingDefinition() {
    return (Definition) bindingDefs.get(NS_ATM_FRONT);
  }

  private Binding getAtmBinding() {
    return getAtmBindingDefinition().getBinding(new QName(NS_ATM_FRONT, "atmBinding")); 
  }
  
  private Service getAtmService() {
    return serviceDef.getService(new QName(NS_ATM_PROCESS, "atmService"));
  }
}
