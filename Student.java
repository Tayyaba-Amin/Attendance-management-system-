// model/Student.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private int rollNo;
    private List<Subject> subjects;

    public Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
        this.subjects = new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public int getRollNo() { return rollNo; }
    public List<Subject> getSubjects() { return subjects; }

    // Subject management
    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    @Override
    public String toString() {
        return rollNo + ". " + name;
    }
}