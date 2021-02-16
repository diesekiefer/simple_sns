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

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
        name = "getAllFollowsbyFrom_userId",
        query = "SELECT f FROM Follow AS f WHERE f.from_userId = :from_userId ORDER BY f.id DESC"
    ),
    @NamedQuery(
        name = "getAllFollowsbyTo_userId",
        query = "SELECT f FROM Follow AS f WHERE f.to_userId = :to_userId ORDER BY f.id DESC"
    ),
    @NamedQuery(
        name = "getFollowsCountbyFrom_userId",
        query = "SELECT COUNT(f) FROM Follow AS f WHERE f.from_userId = :from_userId"
    ),
    @NamedQuery(
        name = "getFollowsCountbyTo_userId",
        query = "SELECT COUNT(f) FROM Follow AS f WHERE f.to_userId = :to_userId"
    ),
    @NamedQuery(
        name = "getFollowbyUsers",
        query = "SELECT f FROM Follow as f WHERE f.from_userId = :from_userId and f.to_userId = :to_userId"
    ),
    @NamedQuery(
        name = "getFollowsCountbyUsers",
        query = "SELECT COUNT(f) FROM Follow AS f WHERE f.from_userId = :from_userId and f.to_userId = :to_userId"
    )
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "from_userId", nullable = false)
    private Integer from_userId;

    @Column(name = "to_userId", nullable = false)
    private Integer to_userId;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom_userId() {
        return from_userId;
    }

    public void setFrom_userId(Integer from_userId) {
        this.from_userId = from_userId;
    }

    public Integer getTo_userId() {
        return to_userId;
    }

    public void setTo_userId(Integer to_userId) {
        this.to_userId = to_userId;
    }
}
