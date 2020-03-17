package servlets;

import accountServer.AccountServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminPageServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(AdminPageServlet.class.getName());
    public static final String PAGE_URL = "/admin";
    public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    private final AccountServer accountServer;

    public AdminPageServlet(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        response.getWriter().println(accountServer.getUsersLimit());
        response.setStatus(HttpServletResponse.SC_OK);

        int limit = accountServer.getUsersLimit();
        int count = accountServer.getUsersCount();
        logger.info("Limit: {}. Count {}", limit, count);
    }
}
