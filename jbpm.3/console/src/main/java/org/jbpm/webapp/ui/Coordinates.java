package org.jbpm.webapp.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * info contained in the gpd.xml file.
 */
public class Coordinates {

  static Map processCoordinates = new HashMap();

  Map nodeCoordinates = new HashMap();
  Integer height = null;
  Integer width = null;
  
  public static final int X = 0;
  public static final int Y = 1;
  public static final int WIDTH = 2;
  public static final int HEIGHT = 3;

  public synchronized static Coordinates getCoordinates(ProcessDefinition processDefinition) {
    Long id = new Long(processDefinition.getId());
    Coordinates coordinates = (Coordinates) processCoordinates.get(id);  
    if (coordinates==null) {
      coordinates = new Coordinates(processDefinition); 
      processCoordinates.put(id, coordinates);
    }
    return coordinates;
  }

  Coordinates(ProcessDefinition processDefinition) {
    FileDefinition fileDefinition = processDefinition.getFileDefinition();
    Document document = XmlUtil.parseXmlInputStream(fileDefinition.getInputStream("gpd.xml"));
    Element processDiagramElement = document.getDocumentElement();
    String widthText = processDiagramElement.getAttribute("width");
    String heightText = processDiagramElement.getAttribute("height");
    height = new Integer(heightText);
    width = new Integer(widthText);
    
    Iterator iter = XmlUtil.elementIterator(processDiagramElement, "node");
    while (iter.hasNext()) {
      Element nodeElement = (Element) iter.next();
      int[] nodeInfo = new int[4];
      String nodeName = nodeElement.getAttribute("name");
      nodeInfo[X] = Integer.parseInt(nodeElement.getAttribute("x"));
      nodeInfo[Y] = Integer.parseInt(nodeElement.getAttribute("y"));
      nodeInfo[WIDTH] = Integer.parseInt(nodeElement.getAttribute("width"));
      nodeInfo[HEIGHT] = Integer.parseInt(nodeElement.getAttribute("height"));
      nodeCoordinates.put(nodeName, nodeInfo);
    }
  }

  public int[] getNodeCoordinates(String nodeName) {
    return (int[]) nodeCoordinates.get(nodeName);
  }
  public Integer getHeight() {
    return height;
  }
  public Integer getWidth() {
    return width;
  }
}
