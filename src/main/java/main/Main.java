package main;

import chat.WebSocketChatServlet;
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

        // Загружается файл конфигурации БД и создается фабрика сессий,
        // сессии открываются по отдельным запросам на обращение в БД
        // создаем сервлеты для обработки запросов и добавляем в хэндлер
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        AccountService accountService = new AccountService();  // Создаем соединение с базой данных при создании аккаунт сервис
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        // создаем и запускаем сервер
        Server server = new Server(8080);
        server.setHandler(handlers);
        server.start();
        Thread.sleep(500);
        System.err.println("Server started"); // вывод с паузой 500для запуска программы проверки работы
        server.join();
    }
}
