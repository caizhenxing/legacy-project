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
package com.example.translator;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.example.translator.spi.Dictionary;
import com.example.translator.spi.DictionaryFactory;
import com.example.translator.types.DictionaryNotAvailable;
import com.example.translator.types.Document;
import com.example.translator.types.QuotationRequest;
import com.example.translator.types.TQuoteStatus;
import com.example.translator.types.TextNotTranslatable;
import com.example.translator.types.TranslationRequest;
import com.example.translator.types.StatusResponse;
import com.example.translator.types.StatusRequest;

public class DocumentTranslator_Impl implements DocumentTranslator, Remote {
  
  private static Map quotationRequests = new HashMap();
  
  private static final Log log = LogFactory.getLog(DocumentTranslator_Impl.class);

  public Document translate(TranslationRequest translationRequest)
  throws DictionaryNotAvailable, TextNotTranslatable, RemoteException {
    Document document = translationRequest.getDocument();
    Locale sourceLocale = new Locale(document.getHead().getLanguage());
    Locale targetLocale = new Locale(translationRequest.getTargetLanguage());
    DictionaryFactory dictionaryFactory = DictionaryFactory.getInstance(sourceLocale, targetLocale);
    Dictionary dictionary = dictionaryFactory.createDictionary(sourceLocale, targetLocale);
    return dictionary.translate(document);
  }

  public void quoteTranslation(QuotationRequest quotationRequest) throws RemoteException {
    String clientName = quotationRequest.getClientName();
    log.debug("received quotation request: clientName=" + clientName);
    quotationRequests.put(clientName, quotationRequest);
  }
  
  public StatusResponse getQuotationStatus(StatusRequest statusRequest) 
  throws RemoteException {
    StatusResponse statusResponse = new StatusResponse();
    TQuoteStatus quoteStatus;
    if (quotationRequests.containsKey(statusRequest.getClientName())) {
      quoteStatus = TQuoteStatus.received;
    }
    else {
      quoteStatus = TQuoteStatus.none;
    }
    statusResponse.setStatus(quoteStatus);
	  return statusResponse;
  }
}
