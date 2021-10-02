package database;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long User_Id;
    @Column(columnDefinition = "BOOLEAN NOT NULL")
    private boolean Permission;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String Name;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL")
    private String Email;
    @Column(columnDefinition = "VARCHAR(30) NOT NUL")
    private String Password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_Id")
    private List<SoloScore> soloScores;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_Id")
    private List<CoopScore> coopScores;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "User_Id")
    private List<VsScore> vsScores;


    public long getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(long user_Id) {
        User_Id = user_Id;
    }

    public boolean isPermission() {
        return Permission;
    }

    public void setPermission(boolean permission) {
        Permission = permission;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<SoloScore> getSoloScores() {
        return soloScores;
    }

    public void setSoloScores(List<SoloScore> soloScores) {
        this.soloScores = soloScores;
    }

    public List<CoopScore> getCoopScores() {
        return coopScores;
    }

    public void setCoopScores(List<CoopScore> coopScores) {
        this.coopScores = coopScores;
    }

    public List<VsScore> getVsScores() {
        return vsScores;
    }

    public void setVsScores(List<VsScore> vsScores) {
        this.vsScores = vsScores;
    }
}
