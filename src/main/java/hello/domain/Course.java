package hello.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id @GeneratedValue
    private int id;
    private String title;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Statistics> statisticsList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Instructor> instructors = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Statistics> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<Statistics> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }
}
