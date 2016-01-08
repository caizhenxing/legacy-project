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
package org.jbpm.bpel.sublang.xpath;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jaxen.BaseXPath;
import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.JaxenException;
import org.jaxen.dom.DocumentNavigator;
import org.jaxen.expr.CommentNodeStep;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.NameStep;
import org.jaxen.expr.ProcessingInstructionNodeStep;
import org.jaxen.expr.Step;
import org.jaxen.expr.TextNodeStep;
import org.jaxen.saxpath.Axis;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.jbpm.bpel.exe.BpelFaultException;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class XPathEvaluator extends BaseXPath {

  private static final long serialVersionUID = 1L;

  protected XPathEvaluator(String text) throws JaxenException {
    super(text, DocumentNavigator.getInstance());
  }

  protected List selectOrCreateNodes(LocationPath location, Context context)
      throws JaxenException {
    List contextNodes = context.getNodeSet();
    // empty context nodeset -> empty selection
    if (contextNodes.isEmpty()) {
      return Collections.EMPTY_LIST;
    }
    ContextSupport support = context.getContextSupport();
    // absolute path, start from the root node
    if (location.isAbsolute()) {
      Object rootNode = support.getNavigator()
          .getDocumentNode(contextNodes.get(0));
      contextNodes = Collections.singletonList(rootNode);
    }
    Iterator stepIter = location.getSteps().iterator();
    while (stepIter.hasNext()) {
      // prepare the context for the current step
      Context stepContext = new Context(support);
      stepContext.setNodeSet(contextNodes);
      // evaluate the step, capture the selected nodes
      Step step = (Step) stepIter.next();
      contextNodes = step.evaluate(stepContext);
      // no node was selected?
      if (contextNodes.isEmpty()) {
        // try to create the missing node
        Node newNode = createNode(step, stepContext);
        // create a new list, since the existing empty list is immutable
        contextNodes = Collections.singletonList(newNode);
      }
    }
    return contextNodes;
  }

  protected Node createNode(Step step, Context context) {
    List nodeset = context.getNodeSet();
    if (!step.getPredicates().isEmpty()) {
      // cannot create node for location steps with predicates
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    if (nodeset.size() != 1) {
      // cannot create node for context node sets of size other than one
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    Object node = nodeset.get(0);
    if (!(node instanceof Element)) {
      // cannot create node for a non-element parent
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    Element parent = (Element) node;
    Document doc = parent.getOwnerDocument();

    Node newNode;
    int axis = step.getAxis();
    switch (axis) {
    case Axis.ATTRIBUTE: {
      if (!(step instanceof NameStep)) {
        // cannot create node for non-name tests on the attribute axis
        throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
      }
      NameStep nameStep = (NameStep) step;
      String localName = nameStep.getLocalName();
      if ("*".equals(localName)) {
        // cannot create node for any-name node tests
        throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
      }

      // BPEL-191: preserve source prefix
      String prefix = nameStep.getPrefix();
      Attr attribute = StringUtils.isEmpty(prefix) ? doc.createAttributeNS(null,
          localName)
          : doc.createAttributeNS(context.translateNamespacePrefixToUri(prefix),
              prefix + ':' + localName);
      parent.setAttributeNodeNS(attribute);

      newNode = attribute;
      break;
    }
    case Axis.CHILD:
      if (step instanceof NameStep) {
        NameStep nameStep = (NameStep) step;
        String localName = nameStep.getLocalName();
        if ("*".equals(localName)) {
          // cannot create node for any-name node tests
          throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
        }

        // BPEL-191: preserve source prefix
        String prefix = nameStep.getPrefix();
        newNode = StringUtils.isEmpty(prefix) ? doc.createElementNS(null,
            localName)
            : doc.createElementNS(context.translateNamespacePrefixToUri(prefix),
                prefix + ':' + localName);
      }
      else if (step instanceof TextNodeStep) {
        newNode = doc.createTextNode("");
      }
      else if (step instanceof ProcessingInstructionNodeStep) {
        newNode = doc.createProcessingInstruction(((ProcessingInstructionNodeStep) step).getName(),
            "");
      }
      else if (step instanceof CommentNodeStep) {
        newNode = doc.createComment("");
      }
      else {
        // cannot create node for any-node tests on the child axis
        throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
      }
      parent.appendChild(newNode);
      break;
    default:
      // cannot create node on the specified axis
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    return newNode;
  }

  protected Node getSingleNode(List nodeset) {
    if (nodeset == null || nodeset.size() != 1) {
      // node set of size other than one
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    Object singleResult = nodeset.get(0);
    if (!(singleResult instanceof Node)) {
      // selection is not a node
      throw new BpelFaultException(BpelConstants.FAULT_SELECTION_FAILURE);
    }
    return (Node) singleResult;
  }
}