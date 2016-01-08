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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.xml.namespace.QName;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author Juan Cantu
 * @version $Revision: 1.1 $ $Date: 2006/08/22 04:13:11 $
 */
public class QNameType implements UserType {

  private static final int[] SQL_TYPES = new int[] { Types.VARCHAR,
      Types.VARCHAR };

  public int[] sqlTypes() {
    return SQL_TYPES;
  }

  public boolean equals(Object x, Object y) throws HibernateException {
    return (x == null) ? (y == null) : ((QName) x).equals(y);
  }

  public int hashCode(Object x) throws HibernateException {
    return x.hashCode();
  }

  public Object deepCopy(Object value) throws HibernateException {
    return value;
  }

  public boolean isMutable() {
    return false;
  }

  public Serializable disassemble(Object value) throws HibernateException {
    return (Serializable) value;
  }

  public Object assemble(Serializable cached, Object owner)
      throws HibernateException {
    return cached;
  }

  public Object replace(Object original, Object target, Object owner)
      throws HibernateException {
    return target;
  }

  /**
   * {@inheritDoc}
   */
  public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
      throws HibernateException, SQLException {
    String namespaceURI = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
    String localPart = (String) Hibernate.STRING.nullSafeGet(rs, names[1]);
    return (namespaceURI != null || localPart != null) ? new QName(
        namespaceURI, localPart) : null;
  }

  /**
   * {@inheritDoc}
   */
  public void nullSafeSet(PreparedStatement st, Object value, int index)
      throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, Types.VARCHAR);
      st.setNull(index + 1, Types.VARCHAR);
    }
    else if (returnedClass().isAssignableFrom(value.getClass())) {
      QName qname = (QName) value;
      // set the value into the resultset
      st.setString(index, qname.getNamespaceURI());
      st.setString(index + 1, qname.getLocalPart());
    }
    else {
      // the received value is not of the expected type
      throw new IllegalArgumentException("Received value is not a["
          + returnedClass().getName() + "] but [" + value.getClass() + "]");
    }
  }

  public Class returnedClass() {
    return QName.class;
  }
}
