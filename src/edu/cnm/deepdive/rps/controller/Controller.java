package edu.cnm.deepdive.rps.controller;

import edu.cnm.deepdive.rps.model.Terrain;
import edu.cnm.deepdive.rps.model.VonNeumann;
import edu.cnm.deepdive.rps.view.TerrainView;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class Controller {

  private static final int TERRAIN_SIZE = 75;
  private static final int STEP_PER_ITERATION = 100;
  private static final int MAX_SLEEP_PER_ITERATION = 10;
  private static final int MIX_THRESHOLD = 10;
  private static final int PAIRS_TO_MIX = 8;


  @FXML
  private TerrainView terrainView;
  @FXML
  private CheckBox fitCheckbox;
  @FXML
  private Text iterationsLabel;
  @FXML
  private ScrollPane viewScroller;
  @FXML
  private Slider speedSlider;
  @FXML
  private Slider mixingSlider;
  @FXML
  private Button start;
  @FXML
  private Button stop;
  @FXML
  private Button reset;

  private double defaultViewHeight;
  private double defaultViewWidth;
  private String iterationFormat;
  private Terrain terrain;
  private boolean running = false;
  private final Object lock = new Object();
  private Timer timer;
  //TODO Create thread object(s).

  @FXML
  private void initialize() {
    terrain = new Terrain(75, new Random(), new VonNeumann());
    defaultViewHeight = terrainView.getHeight();
    defaultViewWidth = terrainView.getWidth();
    iterationFormat = iterationsLabel.getText();
    speedSlider.setMax(MAX_SLEEP_PER_ITERATION);
    reset(null);
    timer = new Timer();
  }

  @FXML
  private void fitView(ActionEvent actionEvent) {
    if (fitCheckbox.isSelected()) {
      terrainView.setWidth(viewScroller.getWidth() - 2);
      terrainView.setHeight(viewScroller.getHeight() - 2);
    } else {
      terrainView.setWidth(defaultViewWidth);
      terrainView.setHeight(defaultViewHeight);
    }
    if(!running) {
      draw();
    }
  }

  @FXML
  private void start(ActionEvent actionEvent) {
    running = true;
    start.setDisable(true);
    reset.setDisable(true);
    stop.setDisable(false);
    timer.start();
    new Runner().start();
  }

  @FXML
  public void stop(ActionEvent actionEvent) {
    //TODO Investigate whether the thread should re-enable the buttons when it's done
    running = false;
    start.setDisable(false);
    stop.setDisable(true);
    reset.setDisable(false);
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
    terrain.reset();
    start.setDisable(false);
    draw();
  }

  private void draw() {
    synchronized (lock) {
      terrainView.draw(terrain.getGrid());
      iterationsLabel.setText(String.format(iterationFormat, terrain.getIterations()));
    }
  }

  private class Timer extends AnimationTimer {

    @Override
    public void handle(long now) {
      draw();
    }
  }

  private class Runner extends Thread {

    @Override
    public void run() {
      while (running) {
        int sleep = (int) (1 + MAX_SLEEP_PER_ITERATION - speedSlider.getValue());
        synchronized (lock) {
          terrain.step(STEP_PER_ITERATION);
          //TODO Mixing?
        }
        try {
          Thread.sleep(sleep);
        } catch (InterruptedException e) {
          //Do nothing!
        }
      }
    }
  }
}
