package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl extends Util implements UserDao {

    private static Session session;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS user " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "last_name VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = getSessionFactory().openSession();

            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);

            session.getTransaction().commit();
            System.out.println("User с именем – " + user.getName() + " добавлен в базу данных");
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();

            User user = session.load(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List userList = new ArrayList();
        try {

            session = getSessionFactory().openSession();
            session.beginTransaction();

            userList = session.createQuery("FROM User").list();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
