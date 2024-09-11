package org.example;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.Scanner;

public class Main {
    private static Session session;
    private static Students student = new Students();
    private static Courses course = new Courses();
    private static Reviews review = new Reviews();
    private static int student_id = 3; //at least 3 users
    private static int course_id = 5; //at least 3 users
    private static int review_id = 5; //at least 3 users
    private static Scanner scanner = new Scanner(System.in);
    private static List<Courses> courseList ;
    private static List<Students> studentList ;
    private static List<Reviews> reviewList ;

    Students student_one = new Students(1,"adam","password12");
    Students student_two = new Students(2,"adam","password123");
    Students student_three = new Students(3,"adam","password1");

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);


        session = HibernateUtil.getSessionFactory().openSession();


        session.beginTransaction();
        //  System.out.println(getAllStudentsFromDatabase());
        //1. Login: when someone opens the app, they should be shown options:
        userLoginRegister();
        //2.
        mainMenu();
        session.getTransaction().commit();
        session.close();

    }
//    private static Students getClientFromIdNumber(int idNumber) {
//
//
//    }



    private static void userLoginRegister() {
        // clearReviewTable();
        // deleteStudent();
        // Retrieve all students
//        Query<Students> studentQuery = session.createQuery("FROM Students", Students.class);
//        List<Students> students = studentQuery.getResultList();
//        System.out.println("Students:");
//        for (Students student : students) {
//            System.out.println(student.toString());
//        }
//        Query<Reviews> reviewQuery = session.createQuery("FROM Reviews", Reviews.class);
//        List<Reviews> reviews = reviewQuery.getResultList();
//        for (Reviews review : reviews) {
//            System.out.println(review.toString());
//        }
//        Query<Courses> courseQuery = session.createQuery("FROM Courses", Courses.class);
//        List<Courses> courses = courseQuery.getResultList();
//        for (Courses course : courses) {
//            System.out.println(course.toString());
//        }
        System.out.println("" +
                "Type \"login\" to login to existing user or " +
                "Type \"register\" to create a new user?");
        String input = scanner.nextLine();
        if (input.toLowerCase().equals("login")) {
            userLogin();
        } else if (input.toLowerCase().equals("register")) {
            userRegister();
        } else {
            System.out.println("Invalid input. Please try again.");
            userLoginRegister();
        }
    }

    private static void userLogin() {
        studentList = getAllStudentsFromDatabase();
        System.out.println("Type username");
        String username = scanner.nextLine();
        System.out.println("Type password");
        String password = scanner.nextLine();
        for (Students i : studentList) {
            if (username.equals(i.getUsername()) && password.equals(i.getPassword())) {
                student = session.get(Students.class, i.getId());
                mainMenu();
            }
        }
        System.out.println("Invalid username and password. Please try again.");
        userLoginRegister();
    }

    private static void userRegister() {
        System.out.println("Create username");
        String username = scanner.nextLine();
        System.out.println("Create password");
        String password = scanner.nextLine();
        System.out.println("Confirm password");
        String confirmPassword = scanner.nextLine();

        // Check if username already exists
        Query query = session.createQuery("FROM Students WHERE username = :username");
        query.setParameter("username", username);
        List<Students> students = query.list();

        if (!students.isEmpty()) {
            System.out.println("Username already exists. Please try again.");
            userRegister();
            return;
        }

        if (confirmPassword.equals(password)) {
            student = new Students();
            student.setName(username);
            student.setPassword(password);

            try {
                // Close any open transactions
                if (session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                }
                session.beginTransaction();
                session.save(student);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("An exception occurred: " + e.getMessage());
                session.getTransaction().rollback();
            }
        } else {
            System.out.println("Passwords do not match. Please try again.");
            userLoginRegister();
        }
    }

    private static void mainMenu() {
        System.out.println(
                "Type \"submit\" to submit a review for a course or \n " +
                        "Type \"see\" to see reviews for a course or \n " +
                        "Type \"logout\" to return to the login page");
        String input = scanner.nextLine();
        if (input.equals("submit")) {
            //session.getTransaction().commit();
            submitReview();
        } else if (input.equals("see")) {
            seeReview();
        } else if (input.equals("logout")) {
            logout();
        } else {
            System.out.println("Invalid input. Please try again.");
            mainMenu();
        }
    }


    private static void submitReview() {

        System.out.println("Type course name (example: \"CS 3140\")");
        String courseName = scanner.nextLine();
        String[] tokens = courseName.split(" ");
        if (tokens.length != 2 || !tokens[0].toUpperCase().matches("[A-Z]{1,4}") || !tokens[1].matches("\\d{4}")) {
            System.out.println("Invalid course name. Please try again.");
            mainMenu();
        } else {
            String subject = tokens[0].toUpperCase();
            String catalogNumber = tokens[1];
            courseList = getAllCoursesFromDatabase();
            boolean courseExists = false;
            for (Courses i : courseList) {
                if (subject.equals(i.getDepartment()) && catalogNumber.equals(i.getCatalogNumber())) {
                    course = session.get(Courses.class, i.getId());
                    courseExists = true;
                    break;
                }
            }
            if (!courseExists) {
                course = new Courses(); // create a new Courses object
                course.setDepartment(subject);
                course.setCatalogNumber(catalogNumber);
                try {
                    session.beginTransaction();
                    session.save(course);
                    session.getTransaction().commit();
                }
                catch (Exception e) {
                    session.getTransaction().rollback();
                }
//
            }
            reviewList = getAllReviewsFromDatabase();
            boolean hasReviewed = false;
            for (Reviews j: reviewList) {
                if (j.getStudentID().equals(student) && j.getCourseID().equals(course)){
                    hasReviewed = true;
                    break;
                }
            }
            if (hasReviewed) {
                System.out.println("You have already reviewed this course. Please try again.");
                mainMenu();
            } else {
                reviewMessagePrompt();
            }
        }
    }
    private static void reviewMessagePrompt() {
        System.out.println("Type a message that is one line long");
        String reviewInput = scanner.nextLine();
        getRating(reviewInput);
    }

    private static void getRating(String reviewInput) {
        System.out.println("Review the course from a number 1-5");
        int rating = scanner.nextInt();
        if (rating == 1 || rating == 2 || rating == 3 || rating == 4 || rating == 5) {
            //save review and rating to table
            review = new Reviews();
            review.setStudentID(student);
            review.setCourseID(course);
            review.setMessage(reviewInput);
            review.setRating(rating);

            try {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().commit();
                }
                session.beginTransaction();
                session.save(review);
                session.getTransaction().commit();
            } catch (Exception e) {
                System.out.println("An exception occurred: " + e.getMessage());
                session.getTransaction().rollback();
            }

            mainMenu();


//            Query query = session.createQuery("from Reviews ");
//            List<Reviews> revs = query.getResultList();
//            System.out.println("------ REVIEWS ------- " );
//
//            for (Reviews rev : revs) {
//                System.out.println(rev);
//            }


        } else {
            System.out.println("Invalid rating. Please Type Again");
            getRating(reviewInput);
        }
    }
