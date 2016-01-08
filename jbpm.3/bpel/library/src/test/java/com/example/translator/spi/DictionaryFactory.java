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
package com.example.translator.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.translator.types.DictionaryNotAvailable;
import com.example.translator.resource.ResourceDictionaryFactory;

public abstract class DictionaryFactory {
  
  private static List instances = new ArrayList();
  
  public abstract Dictionary createDictionary(Locale sourceLocale, Locale targetLocale);
  
  public abstract boolean acceptsLocales(Locale sourceLocale, Locale targetLocale);
    
  public static DictionaryFactory getInstance(Locale sourceLocale, Locale targetLocale)
  throws DictionaryNotAvailable {
    for (int i = 0, n = instances.size(); i < n; i++) {
      DictionaryFactory factory = (DictionaryFactory) instances.get(i);
      if (factory.acceptsLocales(sourceLocale, targetLocale)) {
        return factory;
      }
    }
    throw new DictionaryNotAvailable();
  }
  
  public static void registerInstance(DictionaryFactory instance) {
    instances.add(instance);
  }
  
  static {
    registerInstance(new ResourceDictionaryFactory());
  }
}