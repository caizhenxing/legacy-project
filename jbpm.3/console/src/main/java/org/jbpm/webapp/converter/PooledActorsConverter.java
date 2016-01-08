package org.jbpm.webapp.converter;

import java.util.Collection;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jbpm.taskmgmt.exe.PooledActor;

public class PooledActorsConverter implements Converter {

  public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) throws ConverterException {
    return null;
  }

  public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) throws ConverterException {
    StringBuffer text = new StringBuffer();
    HtmlOutputText htmlOutputText = (HtmlOutputText) uiComponent;
    Collection pooledActors = (Collection) htmlOutputText.getValue();
    Iterator iter = pooledActors.iterator();
    while (iter.hasNext()) {
      PooledActor pooledActor = (PooledActor) iter.next();
      text.append(pooledActor.getActorId());
      if (iter.hasNext()) {
        text.append(", ");
      }
    }
    return text.toString();
  }

}
