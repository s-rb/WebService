package main;

import accountServer.*;
import chat.WebSocketChatServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resourceServer.ResourceServer;
import resourceServer.ResourceServerController;
import resourceServer.ResourceServerControllerMBean;
import resourceServer.ResourceServerInt;
import services.AccountService;
import servlets.*;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Main
{
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }
        String portString = args[0];
        int port = Integer.valueOf(portString);
        logger.info("Starting at http://127.0.0.1:" + portString);

        // Загружается файл конфигурации БД и создается фабрика сессий,
        // сессии открываются по отдельным запросам на обращение в БД
        // создаем сервлеты для обработки запросов и добавляем в хэндлер
        AccountServer accountServer = new AccountServerImpl();
        AccountService accountService = new AccountService();  // Создаем соединение с базой данных при создании аккаунт сервис
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        context.addServlet(new ServletHolder(new AdminPageServlet(accountServer)), "/admin");
        context.addServlet(new ServletHolder(new HomePageServlet(accountServer)), "/home");

        // 6 Resources
        ResourceServerInt resourceServer = new ResourceServer();
        context.addServlet(new ServletHolder(new ResourcesPageServlet(resourceServer)), "/resources");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
//        resourceHandler.setResourceBase("public_html");
        resourceHandler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        AccountServerControllerMBean serverStatistics = new AccountServerController(accountServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController");
        mbs.registerMBean(serverStatistics, name);

        ResourceServerControllerMBean serverResource = new ResourceServerController(resourceServer);
        ObjectName resName = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(serverResource, resName);

        // создаем и запускаем сервер (порт был 8080, теперь задается в аргументах)
        Server server = new Server(port);
        server.setHandler(handlers);
        server.start();
        Thread.sleep(500);
        logger.info("Server started");
        System.err.println("Server started"); // вывод с паузой 500для запуска программы проверки работы
        server.join();
    }
}
