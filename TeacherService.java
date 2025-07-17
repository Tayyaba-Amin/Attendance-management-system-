// service/TeacherService.java
package service;

import model.*;
import java.util.*;

public class TeacherService {
    private AttendanceService attendanceService;

    public TeacherService(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public AttendanceRecord markAttendance(Subject subject, Map<Student, Character> attendanceData) {
        AttendanceRecord record = new AttendanceRecord(subject, new Date());
        for (Map.Entry<Student, Character> entry : attendanceData.entrySet()) {
            record.markAttendance(entry.getKey(), entry.getValue());
        }
        attendanceService.addAttendanceRecord(record);
        return record;
    }

    public List<AttendanceRecord> getAttendanceRecordsForSubject(Subject subject) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord record : attendanceService.getAttendanceRecords()) {
            if (record.getSubject().equals(subject)) {
                result.add(record);
            }
        }
        return result;
    }
}