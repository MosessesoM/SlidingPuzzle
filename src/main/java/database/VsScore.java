package database;

import javax.persistence.*;

@Entity
@Table(name = "VsScore")
public class VsScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long VsScore_Id;
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

    public long getVsScore_Id() {
        return VsScore_Id;
    }

    public void setVsScore_Id(long vsScore_Id) {
        VsScore_Id = vsScore_Id;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
