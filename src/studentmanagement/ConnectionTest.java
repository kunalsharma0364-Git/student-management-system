
package studentmanagement;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("MySQL connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
