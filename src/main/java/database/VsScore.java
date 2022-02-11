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
    @Column(columnDefinition = "INT NOT NULL")
    private int Moves;
    @Column(columnDefinition = "INT NOT NULL")
    private int Time;
    @Column(columnDefinition = "INT NOT NULL")
    private int Level;

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

    public int getMoves() {
        return Moves;
    }

    public void setMoves(int moves) {
        Moves = moves;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }
}
