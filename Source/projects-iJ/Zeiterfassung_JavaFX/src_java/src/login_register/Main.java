package login_register;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class Main {

    //Connection zu Datenbank
    public Main() {

        super();
    }

    public static Connection connect() {
        Connection con = null;
        try {
// get a connection to database
            con = DriverManager.getConnection("jdbc:mariadb://192.168.30.145:3306/", "root", "!30DBrootPW48");

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return con;
    }

    public static void main(String[] args) throws SQLException {
        //Login

        String username = "";
        String password = "";

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your Username: ");
        String name = scan.nextLine();
        System.out.print("Enter your Password: ");
        String psw = scan.nextLine();


        MessageDigest md;
        try
        {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Generate the random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(psw.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

            System.out.println(sb);

        } catch (NoSuchAlgorithmException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Connected zu DB und macht select statement zum herausfinden

        Statement stmt = connect().createStatement();
        String SQL = "SELECT * FROM Login WHERE Username='" + name + "' AND Passwort='" + psw + "'";
        ResultSet rs = stmt.executeQuery(SQL);

        // Passwort und Username kontrolle
        while (rs.next()) {
            username = rs.getString("Username");
            password = rs.getString("Passwort");
        }

        if (name.equals(username) && password.equals(password)) {
            System.out.println("Successful Login!");
        } else {
            System.out.println("Incorrect Password or incorrect Username!");
        }

    }

}