//


    private static void seeReview() {
        System.out.println("Type course name (example: \"CS 3140\")");
        String courseName = scanner.nextLine();
        String[] tokens = null;
        String catalogNumber = "";
        String subject ="";
        //String[] tokens = courseName.split(" "); // said we can assume there will be a space . does that mean we need to catch if there isnt a space
        if (courseName.contains(" ")) {
            tokens = courseName.split(" ");
            subject = tokens[0].toUpperCase();
            catalogNumber = tokens[1];
        }
        else
        {
            tokens = new String[]{courseName};
            subject = tokens[0].toUpperCase();
        }
        if (tokens.length != 2 || !(subject.matches("[A-Z]{1,4}")) || !(catalogNumber.matches("\\d{4}"))) {
            System.out.println("Invalid course name. Please try again.");
            seeReview();
        } else {
            courseList = getAllCoursesFromDatabase();
            boolean found = false;
            for (Courses i : courseList) {
                if (subject.equals(i.getDepartment()) && catalogNumber.equals(i.getCatalogNumber())) {
                    course = session.get(Courses.class, i.getId());
                    int reviewCount = 0;
                    int reviewTotal = 0;
                    reviewList = getAllReviewsFromDatabase();
                    for (Reviews j : reviewList) {
                        if (j.getCourseID().equals(course)) {
                            System.out.println(j.getMessage());
                            reviewTotal += j.getRating();
                            reviewCount++;
                        }
                    }
                    if (reviewCount > 0) {
                        System.out.println("Course Average " + reviewTotal / reviewCount + "/5");
                    } else {
                        System.out.println("The course has no reviews");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("The course does not exist");
            }
            mainMenu();
        }
    }
    private static void logout() {
        userLoginRegister();
    }

    static List<Students> getAllStudentsFromDatabase() { //
        String allStudents = "FROM Students"; //Note that this uses the CLASS name, not the table name CLIENTS
        Query<Students> query = session.createQuery(allStudents, Students.class);
        return query.getResultList();
    }

    static List<Courses> getAllCoursesFromDatabase() { //
        String allCourses = "FROM Courses"; //Note that this uses the CLASS name, not the table name CLIENTS
        Query<Courses> query = session.createQuery(allCourses, Courses.class);
        return query.getResultList();
    }

    static List<Reviews> getAllReviewsFromDatabase() { //
        String allReviews = "FROM Reviews"; //Note that this uses the CLASS name, not the table name CLIENTS
        Query<Reviews> query = session.createQuery(allReviews, Reviews.class);
        return query.getResultList();
    }
}

