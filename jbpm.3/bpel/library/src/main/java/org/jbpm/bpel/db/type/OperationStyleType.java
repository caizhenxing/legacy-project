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

import javax.wsdl.OperationType;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Maps a commons-lang <code>Enum</code> to a Hibernate type.
 * @author Juan Cantu
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class OperationStyleType implements UserType {

  static final int[] SQLTYPES = new int[] { Types.SMALLINT };

  static final OperationType OPERATIONTYPES[] = { OperationType.NOTIFICATION,
      OperationType.ONE_WAY, OperationType.REQUEST_RESPONSE,
      OperationType.SOLICIT_RESPONSE };

  public OperationStyleType() {
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
    int enumCode = ((Integer) Hibernate.INTEGER.nullSafeGet(rs, names[0])).intValue();

    return fromInt(enumCode);
  }

  /** {@inheritDoc} */
  public void nullSafeSet(PreparedStatement st, Object value, int index)
      throws HibernateException, SQLException {
    // make sure the received value is of the right type
    if ((value != null) && !returnedClass().isAssignableFrom(value.getClass())) {
      throw new IllegalArgumentException("Received value is not a["
          + returnedClass().getName() + "] but [" + value.getClass() + "]");
    }

    // set the value into the resultset
    st.setInt(index, toInt(value));
  }

  /** {@inheritDoc} */
  public Class returnedClass() {
    return OperationType.class;
  }

  public int toInt(Object object) {
    for (int i = 0; i < OPERATIONTYPES.length; i++)
      if (OPERATIONTYPES[i].equals(object)) return i + 1;
    return 0;
  }

  public Object fromInt(int code) {
    return (code > 0 && code <= OPERATIONTYPES.length) ? OPERATIONTYPES[code - 1]
        : null;
  }
}
