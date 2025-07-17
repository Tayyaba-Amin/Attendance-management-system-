// model/Teacher.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private String name;
    private String password;
    private List<Subject> subjects;

    public Teacher(String name, String password) {
        this.name = name;
        this.password = password;
        this.subjects = new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public String getPassword() { return password; }
    public List<Subject> getSubjects() { return subjects; }

    // Subject management
    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
            subject.setAssignedTeacher(this);
        }
    }

    public void removeSubject(Subject subject) {
        if (subjects.contains(subject)) {
            subjects.remove(subject);
            subject.setAssignedTeacher(null);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}