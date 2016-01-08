package org.jbpm.webapp;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.HTMLElement;
import junit.framework.TestCase;

public class HomePageTest extends TestCase {

  private static final String baseUrl = "http://localhost:8080/jbpm-console/participant/home.jsf";


  public void setUp() {
    HttpUnitOptions.setScriptingEnabled(false);
  }

  public void testLoginError() throws Exception {
    final WebConversation conv = new WebConversation();
    assertNotNull(conv);
    final WebResponse response = conv.getResponse(baseUrl);
    assertNotNull(response);
    final WebForm loginForm = response.getFormWithName("loginform");
    assertNotNull(loginForm);
    loginForm.setParameter("j_username", "ernie");
    loginForm.setParameter("j_password", "whoopsydayzies");
    final WebResponse loginResponse = loginForm.submit();
    assertNotNull(loginResponse);
    final HTMLElement element = loginResponse.getElementWithID("title");
    assertNotNull(element);
    assertEquals(element.getText(), "Buzzzzzzzzz");
  }

  public void testLoginOk() throws Exception {
    final WebConversation conv = new WebConversation();
    assertNotNull(conv);
    final WebResponse response = conv.getResponse(baseUrl);
    assertNotNull(response);
    final WebForm loginForm = response.getFormWithName("loginform");
    assertNotNull(loginForm);
    loginForm.setParameter("j_username", "ernie");
    loginForm.setParameter("j_password", "ernie");
    final WebResponse loginResponse = loginForm.submit();
    assertNotNull(loginResponse);
    final HTMLElement element = loginResponse.getElementWithID("title");
    assertNotNull(element);
  }
}
