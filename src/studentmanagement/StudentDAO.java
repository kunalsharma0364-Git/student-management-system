
package studentmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Add a student
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (id, name, age, course, marks) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getCourse());
            stmt.setDouble(5, student.getMarks());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // View all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getDouble("marks")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    // Search a student by ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("course"),
                            rs.getDouble("marks")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }

        return null;
    }

    // Update a student's details
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, age = ?, course = ?, marks = ? " +
                "WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setString(3, student.getCourse());
            stmt.setDouble(4, student.getMarks());
            stmt.setInt(5, student.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // Delete a student by ID
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }
}
