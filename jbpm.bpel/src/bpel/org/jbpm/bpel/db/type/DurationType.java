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

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import org.jbpm.bpel.xml.util.Duration;

public class DurationType implements UserType {

  static final int[] SQLTYPES = new int[] { Hibernate.STRING.sqlType() };

  public DurationType() {
  }

  public boolean equals(Object o1, Object o2) {
    return (o1 == o2);
  }

  public int hashCode(Object o) throws HibernateException {
    return o.hashCode();
  }

  public Object deepCopy(Object o) throws HibernateException {
    return o;
  }

  public boolean isMutable() {
    return false;
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

  /** {@inheritDoc} */
  public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
      throws HibernateException, SQLException {
    String durationString = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
    return durationString != null ? Duration.parseDuration(durationString)
        : null;
  }

  /** {@inheritDoc} */
  public void nullSafeSet(PreparedStatement st, Object value, int index)
      throws HibernateException, SQLException {
    String durationString = null;
    if (value != null) {
      if (!returnedClass().isAssignableFrom(value.getClass())) {
        // make sure the received value is of the right type
        throw new IllegalArgumentException("Received value is not a["
            + returnedClass().getName() + "] but [" + value.getClass() + "]");
      }
      durationString = ((Duration) value).toString();
    }
    // set the value into the resultset
    st.setString(index, durationString);
  }

  /** {@inheritDoc} */
  public Class returnedClass() {
    return Duration.class;
  }
}
