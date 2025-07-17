// model/AttendanceRecord.java
package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRecord {
    private Subject subject;
    private Date date;
    private Map<Student, Character> attendance; // 'P' or 'A'

    public AttendanceRecord(Subject subject, Date date) {
        this.subject = subject;
        this.date = date;
        this.attendance = new HashMap<>();
    }

    // Getters
    public Subject getSubject() { return subject; }
    public Date getDate() { return date; }
    public Map<Student, Character> getAttendance() { return attendance; }

    // Attendance management
    public void markAttendance(Student student, char status) {
        attendance.put(student, Character.toUpperCase(status));
    }

    public char getStudentAttendance(Student student) {
        return attendance.getOrDefault(student, 'A');
    }
}