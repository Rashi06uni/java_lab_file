// Assignment 2: Inheritance, Interfaces, and Modular Design
// Name:Rashi Yadav
// Roll Number: 2401350003

import java.util.ArrayList;
import java.util.Scanner;

interface DataOperations {
    void insertMember(Member m);
    void removeMember(int memberId);
    void modifyMember(int memberId, double newScore);
    void showMember(int memberId);
}

abstract class Individual {
    String fullName;
    String emailAddress;

    public Individual(String fullName, String emailAddress) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    abstract void showProfile();
}

class Member extends Individual {
    int memberId;
    String department;
    double score;
    char grade;

    public Member(String fullName, String emailAddress, int memberId, String department, double score) {
        super(fullName, emailAddress);
        this.memberId = memberId;
        this.department = department;
        this.score = score;
        computeGrade();
    }

    private void computeGrade() {
        if (score >= 90) grade = 'A';
        else if (score >= 80) grade = 'B';
        else if (score >= 70) grade = 'C';
        else if (score >= 60) grade = 'D';
        else grade = 'F';
    }

    public void updateScore(double score) {
        this.score = score;
        computeGrade();
    }

    @Override
    void showProfile() {
        System.out.println("\n****Member Profile****");
        System.out.println("Name    : " + fullName);
        System.out.println("Email   : " + emailAddress);
        System.out.println("ID      : " + memberId);
        System.out.println("Dept    : " + department);
        System.out.println("Score   : " + score);
        System.out.println("Grade   : " + grade);
    }
}

class MemberManager implements DataOperations {
    private ArrayList<Member> memberList = new ArrayList<>();

    @Override
    public void insertMember(Member m) {
        for (Member existing : memberList) {
            if (existing.memberId == m.memberId) {
                System.out.println("ERROR: Member ID " + m.memberId + " already exists!");
                return;
            }
        }
        memberList.add(m);
        System.out.println("Success: Member added.");
    }

    @Override
    public void removeMember(int memberId) {
        boolean removed = memberList.removeIf(m -> m.memberId == memberId);
        if (removed) {
            System.out.println("Success: Member with ID " + memberId + " removed.");
        } else {
            System.out.println("Error: Member not found.");
        }
    }

    @Override
    public void modifyMember(int memberId, double newScore) {
        for (Member m : memberList) {
            if (m.memberId == memberId) {
                m.updateScore(newScore);
                System.out.println("Success: Score updated for " + m.fullName);
                return;
            }
        }
        System.out.println("Error: Member not found.");
    }

    @Override
    public void showMember(int memberId) {
        for (Member m : memberList) {
            if (m.memberId == memberId) {
                m.showProfile();
                return;
            }
        }
        System.out.println("Error: Member not found.");
    }

    public void showMember(String fullName) {
        boolean found = false;
        for (Member m : memberList) {
            if (m.fullName.equalsIgnoreCase(fullName)) {
                m.showProfile();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Error: No member found with name " + fullName);
        }
    }
    
    public void showAll() {
        if(memberList.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for(Member m : memberList) m.showProfile();
        }
    }

    public void sampleTests() {
        System.out.println("****Running Member Tests****");

        Member mem1 = new Member("rara", "rara@email.com", 101, "Computer Science", 85.5);
        Member mem2 = new Member("kanu", "kanu@email.com", 102, "Mathematics", 92.0);
        
        insertMember(mem1);
        insertMember(mem2);
        
        modifyMember(101, 88.0);
        showAll();

        System.out.println("****Member Tests Completed****");
    }
}

public class Q2InheritanceEg {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberManager mm = new MemberManager();

        mm.sampleTests();

        while (true) {
            System.out.println("\n****Member Management System****");
            System.out.println("1. Add Member");
            System.out.println("2. View Member (by ID)");
            System.out.println("3. View Member (by Name)");
            System.out.println("4. Update Score");
            System.out.println("5. Remove Member");
            System.out.println("6. View All");
            System.out.println("7. Exit");
            System.out.print("Select option: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Member ID: ");
                    int memberId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();
                    System.out.print("Enter Score: ");
                    double score = sc.nextDouble();
                    
                    Member newMember = new Member(name, email, memberId, department, score);
                    mm.insertMember(newMember);
                    break;
                case 2:
                    System.out.print("Enter Member ID to view: ");
                    mm.showMember(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Enter Name to view: ");
                    mm.showMember(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Enter Member ID to update: ");
                    int id = sc.nextInt();
                    System.out.print("Enter new Score: ");
                    double newScore = sc.nextDouble();
                    mm.modifyMember(id, newScore);
                    break;
                case 5:
                    System.out.print("Enter Member ID to remove: ");
                    mm.removeMember(sc.nextInt());
                    break;
                case 6:
                    mm.showAll();
                    break;
                case 7:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}