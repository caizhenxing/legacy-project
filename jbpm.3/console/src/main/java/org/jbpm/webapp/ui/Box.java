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
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;

public class Box extends UIComponentBase {
  
  Map valueExpressions = new HashMap();

  Node node;
  int borderWidth = 4;
  String borderColor = "red";
  String borderStyle = "groove";

  public void encodeBegin(FacesContext facesContext) throws IOException {
    log.debug("encoding for box begin...");

    Node node = (Node) ((ValueExpression)valueExpressions.get("node")).getValue(facesContext.getELContext());
    ResponseWriter responseWriter = facesContext.getResponseWriter();
    ProcessDefinition processDefinition = node.getProcessDefinition();
    Coordinates coordinates = Coordinates.getCoordinates(processDefinition);
    int[] nodeCoordinates = coordinates.getNodeCoordinates(node.getName());
    
    StringBuffer style = new StringBuffer();
    responseWriter.startElement("div", this);
    style.append("position:relative; ");
    style.append("border: ");
    style.append(borderColor);
    style.append(" ");
    style.append(Integer.toString(borderWidth));
    style.append("px ");
    style.append(borderStyle);
    style.append("; ");
    style.append("left:"+ (nodeCoordinates[Coordinates.X]-borderWidth) +"px; ");
    style.append("top:"+ (nodeCoordinates[Coordinates.Y]-borderWidth) +"px; ");
    style.append("width:"+ nodeCoordinates[Coordinates.WIDTH] +"px; ");
    style.append("height:"+ nodeCoordinates[Coordinates.HEIGHT] +"px; ");
    responseWriter.writeAttribute("style", style.toString(), null);

  }
  
  public void encodeEnd(FacesContext facesContext) throws IOException {
    log.debug("encoding for box end...");
    ResponseWriter responseWriter = facesContext.getResponseWriter();
    responseWriter.endElement("div");
  }
  
  public String getFamily() {
    return null;
  }
  
  public void setNode(Node node) {
    log.debug("setNode[ "+node+" ]");
    this.node = node;
  }

  public Node getNode() {
    return node;
  }
  
  public void setValueExpression(String name, ValueExpression binding) {
    log.debug("setValueExpression[ "+name+" | "+ binding +" ]");
    valueExpressions.put(name, binding);
  }

  private static Log log = LogFactory.getLog(Box.class);
}
