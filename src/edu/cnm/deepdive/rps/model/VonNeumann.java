package edu.cnm.deepdive.rps.model;

public class VonNeumann implements Neighborhood {

  private final Location[] neighbors = {
      new Location(-1, 0),
      new Location(0, -1),
      new Location(0, 1),
      new Location(1, 0)
  };

  @Override
  public Location[] neighbors() {
    return neighbors;
  }
}
