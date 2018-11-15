package edu.cnm.deepdive.rps;

import edu.cnm.deepdive.rps.controller.Controller;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private static final String RESOURCES_DIR =   "resources";
  private static final String LAYOUT_RESOURCE = RESOURCES_DIR + "/main.fxml";
  private static final String RESOURCE_BUNDLE = RESOURCES_DIR +"/strings";
  private static final String WINDOW_TITLE_KEY = "window_title";

  private Controller controller;

  @Override
  public void start(Stage stage) throws Exception {
    ClassLoader classLoader = getClass().getClassLoader();
    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
    FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(LAYOUT_RESOURCE), bundle);
    Parent root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    Scene scene = new Scene(root);
    stage.setTitle(bundle.getString(WINDOW_TITLE_KEY));
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    controller.stop(null);
    super.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }


}
