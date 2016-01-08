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
package org.jbpm.bpel.xml;

import org.jbpm.bpel.def.Activity;
import org.jbpm.bpel.def.While;

/**
 * Encapsulates the logic to create and connect process elements that constitute
 * the <i>repeatUntil</i> structure.
 * @author Juan Cantú
 * @version $Revision: 1.2 $ $Date: 2006/08/22 04:13:10 $
 */
public class RepeatUntilReader extends WhileReader {

  protected Activity createActivity() {
    While whileBlock = new While();
    whileBlock.setEvaluateFirst(false);
    return whileBlock;
  }
}
