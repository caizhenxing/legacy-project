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
package org.jbpm.bpel.def;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;

import com.ibm.wsdl.Constants;

import org.jbpm.bpel.def.Import.Type;
import org.jbpm.bpel.variable.def.ElementType;
import org.jbpm.bpel.variable.def.MessageType;
import org.jbpm.bpel.variable.def.SchemaType;
import org.jbpm.bpel.wsdl.PartnerLinkType;
import org.jbpm.bpel.wsdl.Property;
import org.jbpm.bpel.wsdl.util.WsdlUtil;
import org.jbpm.bpel.wsdl.xml.WsdlConstants;
import org.jbpm.module.def.ModuleDefinition;
import org.jbpm.module.exe.ModuleInstance;

/**
 * Groups imported documents and provides lookup facilites for various elements
 * defined in them.
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public class ImportsDefinition extends ModuleDefinition {

  private List imports;

  private Map partnerLinkTypes = new HashMap();
  private Map portTypes = new HashMap();
  private Map properties = new HashMap();
  private Map messages = new HashMap();
  private Map messageTypes = new HashMap();
  private Map elementTypes = new HashMap();
  private Map schemaTypes = new HashMap();

  private static final long serialVersionUID = 1L;

  public List getImports() {
    return imports;
  }

  public void setImports(List imports) {
    this.imports = imports;
  }

  public void addImport(Import imp) {
    if (imports == null) {
      imports = new ArrayList();
    }
    imports.add(imp);
  }

  public PartnerLinkType getPartnerLinkType(QName name) {
    PartnerLinkType partnerLinkType = (PartnerLinkType) partnerLinkTypes.get(name);
    if (partnerLinkType == null) {
      for (int i = 0, n = imports.size(); i < n; i++) {
        Import imp = (Import) imports.get(i);
        if (Type.WSDL.equals(imp.getType())) {
          partnerLinkType = WsdlUtil.getPartnerLinkType((Definition) imp.getDocument(),
              name);
          if (partnerLinkType != null) {
            addPartnerLinkType(partnerLinkType);
            break;
          }
        }
      }
    }
    return partnerLinkType;
  }

  public void addPartnerLinkType(PartnerLinkType partnerLinkType) {
    // register the referenced port types
    PartnerLinkType.Role role = partnerLinkType.getFirstRole();
    if (role != null) {
      role.setPortType(addPortType(role.getPortType()));
    }
    role = partnerLinkType.getSecondRole();
    if (role != null) {
      role.setPortType(addPortType(role.getPortType()));
    }
    partnerLinkTypes.put(partnerLinkType.getQName(), partnerLinkType);
  }

  public PortType getPortType(QName name) {
    PortType portType = (PortType) portTypes.get(name);
    if (portType == null) {
      for (int i = 0, n = imports.size(); i < n; i++) {
        Import imp = (Import) imports.get(i);
        if (Type.WSDL.equals(imp.getType())) {
          portType = WsdlUtil.getPortType((Definition) imp.getDocument(), name);
          if (portType != null) {
            portTypes.put(name, portType);
            break;
          }
        }
      }
    }
    return portType;
  }

  public PortType addPortType(PortType portType) {
    QName name = portType.getQName();
    PortType internPortType = (PortType) portTypes.get(name);
    if (internPortType == null) {
      // add the port type
      Iterator operationIt = portType.getOperations().iterator();
      while (operationIt.hasNext()) {
        Operation operation = (Operation) operationIt.next();
        // input
        Input input = operation.getInput();
        if (input != null) {
          input.setMessage(addMessage(input.getMessage()));
        }
        // output
        Output output = operation.getOutput();
        if (output != null) {
          output.setMessage(addMessage(output.getMessage()));
        }
        // faults
        Iterator faultIt = operation.getFaults().values().iterator();
        while (faultIt.hasNext()) {
          Fault fault = (Fault) faultIt.next();
          fault.setMessage(addMessage(fault.getMessage()));
        }
      }
      portTypes.put(name, portType);
      internPortType = portType;
    }
    return internPortType;
  }

  public Message getMessage(QName name) {
    Message message = (Message) messages.get(name);
    if (message == null) {
      for (int i = 0, n = imports.size(); i < n; i++) {
        Import imp = (Import) imports.get(i);
        if (Type.WSDL.equals(imp.getType())) {
          message = WsdlUtil.getMessage((Definition) imp.getDocument(), name);
          if (message != null) {
            messages.put(name, message);
            break;
          }
        }
      }
    }
    return message;
  }

  public Message addMessage(Message message) {
    QName name = message.getQName();
    Message internMessage = (Message) messages.get(name);
    if (internMessage == null) {
      messages.put(name, message);
      internMessage = message;
    }
    return internMessage;
  }

  public Property getProperty(QName name) {
    Property property = (Property) properties.get(name);
    if (property == null) {
      for (int i = 0, n = imports.size(); i < n; i++) {
        Import imp = (Import) imports.get(i);
        if (Type.WSDL.equals(imp.getType())) {
          property = WsdlUtil.getProperty((Definition) imp.getDocument(), name);
          if (property != null) {
            properties.put(name, property);
            break;
          }
        }
      }
    }
    return property;
  }

  public void addProperty(Property property) {
    properties.put(property.getQName(), property);
  }

  /*
   * public PortType getPortType(QName name) { if (portTypes == null ||
   * !portTypes.containsKey(name)) { for (int i = 0, n = imports.size(); i < n;
   * i++) { Import imp = (Import) imports.get(i); if
   * (Type.WSDL.equals(imp.getType())) { PortType portType = ((Definition)
   * imp.getDocument()).getPortType(name); if (portType != null) {
   * addPortType(portType); return portType; } } } return null; } return
   * (PortType) portTypes.get(name); }
   */

  /**
   * Gets the import where a certain definition can be found.
   * @param name the definition name
   * @param element the definition element, can be one of the following:
   *          <ul>
   *          <li>wsdl:portType</li>
   *          <li>wsdl:message</li>
   *          <li>bpel:partnerLinkType</li>
   *          <li>bpel:property</li>
   *          </ul>
   * @return the declaring import
   */
  public Import getDeclaringImport(QName name, QName element) {
    for (int i = 0, n = imports.size(); i < n; i++) {
      Import imp = (Import) imports.get(i);
      if (imp.getType().equals(Type.WSDL)) {
        Definition def = (Definition) imp.getDocument();
        if ((Constants.Q_ELEM_PORT_TYPE.equals(element) && WsdlUtil.getPortType(def,
            name) != null)
            || (Constants.Q_ELEM_MESSAGE.equals(element) && WsdlUtil.getMessage(def,
                name) != null)
            || (WsdlConstants.Q_PARTNER_LINK_TYPE.equals(element) && WsdlUtil.getPartnerLinkType(def,
                name) != null)
            || (WsdlConstants.Q_PROPERTY.equals(element) && WsdlUtil.getProperty(def,
                name) != null)) {
          return imp;
        }
      }
    }
    return null;
  }

  public MessageType getMessageType(QName name) {
    if (messageTypes == null) {
      messageTypes = new HashMap();
    }
    MessageType type = (MessageType) messageTypes.get(name);
    if (type == null) {
      Message message = getMessage(name);
      if (message != null) {
        type = new MessageType(message);
        messageTypes.put(name, type);
      }
    }
    return type;
  }

  public ElementType getElementType(QName name) {
    if (elementTypes == null) {
      elementTypes = new HashMap();
    }
    ElementType type = (ElementType) elementTypes.get(name);
    if (type == null) {
      type = new ElementType(name);
      elementTypes.put(name, type);
    }
    return type;
  }

  public SchemaType getSchemaType(QName name) {
    if (schemaTypes == null) {
      schemaTypes = new HashMap();
    }
    SchemaType type = (SchemaType) schemaTypes.get(name);
    if (type == null) {
      type = new SchemaType(name);
      schemaTypes.put(name, type);
    }
    return type;
  }

  public ModuleInstance createInstance() {
    return null;
  }
}
