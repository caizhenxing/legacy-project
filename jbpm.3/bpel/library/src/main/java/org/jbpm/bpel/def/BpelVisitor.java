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

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public interface BpelVisitor {

  public void visit(BpelDefinition process);

  public void visit(Empty empty);

  public void visit(Receive receive);

  public void visit(Reply reply);

  public void visit(Invoke invoke);

  public void visit(Assign assign);

  public void visit(Throw throwActivity);

  public void visit(Exit exit);

  public void visit(Wait wait);

  public void visit(Sequence sequence);

  public void visit(If ifActivity);

  public void visit(While whileActivity);

  public void visit(Pick pick);

  public void visit(Flow flow);

  public void visit(Scope scope);

  public void visit(Compensate compensate);

  public void visit(Rethrow rethrow);

  public void visit(Validate validate);
}