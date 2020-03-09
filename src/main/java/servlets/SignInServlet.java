package servlets;

import accounts.UserProfile;
import com.google.gson.Gson;
import services.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    private final static String CONTENT_TYPE = "text/html;charset=utf-8";
    private final AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        if (login == null || pass == null) {
            response.setContentType(CONTENT_TYPE);
            response.getWriter().println("Извините, требуется заполнить все поля");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        UserProfile userProfile = accountService.getUserByLogin(login);
        if (userProfile == null) { // пользователь не зарегистрирован
            response.setContentType(CONTENT_TYPE);
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String result = "Authorized: " + login;
            response.setContentType(CONTENT_TYPE);
            response.getWriter().println(result);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
