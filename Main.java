// Main.java
import model.*;
import service.*;
import util.*;
import java.util.*;

public class Main {
    private static AttendanceService attendanceService = new AttendanceService();
    private static AdminService adminService = new AdminService(attendanceService);
    private static TeacherService teacherService = new TeacherService(attendanceService);
    private static StudentService studentService = new StudentService(attendanceService);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Attendance Management System =====");
            System.out.println("1. Admin");
            System.out.println("2. Teacher");
            System.out.println("3. See Attendance");
            System.out.println("4. Exit");
            
            int choice = InputUtil.getIntInput("Select an option: ");
            
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    teacherLogin();
                    break;
                case 3:
                    viewAttendanceAsStudent();
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adminLogin() {
        String username = InputUtil.getStringInput("Enter admin username: ");
        String password = InputUtil.getStringInput("Enter admin password: ");

        if (username.equals("ali") && password.equals("admin")) {
            adminMenu();
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Subject");
            System.out.println("3. Assign Subject to Teacher");
            System.out.println("4. View Attendance");
            System.out.println("5. Back to Main Menu");
            
            int choice = InputUtil.getIntInput("Select an option: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addSubject();
                    break;
                case 3:
                    assignSubjectToTeacher();
                    break;
                case 4:
                    viewAttendance();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.println("\n===== Add Student =====");
        
        // Display existing students
        System.out.println("Existing Students:");
        for (Student student : attendanceService.getStudents()) {
            System.out.println(student);
        }

        String name = InputUtil.getStringInput("\nEnter student name: ");
        int rollNo = InputUtil.getIntInput("Enter roll number (must not be 1-10): ");

        // Validate roll number
        if (rollNo >= 1 && rollNo <= 10) {
            System.out.println("Roll number must not be between 1-10. These are reserved for default students.");
            return;
        }

        // Check if roll number exists
        for (Student student : attendanceService.getStudents()) {
            if (student.getRollNo() == rollNo) {
                System.out.println("Student with this roll number already exists.");
                return;
            }
        }

        // Select subjects
        System.out.println("\nAvailable Subjects:");
        List<Subject> subjects = attendanceService.getSubjects();
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i+1) + ". " + subjects.get(i));
        }

        List<Integer> selectedIndices = InputUtil.getMultipleChoiceInput(
            "Select subjects (comma separated numbers): ", subjects.size());
        
        List<Subject> selectedSubjects = new ArrayList<>();
        for (int index : selectedIndices) {
            selectedSubjects.add(subjects.get(index));
        }

