package org.example;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "REVIEWS")
public class Reviews
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE) // this was in xml but i used it to figure out how to do it for this https://docs.jboss.org/hibernate/orm/4.2/manual/en-US/html_single/
    @JoinColumn(name = "STUDENT",referencedColumnName = "ID", nullable = false)
    private Students studentID;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "COURSE", referencedColumnName = "ID", nullable = false)
    private Courses courseID;


    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "RATING", columnDefinition = "INT CHECK (RATING BETWEEN 1 AND 5)", nullable = false) // used to define CHECK : https://docs.oracle.com/javadb/10.8.3.0/ref/rrefsqlj13590.html
    // columnDefinition default: Generated SQL to create a column of the inferred type. https://stackoverflow.com/questions/16078681/what-properties-does-column-columndefinition-make-redundant#:~:text=columnDefinition%20definition%3A%20The%20SQL%20fragment,column%20of%20the%20inferred%20type.
    private int rating;

//    @OneToMany(mappedBy = "review")
//    private List<Transaction> transactionList;

//    public Account() {
//        transactionList = new ArrayList<>();
//   }

    public Reviews() { // do i need to create a constructor like i did for course and student
        // or do i need to make a list like the code example for account

    }

    public Reviews (int id, Students studentId, Courses courseID, String message, int rating )
    {
        this.id = id;
        this.studentID = studentId;
        this.courseID = courseID;
        this.message = message;
        this.rating = rating;

    }
    public int getId() {
         return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Students getStudentID()
    {
        return studentID;
    }

    public void setStudentID(Students studentID)
    {
        this.studentID = studentID;
    }

    public Courses getCourseID()
    {
        return courseID;
    }
    public void setCourseID(Courses courseID)
    {
        this.courseID = courseID;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Reviews {" +
                "id= " + id +
                ", student =" + studentID.getId() +
                ", course=" + courseID.getId() +
                ", message =" + message + ", " +
                "rating =" +  rating + '}';
    }
    //An id number [Auto increment primary key]
    //A foreign-key to the Students Table (the student who wrote the review)
    //A foreign-key to the Courses Table (the course the review is about)
    //A text message from the student reviewing the course
    //A rating from 1-5 (integer) of the course. Your table should disallow other values
    //Modification: A check constraint is encouraged but not required, so long
    // as your course review program never inserts an invalid number into the table.

}
