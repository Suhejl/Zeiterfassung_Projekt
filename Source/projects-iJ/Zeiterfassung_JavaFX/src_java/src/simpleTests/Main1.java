package simpleTests;

import java.sql.*;
import java.util.LinkedList;

public class Main1 {
  public LinkedList getEntries(){
    Connection connection = null;
    LinkedList entries = new LinkedList();
    try {
      connection = DriverManager.getConnection("jdbc:mariadb://192.168.30.145:3306/", "root", "!30DBrootPW48");

    }catch(SQLException e){
      e.printStackTrace();
    }
    return entries;
  }
  
}