        adminService.addStudent(name, rollNo, selectedSubjects);
        System.out.println("Student added successfully!");
    }

    private static void addSubject() {
        System.out.println("\n===== Add Subject =====");
        
        // Display existing subjects
        System.out.println("Existing Subjects:");
        List<Subject> subjects = attendanceService.getSubjects();
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i+1) + ". " + subjects.get(i));
        }

        String name = InputUtil.getStringInput("\nEnter new subject name: ");

        // Check if subject exists
        for (Subject subject : subjects) {
            if (subject.getName().equalsIgnoreCase(name)) {
                System.out.println("Subject already exists.");
                return;
            }
        }

        adminService.addSubject(name);
        System.out.println("Subject added successfully!");
    }

    private static void assignSubjectToTeacher() {
        System.out.println("\n===== Assign Subject to Teacher =====");
        
        // Display teachers
        System.out.println("Teachers:");
        List<Teacher> teachers = attendanceService.getTeachers();
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i+1) + ". " + teachers.get(i));
        }

        int teacherIndex = InputUtil.getIntInput("\nSelect teacher to assign subjects (by number): ") - 1;

        if (teacherIndex < 0 || teacherIndex >= teachers.size()) {
            System.out.println("Invalid teacher selection.");
            return;
        }

        Teacher teacher = teachers.get(teacherIndex);

        // Display currently assigned subjects
        System.out.println("\nCurrently assigned subjects:");
        List<Subject> teacherSubjects = teacher.getSubjects();
        if (teacherSubjects.isEmpty()) {
            System.out.println("No subjects assigned.");
        } else {
            for (Subject subject : teacherSubjects) {
                System.out.println(subject);
            }
        }

        // Display all available subjects
        System.out.println("\nAll Subjects:");
        List<Subject> allSubjects = attendanceService.getSubjects();
        List<Subject> availableSubjects = new ArrayList<>(allSubjects);
        availableSubjects.removeAll(teacherSubjects);

        for (int i = 0; i < availableSubjects.size(); i++) {
            System.out.println((i+1) + ". " + availableSubjects.get(i));
        }

        List<Integer> selectedIndices = InputUtil.getMultipleChoiceInput(
            "\nSelect subjects to assign (comma separated numbers): ", availableSubjects.size());
        
        List<Subject> subjectsToAssign = new ArrayList<>();
        for (int index : selectedIndices) {
            subjectsToAssign.add(availableSubjects.get(index));
        }

        adminService.assignSubjectsToTeacher(teacher, subjectsToAssign);
        System.out.println("Subjects assigned successfully!");
    }

    private static void viewAttendance() {
        System.out.println("\n===== View Attendance =====");
        
        // Display subjects
        System.out.println("Subjects:");
        List<Subject> subjects = attendanceService.getSubjects();
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i+1) + ". " + subjects.get(i));
        }

        int subjectIndex = InputUtil.getIntInput("\nSelect subject to view attendance (by number): ") - 1;

        if (subjectIndex < 0 || subjectIndex >= subjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }

        Subject subject = subjects.get(subjectIndex);

        // Get attendance records for this subject
        List<AttendanceRecord> records = new ArrayList<>();
        for (AttendanceRecord record : attendanceService.getAttendanceRecords()) {
            if (record.getSubject().equals(subject)) {
                records.add(record);
            }
        }

        if (records.isEmpty()) {
            System.out.println("No attendance records found for this subject.");
            return;
        }

        // Display dates
        System.out.println("\nAttendance Dates:");
        for (int i = 0; i < records.size(); i++) {
            System.out.println((i+1) + ". " + records.get(i).getDate());
        }

        int dateIndex = InputUtil.getIntInput("\nSelect date to view attendance (by number): ") - 1;

        if (dateIndex < 0 || dateIndex >= records.size()) {
            System.out.println("Invalid date selection.");
            return;
        }

        AttendanceRecord record = records.get(dateIndex);

        // Display attendance
        System.out.println("\nAttendance for " + subject + " on " + record.getDate() + ":");
        System.out.println("Roll No\tName\t\tStatus");
        for (Map.Entry<Student, Character> entry : record.getAttendance().entrySet()) {
            System.out.println(entry.getKey().getRollNo() + "\t" + 
                             entry.getKey().getName() + "\t\t" + 
                             (entry.getValue() == 'P' ? "Present" : "Absent"));
        }
    }

    private static void teacherLogin() {
        String username = InputUtil.getStringInput("Enter teacher username: ");
        String password = InputUtil.getStringInput("Enter teacher password: ");

        Teacher teacher = null;
        for (Teacher t : attendanceService.getTeachers()) {
            if (t.getName().equals(username) && t.getPassword().equals(password)) {
                teacher = t;
                break;
            }
        }

        if (teacher != null) {
            teacherMenu(teacher);
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }
    }

    private static void teacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("\n===== Teacher Menu (" + teacher + ") =====");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Back to Main Menu");
            
            int choice = InputUtil.getIntInput("Select an option: ");
            
            switch (choice) {
                case 1:
                    markAttendance(teacher);
                    break;
                case 2:
                    viewAttendanceAsTeacher(teacher);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void markAttendance(Teacher teacher) {
        System.out.println("\n===== Mark Attendance =====");
        
        // Display teacher's subjects
        List<Subject> teacherSubjects = teacher.getSubjects();
        if (teacherSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        System.out.println("Your Subjects:");
        for (int i = 0; i < teacherSubjects.size(); i++) {
            System.out.println((i+1) + ". " + teacherSubjects.get(i));
        }

        int subjectIndex = InputUtil.getIntInput("\nSelect subject to mark attendance (by number): ") - 1;

        if (subjectIndex < 0 || subjectIndex >= teacherSubjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }

        Subject subject = teacherSubjects.get(subjectIndex);

        // Get students for this subject (default students + those who selected it)
        List<Student> students = new ArrayList<>();
        for (Student student : attendanceService.getStudents()) {
            if (student.getRollNo() <= 10 || student.getSubjects().contains(subject)) {
                students.add(student);
            }
        }

        if (students.isEmpty()) {
            System.out.println("No students enrolled in this subject.");
            return;
        }

        // Mark attendance for each student
        Map<Student, Character> attendanceData = new HashMap<>();
        System.out.println("\nMark attendance (P for Present, A for Absent):");
        for (Student student : students) {
            String input = InputUtil.getStringInput(student.getName() + " (" + student.getRollNo() + "): ");
            char status = input.isEmpty() ? 'A' : Character.toUpperCase(input.charAt(0));
            if (status != 'P' && status != 'A') {
                status = 'A'; // default to absent if invalid input
            }
            attendanceData.put(student, status);
        }

        teacherService.markAttendance(subject, attendanceData);
        System.out.println("Attendance marked successfully!");
    }

    private static void viewAttendanceAsTeacher(Teacher teacher) {
        System.out.println("\n===== View Attendance =====");
        
        // Display teacher's subjects
        List<Subject> teacherSubjects = teacher.getSubjects();
        if (teacherSubjects.isEmpty()) {
            System.out.println("No subjects assigned to you.");
            return;
        }

        System.out.println("Your Subjects:");
        for (int i = 0; i < teacherSubjects.size(); i++) {
            System.out.println((i+1) + ". " + teacherSubjects.get(i));
        }

        int subjectIndex = InputUtil.getIntInput("\nSelect subject to view attendance (by number): ") - 1;

        if (subjectIndex < 0 || subjectIndex >= teacherSubjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }

        Subject subject = teacherSubjects.get(subjectIndex);

        // Get attendance records for this subject
        List<AttendanceRecord> records = teacherService.getAttendanceRecordsForSubject(subject);

        if (records.isEmpty()) {
            System.out.println("No attendance records found for this subject.");
            return;
        }

        // Display dates
        System.out.println("\nAttendance Dates:");
        for (int i = 0; i < records.size(); i++) {
            System.out.println((i+1) + ". " + records.get(i).getDate());
        }

        int dateIndex = InputUtil.getIntInput("\nSelect date to view attendance (by number): ") - 1;

        if (dateIndex < 0 || dateIndex >= records.size()) {
            System.out.println("Invalid date selection.");
            return;
        }

        AttendanceRecord record = records.get(dateIndex);

        // Display attendance
        System.out.println("\nAttendance for " + subject + " on " + record.getDate() + ":");
        System.out.println("Roll No\tName\t\tStatus");
        for (Map.Entry<Student, Character> entry : record.getAttendance().entrySet()) {
            System.out.println(entry.getKey().getRollNo() + "\t" + 
                             entry.getKey().getName() + "\t\t" + 
                             (entry.getValue() == 'P' ? "Present" : "Absent"));
        }
    }

    private static void viewAttendanceAsStudent() {
        System.out.println("\n===== View Your Attendance =====");
        
        int rollNo = InputUtil.getIntInput("Enter your roll number: ");
        Student student = studentService.getStudentByRollNo(rollNo);

        if (student == null) {
            System.out.println("Student with this roll number not found.");
            return;
        }

        // Display student's subjects
        List<Subject> studentSubjects = student.getSubjects();
        if (studentSubjects.isEmpty()) {
            System.out.println("No subjects registered for this student.");
            return;
        }

        System.out.println("Your Subjects:");
        for (int i = 0; i < studentSubjects.size(); i++) {
            System.out.println((i+1) + ". " + studentSubjects.get(i));
        }

        int subjectIndex = InputUtil.getIntInput("\nSelect subject to view attendance (by number): ") - 1;

        if (subjectIndex < 0 || subjectIndex >= studentSubjects.size()) {
            System.out.println("Invalid subject selection.");
            return;
        }

        Subject subject = studentSubjects.get(subjectIndex);

        // Get attendance records for this student and subject
        List<AttendanceRecord> records = new ArrayList<>();
        for (AttendanceRecord record : attendanceService.getAttendanceRecords()) {
            if (record.getSubject().equals(subject) && record.getAttendance().containsKey(student)) {
                records.add(record);
            }
        }

        if (records.isEmpty()) {
            System.out.println("No attendance records found for this subject.");
            return;
        }

        // Display dates
        System.out.println("\nAttendance Dates:");
        for (int i = 0; i < records.size(); i++) {
            System.out.println((i+1) + ". " + records.get(i).getDate());
        }

        int dateIndex = InputUtil.getIntInput("\nSelect date to view attendance (by number): ") - 1;

        if (dateIndex < 0 || dateIndex >= records.size()) {
            System.out.println("Invalid date selection.");
            return;
        }

        AttendanceRecord record = records.get(dateIndex);
        char status = record.getStudentAttendance(student);

        System.out.println("\nYour attendance for " + subject + " on " + record.getDate() + ":");
        System.out.println("Status: " + (status == 'P' ? "Present" : "Absent"));
    }
}