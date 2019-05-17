package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

  @FXML
  private void changeScreenButtonPushed(ActionEvent event) throws IOException {

    Parent tableViewParent = FXMLLoader.load(getClass().getResource("ExampleOfTableView.fxml"));
    Scene tableViewScene = new Scene(tableViewParent);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    window.setScene(tableViewScene);
    window.show();

  }

}
