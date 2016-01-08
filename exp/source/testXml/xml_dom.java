import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.DOMImplementation;

public class ShowDomImpl {
	
   public static void main (String args[]) {
      try {
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder docb = dbf.newDocumentBuilder();
         DOMImplementation domImpl = docb.getDOMImplementation();
	
         if (domImpl.hasFeature("StyleSheets", "2.0")) {
            System.out.println("Style Sheets are supported.");
         } else {
            System.out.println("Style Sheets are not supported.");
         }
      } catch (Exception e) {}				  
	}
	
}





import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;

//STEP 1
import org.w3c.dom.Element;
//STEP 2
import org.w3c.dom.NodeList;
//STEP 3
import org.w3c.dom.Node;
//stepThroughAll
import org.w3c.dom.NamedNodeMap;


public class OrderProcessor {

	
private static void stepThroughAll (Node start)
   {
      System.out.println(start.getNodeName()+" = "+start.getNodeValue());   
         
      if (start.getNodeType() == start.ELEMENT_NODE) 
      {   
          NamedNodeMap startAttr = start.getAttributes();
          for (int i = 0; 
               i < startAttr.getLength();
               i++) {
             Node attr = startAttr.item(i);
             System.out.println("  Attribute:  "+ attr.getNodeName()
                                          +" = "+attr.getNodeValue());
          }   
      } 

      
      for (Node child = start.getFirstChild(); 
          child != null;
          child = child.getNextSibling())
      {
         stepThroughAll(child);
      }
   }


   private static void stepThrough (Node start)
   {

      System.out.println(start.getNodeName()+" = "+start.getNodeValue());   

      for (Node child = start.getFirstChild(); 
          child != null;
          child = child.getNextSibling())
      {
            stepThrough(child);

      }
   }



   private static void changeOrder (Node start, 
                               String elemName, 
                              String elemValue)
   {
      if (start.getNodeName().equals(elemName)) {
         start.getFirstChild().setNodeValue(elemValue);
      }
         
      for (Node child = start.getFirstChild(); 
          child != null;
          child = child.getNextSibling())
      {
          changeOrder(child, elemName, elemValue);
      }
   }

   public static void main (String args[]) {
      File docFile = new File("orders.xml");
      Document doc = null;      
      try {

       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	   dbf.setValidating(true);
         
       DocumentBuilder db = dbf.newDocumentBuilder();
       doc = db.parse(docFile);
		
		   //STEP 1:  Get the root element
		Element root = doc.getDocumentElement();
		System.out.println("The root element is "+root.getNodeName());
      
		//STEP 2:  Get the children
		NodeList children = root.getChildNodes();
		System.out.println("There are "+children.getLength()
                                  +" nodes in this document.");
         //STEP 3:  Step through the children
		for (Node child = root.getFirstChild(); 
			 child != null;
			 child = child.getNextSibling())
		{
			 System.out.println(start.getNodeName()+" = " 
                                       +start.getNodeValue());
		}
       
      //STEP 4:  Recurse this functionality
      stepThrough(root);
		//
			stepThroughAll(root);


     // Change text content

      changeOrder(root, "status", "processing");
      NodeList orders = root.getElementsByTagName("status");
      for (int orderNum = 0; 
           orderNum < orders.getLength(); 
           orderNum++) 
      {
          Element thisOrder = (Element)orders.item(orderNum);
   NodeList orderItems = thisOrder.getElementsByTagName("item");
   double total = 0;
   for (int itemNum = 0;
        itemNum < orderItems.getLength();
        itemNum++) {
			
      // Total up cost for each item and 
      // add to the order total
			  
        //Get this item as an Element
        Element thisOrderItem = (Element)orderItems.item(itemNum);

        //Get pricing information for this Item
        String thisPrice = 
                     thisOrderItem.getElementsByTagName("price").item(0)
                                        .getFirstChild().getNodeValue();
        double thisPriceDbl = new Double(thisPrice).doubleValue();
				
        //Get quantity information for this Item
        String thisQty = 
                     thisOrderItem.getElementsByTagName("qty").item(0)
                                      .getFirstChild().getNodeValue();
        double thisQtyDbl = new Double(thisQty).doubleValue();

        double thisItemTotal = thisPriceDbl*thisQtyDbl;
        total = total + thisItemTotal;
		}
	 String totalString = new Double(total).toString();
	  //add element Node
    Node totalNode = doc.createTextNode(totalString);
		  
    Element totalElement = doc.createElement("total");
    totalElement.appendChild(totalNode);
		  
    thisOrder.insertBefore(totalElement, thisOrder.getFirstChild());
	

	//delete
	
   //Get this item as an Element
   Element thisOrderItem = (Element)orderItems.item(itemNum);

  if (thisOrderItem.getAttributeNode("instock")
                  .getNodeValue().equals("N")) {
					
      Node deadNode = 
                 thisOrderItem.getParentNode().removeChild(thisOrderItem);

   } else {
				
      //Get pricing information for this Item
      String thisPrice = 
                   thisOrderItem.getElementsByTagName("price").item(0)
                                      .getFirstChild().getNodeValue();

      total = total + thisItemTotal;

      }


//replace
	   if (thisOrderItem.getAttributeNode("instock")
                  .getNodeValue().equals("N")) {
					
      Element backElement = doc.createElement("backordered");

      Node deadNode = thisOrderItem.getParentNode()
                .replaceChild(backElement,  thisOrderItem);


   } else {

   }
   //create Attribute and set value
   if (thisOrderItem.getAttributeNode("instock")
                  .getNodeValue().equals("N")) {
					
   Element backElement = doc.createElement("backordered");

   backElement.setAttributeNode(doc.createAttribute("itemid"));
				   
   String itemIdString = 
              thisOrderItem.getAttributeNode("itemid").getNodeValue();
   backElement.setAttribute("itemid", itemIdString);


   Node deadNode = thisOrderItem.getParentNode().replaceChild(backElement,
                                                        thisOrderItem);

} else {
}

//delete attribute
Element thisOrder = (Element)orders.item(orderNum);
		  
Element customer = 
       (Element)thisOrder.getElementsByTagName("customerid")
                                     .item(0);
customer.removeAttribute("limit");		  
		  
NodeList orderItems = thisOrder.getElementsByTagName("item");

      } catch (javax.xml.parsers.ParserConfigurationException pce) {
         System.out.println("The parser was not configured correctly.");
         System.exit(1);
      } catch (java.io.IOException ie) {
         System.out.println("Cannot read input file.");
         System.exit(1);
      } catch (org.xml.sax.SAXException se) {
         System.out.println("Problem parsing the file.");
         System.exit(1);
      } catch (java.lang.IllegalArgumentException ae) {
         System.out.println("Please specify an XML source.");
         System.exit(1);

      }
   }
}


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;
//STEP 1
import org.w3c.dom.Element;
//STEP 2
import org.w3c.dom.NodeList;
//STEP 3
import org.w3c.dom.Node;


public class OrderProcessor {
...
         System.exit(1);
      }
      
   //STEP 1:  Get the root element
   Element root = doc.getDocumentElement();
   System.out.println("The root element is "+root.getNodeName());
      
   //STEP 2:  Get the children
   NodeList children = root.getChildNodes();
   System.out.println("There are "+children.getLength()
                                  +" nodes in this document.");
         //STEP 3:  Step through the children
      for (Node child = root.getFirstChild(); 
          child != null;
          child = child.getNextSibling())
      {
         System.out.println(start.getNodeName()+" = " 
                                       +start.getNodeValue());
      }
                               
   }
}

