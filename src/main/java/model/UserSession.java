package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class UserSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(256)")
    private String login;

    @Column(name = "session_id", unique = true, updatable = false, columnDefinition = "VARCHAR(256)")
    private String sessionId;

    public UserSession() {
    }

    public UserSession(String login, String sessionId) {
        this.login = login;
        this.sessionId = sessionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
