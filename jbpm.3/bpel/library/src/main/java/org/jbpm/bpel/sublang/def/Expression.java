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
package org.jbpm.bpel.sublang.def;

import org.jbpm.bpel.sublang.exe.EvaluatorFactory;
import org.jbpm.bpel.sublang.exe.ExpressionEvaluator;
import org.jbpm.bpel.xml.BpelConstants;
import org.jbpm.bpel.xml.BpelException;

/**
 * Expressions extract and combine data from the process variables in
 * interesting ways to control the behavior of the process.
 * @see "WS-BPEL 2.0 &sect;9.1, 14.3"
 * @author Alejandro Guizar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class Expression extends Snippet {

  private transient ExpressionEvaluator evaluator;

  private static final long serialVersionUID = 1L;

  public ExpressionEvaluator getEvaluator() {
    if (evaluator == null) {
      parse();
    }
    return evaluator;
  }

  public void parse() {
    // get language specific to this script
    String language = getLanguage();
    // no specific language, use XPath 1.0
    if (language == null) language = BpelConstants.URN_XPATH_1_0;

    // get factory associated to language
    EvaluatorFactory factory = EvaluatorFactory.getInstance(language);
    if (factory == null) {
      throw new BpelException("unsupported language: " + language);
    }

    // create expression evaluator
    evaluator = factory.createEvaluator(this);
  }
}