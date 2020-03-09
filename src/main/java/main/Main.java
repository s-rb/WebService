package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.AccountService;
import servlets.AllRequestsServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main
{
    public static void main(String[] args) throws Exception {

        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        AccountService accountService = new AccountService();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        Thread.sleep(500);
        System.err.println("Server started"); // вывод с паузой 500для запуска программы проверки работы
        server.join();
    }
}
