package com.example.translator.spi;

import com.example.translator.types.Document;
import com.example.translator.types.TextNotTranslatable;

public interface Dictionary {
  
  public abstract String translate(String text) throws TextNotTranslatable;
  
  public abstract Document translate(Document document) throws TextNotTranslatable;
}