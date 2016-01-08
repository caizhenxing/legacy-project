package org.jbpm.ui.taskform;

public class Button {
  
  public static final Button BUTTON_SAVE = new Button(
    "<input type=\"submit\" jsfc=\"h:commandButton\" action=\"#{taskBean.save}\" value=\"Save\"/>" 
  );
  
  public static final Button BUTTON_CANCEL = new Button(
      "<input type=\"submit\" jsfc=\"h:commandButton\" action=\"#{taskBean.cancel}\" value=\"Cancel\"/>"
  );

  public static Button createTransitionButton(String transitionName) {
    return new Button("<input type=\"submit\" jsfc=\"h:commandButton\" id=\"transitionButton\" action=\"#{taskBean.saveAndClose}\" value=\""+transitionName+"\" onclick=\"document.forms['taskform'].elements['taskform:transitionName'].value='"+transitionName+"'/>");
  }


  String xhtml;
  
  public Button(String xhtml) {
    this.xhtml = xhtml; 
  }
  
  public String getXhtml() {
    return xhtml;
  }
  
  public void setXhtml(String xhtml) {
    this.xhtml = xhtml;
  }
}
