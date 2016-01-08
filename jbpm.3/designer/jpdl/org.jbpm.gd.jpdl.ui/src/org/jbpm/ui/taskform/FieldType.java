package org.jbpm.ui.taskform;

public abstract class FieldType {
  
  public static FieldType[] getFieldTypes() {
    return fieldTypes;
  }
  
  public static FieldType[] fieldTypes = new FieldType[]{
    
    new FieldType(){
      public String render(Field field) {
        return "<input jsfc=\"h:input\" "+getValueAttribute(field)+getReadOnlyAttribute(field)+" />";
      }
      public String getName() {
        return "Text";
      }
    }
    
  };
  
  public String getValueAttribute(Field field) {
    return "value=\"#{taskBean.variables['"+field.getVariableName()+"']}";
  }

  public String getReadOnlyAttribute(Field field) {
    return (field.isReadOnly() ? " readonly=\"true\" />" : "");
  }

  public String toString() {
    return getName();
  }
  public abstract String getName();
  public abstract String render(Field field);
}
