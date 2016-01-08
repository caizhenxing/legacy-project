package org.jbpm.webapp.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.el.ValueExpression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.graph.def.ProcessDefinition;

public class ProcessImage extends UIComponentBase {

  private Map valueExpressions = new HashMap();

  public void encodeBegin(FacesContext facesContext) throws IOException {
    log.debug("encodeBegin");
    ValueExpression valueExpression = (ValueExpression)valueExpressions.get("processDefinition");
    ProcessDefinition processDefinition = (ProcessDefinition) valueExpression.getValue(facesContext.getELContext());
    log.debug("processDefinition: "+processDefinition);
    Coordinates coordinates = Coordinates.getCoordinates(processDefinition);
    ResponseWriter responseWriter = facesContext.getResponseWriter();
    responseWriter.startElement("div", this);
    String style = "position:relative; background-image:url('processimage?definitionId="+processDefinition.getId()+"'); height:" + coordinates.getHeight() + "px; width:" + coordinates.getWidth() + "px;";
    responseWriter.writeAttribute("style", style, null);
  }

  public void encodeEnd(FacesContext facesContext) throws IOException {
    log.debug("encodeEnd");
    ResponseWriter responseWriter = facesContext.getResponseWriter();
    responseWriter.endElement("div");
  }

  public String getFamily() {
    return null;
  }

  public void setValueExpression(String name, ValueExpression binding) {
    log.debug("setValueExpression[ "+name+" | "+ binding +" ]");
    valueExpressions.put(name, binding);
  }

  private static Log log = LogFactory.getLog(ProcessImage.class);
}
