package org.jbpm.jpdl.el.impl;


import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmException;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.VariableResolver;

public class JbpmExpressionEvaluator {

  static ExpressionEvaluatorImpl evaluator = new ExpressionEvaluatorImpl();
  
  static VariableResolver variableResolver = null;
  public static void setVariableResolver(VariableResolver variableResolver) {
    JbpmExpressionEvaluator.variableResolver = variableResolver;
  }
  
  public static Object evaluate(String expression, ExecutionContext executionContext) {
    return evaluate(expression, executionContext, getUsedVariableResolver());
  }

  public static Object evaluate(String expression, ExecutionContext executionContext, VariableResolver usedVariableResolver) {
    Object result = null;
    
    ExecutionContext.pushCurrentContext(executionContext);
    try {
      String dollarExpression = translateExpressionToDollars(expression);
      result = evaluator.evaluate(dollarExpression, Object.class, usedVariableResolver, null);

    } catch (ELException e) {
      
      throw new JbpmException("couldn't evaluate expression '"+expression+"'", (e.getRootCause()!=null ? e.getRootCause() : e));
    } finally {
      ExecutionContext.popCurrentContext(executionContext);
    }
    
    return result;
  }

  static String translateExpressionToDollars(String expression) {
    char[] chars = expression.toCharArray();
    int index = 0;
    while (index!=-1) {
      index = expression.indexOf("#{", index);
      if (index!=-1) {
        chars[index] = '$';
        index++;
      }
    }
    return new String(chars);
  }

  public static VariableResolver getUsedVariableResolver() {
    if (variableResolver!=null) {
      return variableResolver;
    }
    return (VariableResolver) JbpmConfiguration.Configs.getObject("jbpm.variable.resolver");
  }
}
