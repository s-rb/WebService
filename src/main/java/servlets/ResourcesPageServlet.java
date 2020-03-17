package servlets;

import accountServer.AccountServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.servlet.Source;
import resourceServer.ResourceServerInt;
import resources.TestResource;
import sax.ReadXMLFileSAX;
import templater.PageGenerator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResourcesPageServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(AdminPageServlet.class.getName());
    public static final String PAGE_URL = "/admin";
    public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private final ResourceServerInt resourceServer;

    public ResourcesPageServlet(ResourceServerInt resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String pathToResource = request.getParameter("path");
        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(pathToResource);
        resourceServer.setTestResource(resource);
        // по полученному Path читаем из папки ресурс, создаем его и сохраняем в ResourceServer (или ResourceService)
        // После чтения эти значения должны быть видны в JMX
        response.setContentType(CONTENT_TYPE);
        if (pathToResource == null) {
            response.getWriter().println("Путь не верный или не задан");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
