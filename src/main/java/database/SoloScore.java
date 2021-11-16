package database;

import javax.persistence.*;

@Entity
@Table(name = "SoloScore")
public class SoloScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long SoloScore_Id;
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

    public long getSoloScore_Id() {
        return SoloScore_Id;
    }

    public void setSoloScore_Id(long soloScore_Id) {
        SoloScore_Id = soloScore_Id;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
