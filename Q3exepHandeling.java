// Assignment 3: Exception Handling, Multithreading, and Wrapper Classes
// Name: Rashi Yadav
// Roll Number: 2401350003
// Btech CSE (FSD)

import java.util.ArrayList;
import java.util.Scanner;

class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) {
        super(message);
    }
}

interface DataOperations {
    void insertMember(Member m);
    void removeMember(int memberId) throws MemberNotFoundException;
    void modifyMember(int memberId, double newScore) throws MemberNotFoundException;
    void showMember(int memberId) throws MemberNotFoundException;
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
        System.out.println("\n--- Member Profile ---");
        System.out.println("Name    : " + fullName);
        System.out.println("Email   : " + emailAddress);
        System.out.println("ID      : " + memberId);
        System.out.println("Dept    : " + department);
        System.out.println("Score   : " + score);
        System.out.println("Grade   : " + grade);
    }
}

class ProcessingTask implements Runnable {
    @Override
    public void run() {
        try {
            System.out.print("Processing");
            for (int i = 0; i < 5; i++) {
                System.out.print(".");
                Thread.sleep(400);
            }
            System.out.println(" Done!");
        } catch (InterruptedException e) {
            System.out.println("Task Interrupted");
        }
    }
}

class MemberManager implements DataOperations {
    private ArrayList<Member> memberList = new ArrayList<>();

    @Override
    public void insertMember(Member m) {
        for (Member existing : memberList) {
            if (existing.memberId == m.memberId) {
                System.out.println("ERROR: Member ID already exists!");
                return;
            }
        }

        Thread processingThread = new Thread(new ProcessingTask());
        processingThread.start();
        
        try {
            processingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memberList.add(m);
        System.out.println("Success: Member added to database.");
    }

    @Override
    public void removeMember(int memberId) throws MemberNotFoundException {
        boolean removed = memberList.removeIf(m -> m.memberId == memberId);
        if (!removed) {
            throw new MemberNotFoundException("Member with ID " + memberId + " not found for removal.");
        }
        System.out.println("Success: Member removed.");
    }

    @Override
    public void modifyMember(int memberId, double newScore) throws MemberNotFoundException {
        for (Member m : memberList) {
            if (m.memberId == memberId) {
                m.updateScore(newScore);
                System.out.println("Success: Score updated.");
                return;
            }
        }
        throw new MemberNotFoundException("Cannot update. Member ID " + memberId + " does not exist.");
    }

    @Override
    public void showMember(int memberId) throws MemberNotFoundException {
        for (Member m : memberList) {
            if (m.memberId == memberId) {
                m.showProfile();
                return;
            }
        }
        throw new MemberNotFoundException("Search failed. Member ID " + memberId + " is not in records.");
    }

    public void showAll() {
        if (memberList.isEmpty()) {
            System.out.println("No records found.");
        } else {
            for (Member m : memberList) m.showProfile();
        }
    }

    public void sampleTests() {
        System.out.println("****Running Member Tests****");

        try {
            Member mem1 = new Member("rara", "rara@email.com", 101, "Computer Science", 85.5);
            Member mem2 = new Member("kanu", "kanu@email.com", 102, "Mathematics", 92.0);
            
            insertMember(mem1);
            insertMember(mem2);
            
            modifyMember(101, 88.0);
            showAll();

            System.out.println("****Member Tests Completed****");
        } catch (Exception e) {
            System.out.println("Test error: " + e.getMessage());
        }
    }
}

public class Q3exepHandeling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberManager mm = new MemberManager();

        mm.sampleTests();

        while (true) {
            System.out.println("\n****Advanced Member System****");
            System.out.println("1. Add Member");
            System.out.println("2. View Member");
            System.out.println("3. Update Score");
            System.out.println("4. Remove Member");
            System.out.println("5. View All");
            System.out.println("6. Exit");
            System.out.print("Select option: ");

            try {
                String input = sc.nextLine();
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        
                        System.out.print("Enter Member ID: ");
                        int memberId = Integer.parseInt(sc.nextLine());
                        
                        System.out.print("Enter Department: ");
                        String department = sc.nextLine();
                        
                        System.out.print("Enter Score: ");
                        double score = Double.parseDouble(sc.nextLine());
                        
                        if (score < 0 || score > 100) {
                            throw new IllegalArgumentException("Score must be between 0 and 100");
                        }
                        
                        Member newMember = new Member(name, email, memberId, department, score);
                        mm.insertMember(newMember);
                        break;

                    case 2:
                        System.out.print("Enter Member ID to view: ");
                        int viewId = Integer.parseInt(sc.nextLine());
                        mm.showMember(viewId);
                        break;

                    case 3:
                        System.out.print("Enter Member ID to update: ");
                        int updateId = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter new Score: ");
                        double newScore = Double.parseDouble(sc.nextLine());
                        
                        if (newScore < 0 || newScore > 100) {
                            throw new IllegalArgumentException("Score must be between 0 and 100");
                        }
                        
                        mm.modifyMember(updateId, newScore);
                        break;

                    case 4:
                        System.out.print("Enter Member ID to remove: ");
                        int removeId = Integer.parseInt(sc.nextLine());
                        mm.removeMember(removeId);
                        break;

                    case 5:
                        mm.showAll();
                        break;

                    case 6:
                        System.out.println("Exiting system...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid option! Please enter 1-6.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Input Error: Please enter valid numbers. " + e.getMessage());
            } catch (MemberNotFoundException e) {
                System.out.println("Database Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Validation Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}