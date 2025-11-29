// Assignment 1: Student Class Design & Basic Operations
// Name: Rashi Yadav
// Roll Number: 2401350003

import java.util.Scanner;

class Student {
    int rollNumber;
    String name;
    String course;
    double marks;
    char grade;

    public Student(int rollNumber, String name, String course, double marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.course = course;
        this.marks = marks;
        calculateGrade();
    }

    public void calculateGrade() {
        if (marks >= 90) {
            grade = 'A';
        } else if (marks >= 80) {
            grade = 'B';
        } else if (marks >= 70) {
            grade = 'C';
        } else if (marks >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }
    }

    public void displayStudentDetails() {

        System.out.println("      STUDENT RECORD         ");
        System.out.println("Roll Number : " + rollNumber);
        System.out.println("Name        : " + name);
        System.out.println("Course      : " + course);
        System.out.println("Marks       : " + marks);
        System.out.println("Grade       : " + grade);
    
    }

    public void updateMarks(double newMarks) {
        this.marks = newMarks;
        calculateGrade();
        System.out.println("Marks updated to: " + newMarks + ". New Grade: " + grade);
    }
}

class StudentManagement {
    Scanner sc = new Scanner(System.in);
    Student[] students = new Student[100];
    int studentCount = 0;

    public void createStudent(int rollNumber, String name, String course, double marks) {
        Student student = new Student(rollNumber, name, course, marks);
        students[studentCount] = student;
        studentCount++;
        System.out.println("Student record created for: " + name);
    }

    public void createStudentInteractive() {
        System.out.print("Enter Roll Number: ");
        int rollNumber = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        
        System.out.print("Enter Course: ");
        String course = sc.nextLine();
        
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();
        createStudent(rollNumber, name, course, marks);
    }

    public void displayAllStudents() {
        if (studentCount == 0) {
            System.out.println("No student records found.");
            return;
        }
        
        System.out.println("\n****ALL STUDENT RECORDS****");
        for (int i = 0; i < studentCount; i++) {
            students[i].displayStudentDetails();
        }
    }

    public Student findStudent(int rollNumber) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].rollNumber == rollNumber) {
                return students[i];
            }
        }
        return null;
    }

    public void updateStudentMarks(int rollNumber, double newMarks) {
        Student student = findStudent(rollNumber);
        if (student != null) {
            student.updateMarks(newMarks);
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    public void updateStudentMarksInteractive() {
        System.out.print("Enter Roll Number to update: ");
        int rollNumber = sc.nextInt();
        System.out.print("Enter new marks: ");
        double newMarks = sc.nextDouble();
        sc.nextLine();
        updateStudentMarks(rollNumber, newMarks);
    }

    public void viewStudentDetails(int rollNumber) {
        Student student = findStudent(rollNumber);
        if (student != null) {
            student.displayStudentDetails();
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    public void viewStudentDetailsInteractive() {
        System.out.print("Enter Roll Number: ");
        int rollNumber = sc.nextInt();
        sc.nextLine();
        viewStudentDetails(rollNumber);
    }

   
    public void testCases() {
        System.out.println("****Running Student Test Cases****");

        createStudent(101, "SHIVI", "Computer Science", 85.5);
        createStudent(102, "RARA", "Mathematics", 92.0);
        createStudent(103, "KANU", "Physics", 67.8);

        updateStudentMarks(101, 88.0); 

        displayAllStudents();

        System.out.println(" Test Cases Completed ");
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n****Student Record Management System****");
            System.out.println("1. Create new student record");
            System.out.println("2. View all student records");
            System.out.println("3. View specific student details");
            System.out.println("4. Update student marks");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1: createStudentInteractive(); break;
                case 2: displayAllStudents(); break;
                case 3: viewStudentDetailsInteractive(); break;
                case 4: updateStudentMarksInteractive(); break;
                case 5: System.out.println("Exiting Student Management System..."); return;
                default: System.out.println("Invalid choice, try again.");
            }
        }
    }
}

public class Q1studRecord {
    public static void main(String[] args) {
        StudentManagement sm = new StudentManagement();

        sm.testCases();
    }
}