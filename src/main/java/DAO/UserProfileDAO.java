package DAO;

import model.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserProfileDAO {

    private Session session;

    public UserProfileDAO(Session session) {
        this.session = session;
    }

    public UserProfile get(long id) throws HibernateException { // Найти в базе профиль по id
        return (UserProfile) session.get(UserProfile.class, id);
    }

    public long getUserId(String login) throws HibernateException { // получить id по логину
        Criteria criteria = session.createCriteria(UserProfile.class);
        return ((UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult()).getId();
    }

    public long insertUser(String login, String password) throws HibernateException { // добавить пользователя в базу
            return (Long) session.save(new UserProfile(login, password));
    }

    public UserProfile getUserByLogin(String login) {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return ((UserProfile) criteria.add(Restrictions.eq("login", login)).uniqueResult());
    }

    public void deleteUserProfile(UserProfile userProfile) {
        session.delete(userProfile);
    }
}
