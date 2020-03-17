package servlets;

import accountServer.AccountServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(HomePageServlet.class.getName());
    public static final String PAGE_URL = "/home";
    public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private final AccountServer accountServer;

    public HomePageServlet(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        // Если в request получаем параметр remove, то удаляем пользователя из аккаунт сервера
        String remove = request.getParameter("remove");
        if (remove != null) {
            accountServer.removeUser();
            response.getWriter().println("Hasta la vista!");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // После удаления пишем в лог количество юзеров и лимит
        int limit = accountServer.getUsersLimit();
        int count = accountServer.getUsersCount();
        logger.info("Limit: {}. Count {}", limit, count);

        if (limit > count) {
            logger.info("User pass");
            accountServer.addNewUser();
            response.getWriter().println("Hello, world!");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.info("User were rejected");
            response.getWriter().println("Server is closed for maintenance!");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }



    }
}
