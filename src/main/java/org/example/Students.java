package org.example;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "ID", nullable = false)
    private int id;


    @Column (name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column (name = "PASSWORD", nullable = false)
    private String password;
    public Students(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

//    @OneToMany(mappedBy = "reviews")
//    private List<Students> listStudents;

    public Students() {
        //a zero-argument constructor is required for hibernate to work correctly
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student {" +
                "id =" + id +
                ", username ='" + username + '\'' +
                ", password =" + password + '}';
    }
}
