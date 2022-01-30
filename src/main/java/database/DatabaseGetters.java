package database;

import javax.persistence.*;
import java.util.List;

public class DatabaseGetters {

    public User getUser(String name, String password){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query=entityManager.createQuery("FROM User c WHERE c.Name LIKE:Name AND c.Password LIKE:Password");
        query.setParameter("Name",name);
        query.setParameter("Password",password);
        List<User> users;
        users=query.getResultList();

        if (users.size()==1){
            entityManager.close();
            entityManagerFactory.close();
            return users.get(0);
        }
        else {
            entityManager.close();
            entityManagerFactory.close();
            return null;
        }
    }

    public List<User> getUsers(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("From User c");
        List<User> users = query.getResultList();

        entityManager.close();
        entityManagerFactory.close();
        return users;

    }

    public List<SoloScore> getSoloScores(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("From SoloScore c");
        List<SoloScore> soloScores = query.getResultList();

        entityManager.close();
        entityManagerFactory.close();
        return soloScores;
    }

    public List<VsScore> getVsScores(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("From VsScore c");
        List<VsScore> vsScores = query.getResultList();

        entityManager.close();
        entityManagerFactory.close();
        return vsScores;
    }

    public User getUserById(long id){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createQuery("FROM User c WHERE c.User_id LIKE:id");
        query.setParameter("id",id);
        List<User> users=null;
        users = query.getResultList();

        for (User user:users){
            return user;
        }

        return null;
    }
}
