import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserLookup {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public void findUser(String username) {
        try {
            Connection conn = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);

            // VULNERABLE: Direct concatenation of user input
            String query =
                    "SELECT * FROM users WHERE username = '" +
                    username + "'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                        "User: " + rs.getString("username"));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserLookup lookup = new UserLookup();

        // Simulated user input
        String username = args.length > 0 ? args[0] : "admin";

        lookup.findUser(username);
    }
}
