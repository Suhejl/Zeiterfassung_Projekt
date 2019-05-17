package login_register;

import javax.xml.transform.Result;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

  //Connection zu Datenbank

  public static Scanner scan = new Scanner(System.in);
  static String setUsername;
  static String setPassword;
  static boolean backToMeni = false;

  static Connection connection = null;

  public static Connection connect() {
    Connection con = null;
    try {
// get a connection to database
      con = DriverManager.getConnection("jdbc:mariadb://192.168.30.145:3306/zeiterfassung", "root", "!30DBrootPW48");

    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
    return con;
  }

  public static void main(String[] args) throws SQLException {
    //Login
    connection = connect();
  do {
      int choice = menu();
      switch (choice) {
        case 1:
          loginRegister();
          break;
        case 2:
          login();
          break;
        default:
          System.out.println("Falsch");
      }
      System.out.print("Wollen Sie zurück ins Menü\n>");
      backToMeni = scan.nextBoolean();
    } while (backToMeni);

  }

  public static void loginRegister() {
    boolean isCaught = false;
    SecureRandom random = new SecureRandom();

    String saltValue = "";
    byte[] salt = new byte[16];

    System.out.print("Enter your Username: ");
    setUsername = scan.nextLine();
    System.out.print("Enter your Password: ");
    setPassword = scan.nextLine();


    MessageDigest md;
    try {
      // Select the message digest for the hash computation -> SHA-256
      md = MessageDigest.getInstance("SHA-256");
      // Generate the random salt
      random.nextBytes(salt);
      // Passing the salt to the digest for the computation
      md.update(salt);
      // Generate the salted hash
      byte[] hashedPassword = md.digest(setPassword.getBytes(StandardCharsets.UTF_8));

      StringBuilder sb = new StringBuilder();
      for (byte b : hashedPassword)
        sb.append(String.format("%02x", b));

      setPassword = sb.toString();

      for (int i = 0; i < salt.length; i++) {
        saltValue += salt[i] + " ";
      }

      String insertCode = "INSERT INTO Login (Username, Password, Salt) VALUES ('" + setUsername + "', '" + setPassword + "', '" + saltValue + "');";
      String checkUser = "SELECT Username, Password, Salt FROM Login WHERE Username='" + setUsername + "' AND Password='" + setPassword + "' AND Salt='" + saltValue + "';";


      Statement insertStatement = connection.createStatement();
      insertStatement.executeQuery(insertCode);
      insertStatement.executeQuery(checkUser);

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
      isCaught = true;
    }
    if (!isCaught) {
      System.out.println("Account erfolgreich erstellt");
    }

  }

  public static void login() {
    String getSalt = "";
    String getPassword = "";
    String getUsername = "";

    System.out.print("Enter yout Username: ");
    setUsername = scan.nextLine();
    System.out.print("Emter your password: ");
    setPassword = scan.nextLine();

    // StringBuilder passwordSb = new StringBuilder(setPassword);

    try {
      String getSaltandHash = "SELECT Password, Salt, Username FROM Login WHERE Username='" + setUsername + "';";
      Statement pwStatement = connection.createStatement();
      ResultSet getHashSalt = pwStatement.executeQuery(getSaltandHash);

      while (getHashSalt.next()) {
        getPassword = getHashSalt.getString("Password");
        getSalt = getHashSalt.getString("Salt");
        getUsername = getHashSalt.getString("Username");
      }
      String[] getSaltArray = getSalt.split(" ");

      byte[] getSaltinByte = new byte[getSaltArray.length];
      for (int i = 0; i < getSaltArray.length; i++) {
        getSaltinByte[i] = Byte.parseByte(getSaltArray[i]);
      }

      MessageDigest hashPassword = MessageDigest.getInstance("SHA-256");

      hashPassword.update(getSaltinByte);

      byte[] hashedPassword = hashPassword.digest(setPassword.getBytes(StandardCharsets.UTF_8));

      StringBuilder pwtoHash = new StringBuilder();

      for (byte b : hashedPassword)
        pwtoHash.append(String.format("%02x", b));

      setPassword = pwtoHash.toString();

      if (setPassword.equals(getPassword) && setUsername.equals(getUsername)) {
        System.out.println("Ok");
      } else {
        System.out.println("Username oder Passwort stimmen nicht überrein");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println("Dieser User existiert nicht");
    }

  }

  public static int menu() {
    boolean repeat_menu;
    int choice = 0;

    do {
      repeat_menu = false;
      System.out.println("Was wollen Sie machen?");
      System.out.println("[1] Registrieren");
      System.out.print("[2] Login\n>");
      try {
        choice = scan.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Ungültige Eingabe, Bitte nur Zahlen. ");
        // scan.next();
        repeat_menu = true;
        scan.nextLine();
      }/* finally {
                while (scan.hasNext("\\s")) {
                    scan.nextLine();
                    System.out.println("Bitte nur eine Zahl eingeben.");
                    repeat_menu = true;
                }

            }*/
    } while (repeat_menu);
    scan.nextLine();
    return choice;
  }

}




