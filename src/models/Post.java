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
            name = "checkRegisteredUser_id",
            query = "SELECT COUNT(p) FROM Post AS p WHERE p.user_id = :user_id"
    )
})
@Entity
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer user_id;

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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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
