package org.jbpm.ui.taskform;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class FormGenerator {

  public static final int BUFFERSIZE = 4096;
  public static final String NEWLINE = System.getProperty("line.separator");

  public static String getForm(List fields, List buttons) {
    String form = getTemplateString();
    String fieldRowsText = buildFieldRows(fields);
    form = insert(form, "<!-- TASKFORM ROWS -->", fieldRowsText); 
    String buttonsText = buildButtons(buttons);
    form = insert(form, "<!-- TASKFORM BUTTONS -->", buttonsText); 
    return form.toString();
  }

  /**
   * add value after marker in source.
   */
  public static String insert(String source, String marker, String value) {
    int index = source.indexOf(marker);
    if (index==-1) {
      return source;
    }
    index = index+marker.length();
    StringBuffer buffer = new StringBuffer();
    buffer.append(source.substring(0, index));
    buffer.append(value);
    buffer.append(source.substring(index+1));
    return buffer.toString();
  }

  private static String buildFieldRows(List fields) {
    if (fields==null) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(NEWLINE);
    Iterator iter = fields.iterator();
    while (iter.hasNext()) {
      Field field = (Field) iter.next();
      buffer.append("  <tr>");
      buffer.append(NEWLINE);
      buffer.append("    <td>");
      buffer.append(field.getLabel());
      buffer.append("</td>");
      buffer.append(NEWLINE);
      buffer.append("    <td>");
      buffer.append(NEWLINE);
      buffer.append("      ");
      buffer.append(field.getFieldType().render(field));
      buffer.append(NEWLINE);
      buffer.append("    </td>");
      buffer.append(NEWLINE);
      buffer.append("  </tr>");
      buffer.append(NEWLINE);
    }
    return buffer.toString();
  }

  private static String buildButtons(List buttons) {
    if (buttons==null) {
      return "";
    }
    StringBuffer buffer = new StringBuffer();
    buffer.append(NEWLINE);
    Iterator iter = buttons.iterator();
    while (iter.hasNext()) {
      Button button = (Button) iter.next();
      buffer.append("      ");
      buffer.append(button.getXhtml());
      buffer.append(NEWLINE);
    }
    return buffer.toString();
  }

  public static String getTemplateString() {
    StringBuffer formTemplate = new StringBuffer();
    String resource = "form.template.xhtml";
    InputStream inputStream = FormGenerator.class.getResourceAsStream(resource);
    if (inputStream==null) {
      throw new RuntimeException("couldn't get resource "+resource+" for form template");
    }
    try {
      Reader reader = new InputStreamReader(inputStream);
      char[] chars = new char[BUFFERSIZE];
      int charsRead = reader.read( chars );
      while ( charsRead != -1 ) {
        formTemplate.append( chars, 0, charsRead );
        charsRead = reader.read( chars );
      }
    } catch (IOException e) {
      throw new RuntimeException("couldn't read form template", e);
    }
    return formTemplate.toString();
  }
}
