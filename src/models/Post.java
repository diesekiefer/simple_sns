package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "posts")
@NamedQueries({
    @NamedQuery(
        name = "getAllPosts",
        query = "SELECT p FROM Post AS p ORDER BY p.id DESC"
    ),
    @NamedQuery(
        name = "getPostsCount",
        query = "SELECT COUNT(p) FROM Post AS p"
    ),
    @NamedQuery(
        name = "getAllPostsbyUser",
        query = "SELECT p FROM Post AS p WHERE p.user = :user_id ORDER BY p.id DESC"
    ),
    @NamedQuery(
        name = "checkRegisteredUser_id",
        query = "SELECT COUNT(p) FROM Post AS p WHERE p.user = :user_id"
    )
})
@Entity
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", length=140, nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
