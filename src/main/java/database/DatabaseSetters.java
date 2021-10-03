package database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseSetters {
    public void setUser(String name, String password, String email){
        User user = new User();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        user.setSoloScores(null);
        user.setCoopScores(null);
        user.setVsScores(null);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManagerFactory.close();
        entityManager.close();
    }
}
