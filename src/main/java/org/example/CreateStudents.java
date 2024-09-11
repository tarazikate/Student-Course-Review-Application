package org.example;

import org.hibernate.Session;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class CreateStudents {
    public static void main(String[] args){
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        int studentIdToDelete = 46;

//        try {
//            session.beginTransaction();

            // retrieve the student object using the ID
            Students studentToDelete = session.get(Students.class, studentIdToDelete);

            // delete the student from the database
            session.delete(studentToDelete);

           // session.getTransaction().commit();

            System.out.println("Student deleted successfully");
//        } catch (Exception e) {
//            System.out.println("An exception occurred: " + e.getMessage());
           // session.getTransaction().rollback();
      //  }




        //List<Students> listStudents = null;

        Students bob = new Students();
       // bob.setId(1);
        bob.setName("abc");
        bob.setPassword("123");

        Students amy = new Students();
        amy.setName("Amy_Zinn");
        amy.setPassword("bye");

        Students chris = new Students();
        chris.setName("Chris_James");
        chris.setPassword("dogs");


        //        session.persist(bob);
//        session.persist(amy);
//        session.persist(chris);
//       // session.persist(bob);
//        System.out.println(bob.toString());
//        System.out.println(amy.toString());
//        System.out.println(chris.toString());
       // session.createSQLQuery("DELETE FROM Students").executeUpdate();
//        Query query = session.createQuery("DELETE FROM Students");
//        int deletedCount = query.executeUpdate();

//        Query query2 = session.createQuery("from Students");
//        List<Students> students = query2.getResultList();
//        for (Students student : students) {
//            System.out.println(" List is null" + student);
//        }
//        System.out.println(" List is null" );


        //session.persist(amy);
       // session.persist(chris);

        session.getTransaction().commit();
        session.close();
        HibernateUtil.shutdown();

    }
}
