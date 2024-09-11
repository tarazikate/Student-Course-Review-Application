package org.example;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COURSES")
public class Courses {

    @Id // this sets the id to be the primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // makes it auto increment
    @Column (name = "ID", nullable = false)
    private int id;

    @Column(name = "DEPARTMENT", nullable = false)
    private String department;

    @Column(name = "CATALOG_NUMBER", nullable = false)
    private String catalogNumber; // in the doc the cat num is in " "

    public Courses(int id, String department, String catalogNumber) {
        this.id = id;
        this.department = department;
        this.catalogNumber = catalogNumber;
    }

//    @OneToMany(mappedBy = "courses")
//    private List<Students> listCourses;
    public Courses() {
        //a zero-argument constructor is required for hibernate to work correctly
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getCatalogNumber() {
        return catalogNumber;
    }
    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    @Override
    public String toString() {
        return "Course {" +
                "id =" + id +
                ", department ='" + department + '\'' +
                ", catalogNumber =" + catalogNumber + '}';
    }
}
