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
package org.jbpm.bpel.sublang.exe;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.bpel.sublang.def.Expression;
import org.jbpm.bpel.sublang.def.Query;
import org.jbpm.bpel.sublang.xpath.XPathEvaluatorFactory;
import org.jbpm.bpel.xml.BpelConstants;

/**
 * Evaluator factories produce BPEL expression and query evaluators.
 * @author Alejandro Guízar
 * @version $Revision: 1.3 $ $Date: 2006/08/21 01:05:59 $
 */
public abstract class EvaluatorFactory {

  private static Map factories = new HashMap();

  protected EvaluatorFactory() {
  }

  public abstract ExpressionEvaluator createEvaluator(Expression expression);

  public abstract QueryEvaluator createEvaluator(Query query);

  public static EvaluatorFactory getInstance(String language) {
    return (EvaluatorFactory) factories.get(language);
  }

  public static void registerInstance(String language, EvaluatorFactory instance) {
    factories.put(language, instance);
  }

  static {
    EvaluatorFactory.registerInstance(BpelConstants.URN_XPATH_1_0,
        new XPathEvaluatorFactory());
  }
}
