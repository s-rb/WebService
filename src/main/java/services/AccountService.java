package services;

import model.*;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

   private final DBService dbService;

    public AccountService() {
        this.dbService = new DBService();
    }

    public long addNewUser(UserProfile userProfile) throws DBException { // Меняем хранилище на БД
        return dbService.addUser(userProfile);
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        return dbService.getUserByLogin(login);
    }

    public UserProfile getUserBySessionId(String sessionId) throws DBException {
        // Сначала получаем логин по сессии, потом по логину находим профиль
        String login = getUserSessionBySessionId(sessionId).getLogin();
        return getUserByLogin(login);
    }

    public UserSession getUserSessionBySessionId(String sessionId) throws DBException {
        return dbService.getUserSession(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) throws DBException {
        dbService.addSession(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) throws DBException {
        dbService.deleteSession(sessionId);
    }

    public void deleteUserProfile(UserProfile userProfile) throws DBException {
        dbService.deleteUserProfile(userProfile);
    }

    public void changeUserProfile(UserProfile currentUserProfile, UserProfile newUserProfile, String sessionId) throws DBException {
        deleteUserProfile(currentUserProfile); // удаляем по текущему логину
        addNewUser(newUserProfile); // добавляем по новому логину, все потому что переменные в UserProfile final
        // старый добавить нового с тем же sessionId
        dbService.changeLoginBySessionId(newUserProfile, sessionId);
    }

    public UserProfile getUserById(long id) throws DBException {
        return dbService.getUser(id);
    }
}
