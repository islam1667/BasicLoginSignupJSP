
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author islam
 */
public class DAO {
    public static Connection getConnection() {
        Connection c = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","12345");
            initializeTable(c);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return c;
    }

    private static void initializeTable(Connection c) throws SQLException {
        Statement st = c.createStatement();
        st.execute("CREATE DATABASE IF NOT EXISTS `BasicLoginSignUp` CHARACTER SET utf16 COLLATE utf16_general_ci;");
        st.execute("USE `BasicLoginSignUp`;");
        st.execute("CREATE TABLE IF NOT EXISTS `users`  ("
                + "  `id` int NOT NULL AUTO_INCREMENT,"
                + "  `uname` varchar(255) NULL,"
                + "  `upwd` varchar(255) NULL,"
                + "  `umail` varchar(255) NULL,"
                + "  `uphone` varchar(255) NULL,"
                + "  PRIMARY KEY (`id`)"
                + ");");
        st.close();
    }
}
