//package servlets;
//
//import accounts.UserProfile;
//import com.google.gson.Gson;
//import services.AccountService;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class SessionServlet extends HttpServlet {
//
//    private final AccountService accountService;
//    private final static String CONTENT_TYPE_STRING = "text/html;charset=utf-8";
//
//    public SessionServlet(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    // Get logged user profile
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String sessionId = request.getSession().getId();
//        UserProfile userProfile = accountService.getUserBySessionId(sessionId);
//        if (userProfile == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
//            Gson gson = new Gson();
//            String json = gson.toJson(userProfile);
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println(json);
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//    }
//
//    // Sign In
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String login = request.getParameter("login");
//        String pass = request.getParameter("pass");
//        if (login == null || pass == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        UserProfile userProfile = accountService.getUserByLogin(login);
//        if (userProfile == null || !userProfile.getPassword().equals(pass)) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//        accountService.addSession(request.getSession().getId(), userProfile);
//        Gson gson = new Gson();
//        String json = gson.toJson(userProfile);
//        response.setContentType(CONTENT_TYPE_STRING);
//        response.getWriter().println(json);
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//    // Sign Out
//    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String sessionId = request.getSession().getId();
//        UserProfile userProfile = accountService.getUserBySessionId(sessionId);
//        if (userProfile == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        } else {
//            accountService.deleteSession(sessionId);
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Нам будет вас не хватать!");
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//    }
//}
