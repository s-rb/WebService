package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserProfile implements Serializable { // Serializable Important to Hibernate!

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(256)")
    private String login;

    @Column(columnDefinition = "VARCHAR(256)")
    private String password;

    public UserProfile() { // Пустой нужен для Hibernate
    }

    public UserProfile(String login, String password) {
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
