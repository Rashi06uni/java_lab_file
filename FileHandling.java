// Assignment 4: File Handling and Collections
// Name: Rashi Yadav
// Roll Number: 2401350003

import java.io.*;
import java.util.*;

class Member {
    int memberId;
    String memberName;
    String department;
    double score;
    char grade;

    public Member(int memberId, String memberName, String department, double score) {
        this.memberId = memberId;
        this.memberName = memberName;
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

    public String toFileFormat() {
        return memberId + "," + memberName + "," + department + "," + score;
    }

    @Override
    public String toString() {
        return "ID: " + memberId + " | Name: " + memberName + " | Score: " + score + " | Grade: " + grade;
    }
}

class ScoreComparator implements Comparator<Member> {
    @Override
    public int compare(Member m1, Member m2) {
        return Double.compare(m2.score, m1.score);
    }
}

class MemberManager {
    private ArrayList<Member> memberList = new ArrayList<>();
    private final String DATA_FILE = "members.txt";

    public void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No existing data file found. Starting fresh.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String dept = data[2];
                    double score = Double.parseDouble(data[3]);
                    memberList.add(new Member(id, name, dept, score));
                }
            }
            System.out.println("Data loaded successfully from " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Member m : memberList) {
                bw.write(m.toFileFormat());
                bw.newLine();
            }
            System.out.println("Records saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readRawData() {
        System.out.println("\n--- Reading File using RandomAccessFile ---");
        File file = new File(DATA_FILE);
        if (!file.exists()) { 
            System.out.println("File does not exist yet (Save data first).");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.seek(0);
            String line;
            while ((line = raf.readLine()) != null) {
                System.out.println("Raw Data: " + line);
            }
        } catch (IOException e) {
            System.out.println("Error with RandomAccessFile: " + e.getMessage());
        }
    }

    public void showFileInfo() {
        File file = new File(DATA_FILE);
        System.out.println("\n--- File Information ---");
        if (file.exists()) {
            System.out.println("File Name  : " + file.getName());
            System.out.println("Full Path  : " + file.getAbsolutePath());
            System.out.println("File Size  : " + file.length() + " bytes");
            System.out.println("Readable   : " + file.canRead());
            System.out.println("Writable   : " + file.canWrite());
        } else {
            System.out.println("File " + DATA_FILE + " does not exist yet.");
        }
    }

    public void insertMember(Member m) {
        memberList.add(m);
        System.out.println("Member added.");
    }

    public void showSortedByScore() {
        if (memberList.isEmpty()) {
            System.out.println("No records to display.");
            return;
        }

        Collections.sort(memberList, new ScoreComparator());

        System.out.println("\n--- Member List (Sorted by Score) ---");
        Iterator<Member> iterator = memberList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void sampleTests() {
        System.out.println("****Running Member Tests****");

        insertMember(new Member(101, "RARA", "Computer Science", 85.5));
        insertMember(new Member(102, "KANU", "Mathematics", 92.0));
        insertMember(new Member(103, "SHIVI", "Physics", 67.8));

        showSortedByScore();
        saveData();

        System.out.println("****Member Tests Completed****");
    }
}

public class FileHandling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberManager mm = new MemberManager();

        mm.loadData();
        mm.sampleTests();

        while (true) {
            System.out.println("\n=== Member File System ===");
            System.out.println("1. Add Member");
            System.out.println("2. View Sorted Members (by Score)");
            System.out.println("3. Show File Information");
            System.out.println("4. Read File using RandomAccessFile");
            System.out.println("5. Save & Exit");
            System.out.print("Select option: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter Member ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Score: ");
                    double score = sc.nextDouble();
                    mm.insertMember(new Member(id, name, dept, score));
                    break;
                case 2:
                    mm.showSortedByScore();
                    break;
                case 3:
                    mm.showFileInfo();
                    break;
                case 4:
                    mm.readRawData();
                    break;
                case 5:
                    mm.saveData();
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}