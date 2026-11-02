
package studentmanagement;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt("Please enter a valid menu number: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 0 -> System.out.println("Exiting application...");
                default -> System.out.println("Invalid choice. Please choose 0-5.");
            }

        } while (choice != 0);

        sc.close();
    }

    // Add a student
    private static void addStudent() {
        int id = readPositiveInt("Enter student ID: ");
        String name = readNonEmptyString("Enter name: ");
        int age = readAge();
        String course = readNonEmptyString("Enter course: ");
        double marks = readMarks();

        Student student = new Student(id, name, age, course, marks);

        if (dao.addStudent(student)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Could not add student. The ID may already exist.");
        }
    }

    // View all students
    private static void viewAllStudents() {
        List<Student> students = dao.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            displayStudent(student);
        }
    }

    // Search by ID
    private static void searchStudent() {
        int id = readPositiveInt("Enter student ID to search: ");

        Student student = dao.getStudentById(id);

        if (student != null) {
            displayStudent(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Update a student
    private static void updateStudent() {
        int id = readPositiveInt("Enter student ID to update: ");

        Student existing = dao.getStudentById(id);

        if (existing == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Enter the student's updated details:");

        String name = readNonEmptyString("Enter new name: ");
        int age = readAge();
        String course = readNonEmptyString("Enter new course: ");
        double marks = readMarks();

        Student updated = new Student(id, name, age, course, marks);

        if (dao.updateStudent(updated)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Could not update student.");
        }
    }

    // Delete a student
    private static void deleteStudent() {
        int id = readPositiveInt("Enter student ID to delete: ");

        if (dao.deleteStudent(id)) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found or could not be deleted.");
        }
    }

    // Display student details
    private static void displayStudent(Student student) {
        System.out.println("\nID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Course: " + student.getCourse());
        System.out.println("Marks: " + student.getMarks());
        System.out.println("-----------------------------");
    }

    // Read a non-empty string
    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    // Read an integer
    private static int readInt(String errorMessage) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
                System.out.print("Try again: ");
            }
        }
    }

    // Read a positive integer
    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            int value = readInt("Please enter a valid positive whole number.");

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than 0.");
        }
    }

    // Read an age between 1 and 120
    private static int readAge() {
        while (true) {
            int age = readPositiveInt("Enter age (1-120): ");

            if (age <= 120) {
                return age;
            }

            System.out.println("Age cannot exceed 120.");
        }
    }

    // Read marks between 0 and 100
    private static double readMarks() {
        while (true) {
            System.out.print("Enter marks (0-100): ");

            try {
                double marks = Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(marks) && marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println("Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
