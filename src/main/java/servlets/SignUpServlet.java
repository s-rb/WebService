package servlets;

import model.UserProfile;
import com.google.gson.Gson;
import services.AccountService;
import services.DBException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private final static String CONTENT_TYPE = "text/html;charset=utf-8";
    private final AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String login = request.getParameter("login");
            String pass = request.getParameter("password");
            UserProfile userProfile = accountService.getUserByLogin(login);
            if (login == null || pass == null) {
                response.setContentType(CONTENT_TYPE);
                response.getWriter().println("Извините, требуется заполнить все поля");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (userProfile != null) { // такой пользователь уже есть
                response.setContentType(CONTENT_TYPE);
                response.getWriter().println("Пользователь с таким логином уже существует");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                UserProfile newUserProfile = new UserProfile(login, pass);
                long id = accountService.addNewUser(newUserProfile);
                UserProfile profileWithId = accountService.getUserById(id);
                Gson gson = new Gson();
                String json = gson.toJson(profileWithId);
                response.setContentType(CONTENT_TYPE);
                response.getWriter().println(json);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (DBException ex) {
            ex.printStackTrace();
        }
    }
}
