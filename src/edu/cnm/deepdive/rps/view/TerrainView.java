package edu.cnm.deepdive.rps.view;

import edu.cnm.deepdive.rps.model.RpsBreed;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TerrainView extends Canvas {

  private static final double MAX_HUE = 360;
  private static final Color[] BREED_COLORS = new Color[RpsBreed.values().length];

  static {
    for (int i = 0; i < BREED_COLORS.length; i++) {
      BREED_COLORS[i] = Color.hsb(i * MAX_HUE / BREED_COLORS.length, 1, 0.9);
    }
  }

  public void draw(RpsBreed[][] grid) {
    GraphicsContext context = getGraphicsContext2D();
    double cellSize = Math.min(getHeight() / grid.length, getWidth() / grid.length);
    context.clearRect(0, 0, getWidth(), getHeight());
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid.length; col++) {
        context.setFill(BREED_COLORS[grid[row][col].ordinal()]);
        context.fillOval(col * cellSize, row * cellSize, cellSize, cellSize);
      }
    }
  }

}
