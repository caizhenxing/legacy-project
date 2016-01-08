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
package org.jbpm.bpel.db.type;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.jbpm.bpel.xml.util.XmlUtil;

/**
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ElementType implements UserType {

  private static final int[] SQLTYPES = new int[] { Types.CLOB };

  private static final Log log = LogFactory.getLog(ElementType.class);

  public boolean equals(Object o1, Object o2) {
    return (o1 == null) ? (o2 == null) : o1.equals(o2);
  }

  public int hashCode(Object o) {
    return o.hashCode();
  }

  public Object deepCopy(Object o) {
    return o != null ? ((Element) o).cloneNode(true) : null;
  }

  public boolean isMutable() {
    return true;
  }

  public Serializable disassemble(Object o) throws HibernateException {
    return (Serializable) o;
  }

  public Object assemble(Serializable s, Object o) throws HibernateException {
    return s;
  }

  public Object replace(Object original, Object target, Object owner) {
    return target;
  }

  public int[] sqlTypes() {
    return SQLTYPES;
  }

  public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
      throws HibernateException, SQLException {
    // retrieve text from stream
    String columnName = names[0];
    String text = rs.getString(columnName);
    // if SQL value is NULL, text is null as well
    if (text == null) return null;
    // prepare input source with bare location info
    InputSource source = new InputSource(new StringReader(text));
    source.setPublicId(columnName);
    try {
      // parse XML text
      Element element = XmlUtil.getDocumentBuilder()
          .parse(source)
          .getDocumentElement();
      if (log.isTraceEnabled())
        log.trace("returning '" + text + "' as column: " + names[0]);
      return element;
    }
    catch (SAXException e) {
      throw new HibernateException("error while building the DOM element", e);
    }
    catch (IOException e) {
      throw new HibernateException(
          "unable to read xml character stream from db", e);
    }
  }

  public void nullSafeSet(PreparedStatement st, Object value, int index)
      throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, SQLTYPES[0]);
      return;
    }
    Element element = (Element) value;
    try {
      // create identity transformer
      Transformer domWriter = XmlUtil.getTransformerFactory().newTransformer();
      // allocate result stream in memory
      StringWriter sink = new StringWriter();
      // write DOM subtree to stream
      domWriter.transform(new DOMSource(element), new StreamResult(sink));
      String text = sink.toString();
      // bind text from memory
      if (log.isTraceEnabled())
        log.trace("binding '" + text + "' to parameter: " + index);
      st.setString(index, text);
    }
    catch (TransformerException e) {
      throw new HibernateException(
          "unable to convert DOM element to its textual representation", e);
    }
  }

  public Class returnedClass() {
    return Element.class;
  }
}
