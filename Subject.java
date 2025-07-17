// model/Subject.java
package model;

public class Subject {
    private String name;
    private Teacher assignedTeacher;

    public Subject(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() { return name; }
    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public void setAssignedTeacher(Teacher teacher) { this.assignedTeacher = teacher; }

    @Override
    public String toString() {
        return name;
    }
}