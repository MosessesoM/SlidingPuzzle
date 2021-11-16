package database;

import javax.persistence.*;

@Entity
@Table(name = "CoopScore")
public class CoopScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long CoopScore_Id;
    @Column(columnDefinition = "INT NOT NULL")
    private int Score;

    @ManyToOne
    @JoinColumn(name = "User_id",nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCoopScore_Id() {
        return CoopScore_Id;
    }

    public void setCoopScore_Id(long coopScore_Id) {
        CoopScore_Id = coopScore_Id;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
