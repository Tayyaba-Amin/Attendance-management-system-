// service/StudentService.java
package service;

import model.*;
import java.util.*;

public class StudentService {
    private AttendanceService attendanceService;

    public StudentService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public Student getStudentByRollNo(int rollNo) {
        for (Student student : attendanceService.getStudents()) {
            if (student.getRollNo() == rollNo) {
                return student;
            }
        }
        return null;
    }

    public List<AttendanceRecord> getAttendanceRecordsForStudent(Student student) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord record : attendanceService.getAttendanceRecords()) {
            if (record.getAttendance().containsKey(student)) {
                result.add(record);
            }
        }
        return result;
    }
}