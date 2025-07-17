// service/AttendanceService.java
package service;

import model.*;
import java.util.*;

public class AttendanceService {
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Subject> subjects;
    private List<AttendanceRecord> attendanceRecords;

    public AttendanceService() {
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.attendanceRecords = new ArrayList<>();
        initializeDefaultData();
    }

    private void initializeDefaultData() {
        // Add default students
        String[] defaultStudentNames = {"Ali", "sajjad", "usman", "haseeb", "awais", 
                                      "hussnain", "asad", "amjad", "hassan", "saqib"};
        for (int i = 0; i < defaultStudentNames.length; i++) {
            students.add(new Student(defaultStudentNames[i], i+1));
        }

        // Add default subjects
        String[] defaultSubjectNames = {"Linear Algebra", "Software Engineering", 
                                      "Object Oriented Programming", "Digital Logic Design", 
                                      "Discrete Structure", "Cyber Security"};
        for (String name : defaultSubjectNames) {
            subjects.add(new Subject(name));
        }

        // Add default teachers
        teachers.add(new Teacher("asad", "t1"));
        teachers.add(new Teacher("atif", "t2"));
        teachers.add(new Teacher("hassan", "t3"));

        // Assign default subjects to teachers
        teachers.get(0).addSubject(subjects.get(0)); // Linear Algebra
        teachers.get(0).addSubject(subjects.get(1)); // Software Engineering
        teachers.get(1).addSubject(subjects.get(2)); // OOP
        teachers.get(1).addSubject(subjects.get(3)); // DLD
        teachers.get(2).addSubject(subjects.get(4)); // Discrete Structure
        teachers.get(2).addSubject(subjects.get(5)); // Cyber Security
    }

    // Getters for collections
    public List<Student> getStudents() { return students; }
    public List<Teacher> getTeachers() { return teachers; }
    public List<Subject> getSubjects() { return subjects; }
    public List<AttendanceRecord> getAttendanceRecords() { return attendanceRecords; }

    // Add new entities
    public void addStudent(Student student) { students.add(student); }
    public void addSubject(Subject subject) { subjects.add(subject); }
    public void addAttendanceRecord(AttendanceRecord record) { attendanceRecords.add(record); }
}