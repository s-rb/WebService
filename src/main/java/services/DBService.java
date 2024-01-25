package services;

import DAO.UserProfileDAO;
import DAO.UserSessionDAO;
import model.UserProfile;
import model.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;

public class DBService {

    public static final String DB_HOST = "localhost";
    public static final String DB_NAME = "webservice";
    public static final String DB_USER = "mysqladmin";
    public static final String DB_PASSWORD = "mysqlpassword";

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    private final SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME + "?serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", DB_USER);
        configuration.setProperty("hibernate.connection.password", DB_PASSWORD);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    @SuppressWarnings("UnusedDeclaration")
    private Configuration getH2Conxfigxuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    public UserProfile getUser(long id) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UserProfileDAO dao = new UserProfileDAO(session);
            UserProfile dataSet = dao.get(id);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public long addUser(UserProfile userProfile) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserProfileDAO dao = new UserProfileDAO(session);
            long id = dao.insertUser(userProfile.getLogin(), userProfile.getPassword());
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public UserProfile getUserByLogin(String login) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UserProfileDAO dao = new UserProfileDAO(session);
            UserProfile dataSet = dao.getUserByLogin(login);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public UserSession getUserSession(String sessionId) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            UserSessionDAO dao = new UserSessionDAO(session);
            UserSession dataSet = dao.getUserSessionBySessionId(sessionId);
            session.close();
            return dataSet;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void addSession(String sessionId, UserProfile userProfile) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserSessionDAO dao = new UserSessionDAO(session);
            long id = dao.insertSession(sessionId, userProfile.getLogin());
            transaction.commit();
            session.close();
//            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void deleteSession(String sessionId) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserSessionDAO dao = new UserSessionDAO(session);
            dao.deleteSession(sessionId);
            transaction.commit();
            session.close();
//            return id;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void deleteUserProfile(UserProfile userProfile) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserProfileDAO dao = new UserProfileDAO(session);
            dao.deleteUserProfile(userProfile);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void changeLoginBySessionId(UserProfile newUserProfile, String sessionId) throws DBException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserSessionDAO dao = new UserSessionDAO(session);
            dao.changeLoginBySessionId(newUserProfile, sessionId);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }
}
