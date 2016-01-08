package org.jbpm.bpel.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.par.ProcessArchive;

/**
 * @author Alejandro Guizar
 * @version $Revision: 1.1 $ $Date: 2006/09/05 07:26:07 $
 */
public class ProcessDeployServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public static final String PROCESSFILE_PARAM = "processfile";

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String processFile = request.getParameter(PROCESSFILE_PARAM);

    log("deploying process definition: file=" + processFile);

    ProcessArchive archive;
    try {
      URL archiveURL = new URL(processFile);
      InputStream archiveStream = archiveURL.openStream();
      archive = new ProcessArchive(new ZipInputStream(archiveStream));
      archiveStream.close();
    }
    catch (IOException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "Failed to deploy process definition: could not read file");
      log("could not read process file: " + processFile, e);
      return;
    }

    ProcessDefinition definition = archive.parseProcessDefinition();

    List problems = archive.getProblems();
    if (problems.isEmpty()) {
      JbpmContext jbpmContext = JbpmConfiguration.getInstance()
          .createJbpmContext();
      try {
        jbpmContext.deployProcessDefinition(definition);

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.print("Deployed process definition: ");
        out.println(definition);
        out.println("</body></html>");

        log("deployed process definition: " + definition.getName());
      }
      catch (RuntimeException e) {
        jbpmContext.setRollbackOnly();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            "Failed to deploy process definition; internal error");
        log("could not deploy process definition: " + definition.getName(), e);
      }
      finally {
        jbpmContext.close();
      }
    }
    else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "Failed to deploy process definition; problems found");
      log("invalid process definition: file=" + processFile + ", problems="
          + problems);
    }
  }
}