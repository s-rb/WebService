package DAO;

import model.UserProfile;
import model.UserSession;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserSessionDAO {

    private Session session;

    public UserSessionDAO(Session session) {
        this.session = session;
    }

    public UserSession get(long id) throws HibernateException { // Найти в базе сессию по id
        return (UserSession) session.get(UserSession.class, id);
    }

    public long getSessionIdByLogin(String login) throws HibernateException { // получить id по логину
        Criteria criteria = session.createCriteria(UserSession.class);
        return ((UserSession) criteria.add(Restrictions.eq("login", login)).uniqueResult()).getId();
    }

    public UserSession getUserSessionByLogin(String login) throws HibernateException { // получить сессию по логину
        Criteria criteria = session.createCriteria(UserSession.class);
        return ((UserSession) criteria.add(Restrictions.eq("login", login)).uniqueResult());
    }

    public UserSession getUserSessionBySessionId(String sessionId) {
        Criteria criteria = session.createCriteria(UserSession.class);
        return ((UserSession) criteria.add(Restrictions.eq("sessionId", sessionId)).uniqueResult());
    }

    public long insertSession(String sessionId, String login) { // Без проверки добавляем логин и пароль
            return (Long) session.save(new UserSession(login, sessionId));
    }

    public void deleteSession(String sessionId) {
        // Получаем объект и потом удаляем его
        UserSession userSession = getUserSessionBySessionId(sessionId);
        session.delete(userSession);
    }

    public void changeLoginBySessionId(UserProfile newUserProfile, String sessionId) { // delete and create new
        deleteSession(sessionId);
        insertSession(sessionId, newUserProfile.getLogin());
    }
}