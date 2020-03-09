package services;

import accounts.*;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        this.loginToProfile = new HashMap<>();
        this.sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
       loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    public void deleteUserProfile(UserProfile userProfile) {
        loginToProfile.remove(userProfile.getLogin());
    }

    public void changeUserProfile(UserProfile currentUserProfile, UserProfile newUserProfile, String sessionId) {
        deleteUserProfile(currentUserProfile); // удаляем по текущему логину
        addNewUser(newUserProfile); // добавляем по новому логину, все потому что переменные в UserProfile final
        sessionIdToProfile.put(sessionId, newUserProfile); // В текущей сессии меняем сохраненный профиль, а можно удалить
        // старый добавить нового с тем же sessionId
    }
}
