package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;

import java.lang.annotation.Repeatable;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {

  String first;
  @FXML
  private TextField myText;


  public void sayInput() {
    System.out.println(myText.getText());
  }

  @FXML
  private TableView<User> myTable;

  @FXML
  private TableColumn<User, String> col_id;

  @FXML
  private TableColumn<User, String> col_firstname;

  @FXML
  private TableColumn<User, String> col_email;

  @FXML
  private TableColumn<User, String> col_name;

  @FXML
  private TableColumn<User, String> col_notes;


  @FXML
  private TableColumn<User, String> col_tel;


  public void sayHello() {
    System.out.println("Hello");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    initTable();
  }

  public void initTable() {
    initCols();
    loadData();
  }

  public void initCols() {

    //  id, name, firstname, email, tel, notes;
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    col_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
    col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
    col_tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    col_notes.setCellValueFactory(new PropertyValueFactory<>("notes"));


    editableCols();
  }

  public void editableCols() {
    col_id.setCellFactory(TextFieldTableCell.forTableColumn());
    col_id.setOnEditCommit(e -> {
      e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
    });

    col_name.setCellFactory(TextFieldTableCell.forTableColumn());
    col_name.setOnEditCommit(e -> {
      e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
    });

    col_firstname.setCellFactory(TextFieldTableCell.forTableColumn());
    col_firstname.setOnEditCommit(e -> {
      e.getTableView().getItems().get(e.getTablePosition().getRow()).setFirstname(e.getNewValue());
    });

    col_email.setCellFactory(TextFieldTableCell.forTableColumn());
    col_email.setOnEditCommit(e -> {
      e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
    });

    col_tel.setCellFactory(TextFieldTableCell.forTableColumn());
    col_tel.setOnEditCommit(e -> {
      e.getTableView().getItems().get(e.getTablePosition().getRow()).setTel(e.getNewValue());
    });

    col_notes.setCellFactory(TextFieldTableCell.forTableColumn());
    col_notes.setOnEditCommit(e -> {
      e.getTableView().getItems().get(e.getTablePosition().getRow()).setNotes(e.getNewValue());
    });

    myTable.setEditable(true);
  }

  private void loadData(){
 // Connection connection = null;
    ObservableList<User> table_data = FXCollections.observableArrayList();

    for (int i = 0; i < 7; i++) {
      table_data.add(new User(String.valueOf(i), "name " + i, "firstname " + i,
          "email " + i, "tel " + i, "notes " + 1 ));
    }
    myTable.setItems(table_data);
    myTable.refresh();
  }

  public void initialize() {


  }
}
