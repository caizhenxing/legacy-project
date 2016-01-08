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
package com.example.translator.resource;

import java.util.ResourceBundle;
import java.util.MissingResourceException;

import com.example.translator.spi.Dictionary;
import com.example.translator.types.Document;
import com.example.translator.types.TDocumentBody;
import com.example.translator.types.TDocumentHead;
import com.example.translator.types.TextNotTranslatable;

/**
 * @author Alejandro Guízar
 * @version $Revision: 1.2 $ $Date: 2006/08/21 01:05:59 $
 */
public class ResourceDictionary implements Dictionary {
  
  private final ResourceBundle bundle;
  
  public ResourceDictionary(ResourceBundle bundle) {
    this.bundle = bundle;
  }
  
  /** {@inheritDoc} */
  public String translate(String text) throws TextNotTranslatable {
    try {
      return bundle.getString(text);
    }
    catch (MissingResourceException e) {
      throw new TextNotTranslatable(text);
    }
  }

  /** {@inheritDoc} */
  public Document translate(Document document) throws TextNotTranslatable {
    TDocumentHead transHead = new TDocumentHead();
    transHead.setTitle(bundle.getString(document.getHead().getTitle()));
    transHead.setLanguage(bundle.getLocale().getLanguage());
    
    String[] paragraphs = document.getBody().getParagraph();
    String[] transParagraphs = new String[paragraphs.length];
    for (int i = 0; i < paragraphs.length; i++) {
      String paragraph = paragraphs[i];
      try {
        transParagraphs[i] = bundle.getString(paragraph);
      }
      catch (MissingResourceException e) {
        throw new TextNotTranslatable(paragraph);
      }
    }
    
    TDocumentBody transBody = new TDocumentBody();
    transBody.setParagraph(transParagraphs);
    
    Document targetDocument = new Document();
    targetDocument.setHead(transHead);
    targetDocument.setBody(transBody);
    
    return targetDocument;
  }
}
