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
}
