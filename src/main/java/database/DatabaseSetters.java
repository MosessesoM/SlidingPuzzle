package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetters {
    public void setUser(String name, String password, String email){
        User user = new User();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        user.setSoloScores(null);
//        user.setCoopScores(null);
//        user.setVsScores(null);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManagerFactory.close();
        entityManager.close();
    }

    public void setSoloScore(User user, Integer score){
        SoloScore soloScore = new SoloScore();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        soloScore.setScore(score);

//        if (user.getSoloScores()==null){
//            List<SoloScore> soloScores = new ArrayList<SoloScore>();
//            soloScores.add(soloScore);
//            user.setSoloScores(soloScores);
//        } else {
//            user.getSoloScores().add(soloScore);
//        }
        List<SoloScore> soloScores = new ArrayList<>();
        soloScores.add(soloScore);
        soloScore.setUser(user);

        user.setSoloScores(soloScores);

        entityManager.getTransaction().begin();
        entityManager.persist(soloScore);
        entityManager.merge(user);
        entityManager.getTransaction().commit();

        entityManagerFactory.close();
        entityManager.close();
    }

    public void setVsScore(User user, Integer score){
        VsScore vsScore = new VsScore();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        vsScore.setScore(score);

//        if (user.getSoloScores()==null){
//            List<SoloScore> vsScores = new ArrayList<SoloScore>();
//            vsScores.add(vsScore);
//            user.setSoloScores(vsScores);
//        } else {
//            user.getSoloScores().add(vsScore);
//        }
        List<VsScore> vsScores = new ArrayList<>();
        vsScores.add(vsScore);
        vsScore.setUser(user);

        user.setVsScores(vsScores);

        entityManager.getTransaction().begin();
        entityManager.persist(vsScore);
        entityManager.merge(user);
        entityManager.getTransaction().commit();

        entityManagerFactory.close();
        entityManager.close();
    }
}
