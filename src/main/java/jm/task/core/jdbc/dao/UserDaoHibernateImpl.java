package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaQuery;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String str = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (35) NOT NULL, " +
                    "lastName VARCHAR (35) NOT NULL, " +
                    "age INT NOT NULL)";
            session.createSQLQuery(str).executeUpdate();    //
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users ").executeUpdate();
            transaction.commit();
        }
        catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
                session.save(new User(name, lastName, age));
                transaction.commit();
                System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
            try (Session session = Util.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                users = session.createQuery("FROM User").getResultList();
                transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
}
