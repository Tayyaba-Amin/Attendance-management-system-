// service/AdminService.java
package service;

import model.*;
import java.util.*;

public class AdminService {
    private AttendanceService attendanceService;

    public AdminService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void addStudent(String name, int rollNo, List<Subject> selectedSubjects) {
        Student student = new Student(name, rollNo);
        for (Subject subject : selectedSubjects) {
            student.addSubject(subject);
        }
        attendanceService.addStudent(student);
    }

    public void addSubject(String name) {
        attendanceService.addSubject(new Subject(name));
    }

    public void assignSubjectsToTeacher(Teacher teacher, List<Subject> subjectsToAssign) {
        // First remove all current assignments from this teacher
        List<Subject> currentSubjects = new ArrayList<>(teacher.getSubjects());
        for (Subject subject : currentSubjects) {
            teacher.removeSubject(subject);
        }

        // Assign new subjects
        for (Subject subject : subjectsToAssign) {
            // Remove from any other teacher first
            Teacher currentTeacher = subject.getAssignedTeacher();
            if (currentTeacher != null) {
                currentTeacher.removeSubject(subject);
            }
            teacher.addSubject(subject);
        }
    }
}