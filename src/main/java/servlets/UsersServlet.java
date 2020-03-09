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
//public class UsersServlet extends HttpServlet {
//
//    private final static String CONTENT_TYPE_STRING = "text/html;charset=utf-8";
//
//    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"}) //todo: remove after module 2 home work
//    private final AccountService accountService;
//
//    public UsersServlet(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//    //get public user profile
//    public void doGet(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException {
//        String login = request.getParameter("login");
//        UserProfile userProfile = accountService.getUserByLogin(login);
//        if (userProfile == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } else {
//            putJsonUserProfileToResponse(userProfile, response);
//        }
//    }
//
//    //sign up
//    public void doPost(HttpServletRequest request,
//                       HttpServletResponse response) throws ServletException, IOException {
//        String email = request.getParameter("email");
//        String pass = request.getParameter("pass");
//        String login = request.getParameter("login");
//        UserProfile userProfile = accountService.getUserByLogin(login);
//        String sessionId = request.getSession().getId();
//        if (accountService.getUserBySessionId(sessionId) != null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Вы уже зарегистрированы в системе");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        if (email == null || login == null || pass == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Необходимо заполнить все поля");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        if (userProfile != null) { // такой логин уже есть в системе
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Пользователь с таким логином уже существует");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        userProfile = new UserProfile(login, pass, email);
//        accountService.addNewUser(userProfile);
//        accountService.addSession(sessionId, userProfile);
//        putJsonUserProfileToResponse(userProfile, response);
//    }
//
//    //change profile
//    public void doPut(HttpServletRequest request,
//                      HttpServletResponse response) throws ServletException, IOException { // если нет старого логина или пароля
//        String sessionId = request.getSession().getId();
//        UserProfile currentUserProfile = accountService.getUserBySessionId(sessionId);
//        if (currentUserProfile == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Пожалуйста войдите в систему");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//        String email = request.getParameter("email"); // используем новые данные
//        String pass = request.getParameter("pass");
//        String login = request.getParameter("login");
//        UserProfile userProfile = accountService.getUserByLogin(login); // ищем по новому логину
//        if (userProfile != null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Пользователь с таким логином уже существует, попробуйте другой");
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } else { // Меняем профиль и отправляем результат
//            UserProfile newUserProfile = new UserProfile(login, pass, email);
//            accountService.changeUserProfile(currentUserProfile, newUserProfile, sessionId);
//            putJsonUserProfileToResponse(newUserProfile, response);
//        }
//    }
//
//    //unregister
//    public void doDelete(HttpServletRequest request,
//                         HttpServletResponse response) throws ServletException, IOException {
//        // проверка сессии
//        String sessionId = request.getSession().getId();
//        UserProfile userProfile = accountService.getUserBySessionId(sessionId);
//        if (userProfile == null) {
//            response.setContentType(CONTENT_TYPE_STRING);
//            response.getWriter().println("Пожалуйста войдите в систему");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//        // если пользователь уже в системе то по номеру сессии удаляем его профиль
//        accountService.deleteUserProfile(userProfile); // удаляем профиль из map с логинами
//        accountService.deleteSession(sessionId); // и удаляем сессию с профилем
//        // результат
//        response.setContentType(CONTENT_TYPE_STRING);
//        response.getWriter().println("Нам жаль, что вы ушли :-(");
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//
//    private void putJsonUserProfileToResponse(UserProfile userProfile, HttpServletResponse response) throws IOException {
//        Gson gson = new Gson();
//        String json = gson.toJson(userProfile);
//        response.setContentType(CONTENT_TYPE_STRING);
//        response.getWriter().println(json);
//        response.setStatus(HttpServletResponse.SC_OK);
//    }
//}
