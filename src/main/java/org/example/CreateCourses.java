package org.example;

import org.hibernate.Session;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;


public class CreateCourses {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Courses> listCourses = null;

        Courses calculus = new Courses(001, "apma", "1001");
        listCourses.add(calculus);

        Courses cso = new Courses(002, "cs", "2150");
        listCourses.add(cso);

        session.persist(calculus);
        session.persist(cso);

        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();

    }
}
