package sample;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {



  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(Main.class.getResource("sample.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(scene);
    primaryStage.show();


  }


  public static void main(String[] args) {
    launch(args);
  }
}
