package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "users")
@NamedQueries({
    @NamedQuery(
        name = "getAllUsers",
        query = "SELECT u FROM User AS u ORDER BY u.id DESC"
    ),
    @NamedQuery(
        name = "getUsersCount",
        query = "SELECT COUNT(u) FROM User AS u"
    ),
    @NamedQuery(
        name = "checkRegisteredLogin_id",
        query = "SELECT COUNT(u) FROM User AS u WHERE u.login_id = :login_id"
    ),
    @NamedQuery(
        name = "checkLoginIdAndPassword",
        query = "SELECT u FROM User AS u WHERE u.login_id = :id AND u.password = :pass"
    )
})
@Entity
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login_id", nullable = false, unique = true)
    private String login_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
