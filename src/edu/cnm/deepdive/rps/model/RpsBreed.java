package edu.cnm.deepdive.rps.model;

import java.util.Comparator;

public enum RpsBreed {
  ROCK,
  PAPER,
  SCISSORS;

  private static final int[][] DOMINANCE = {
      //R  P  S
      {0, -1, 1}, //R
      {1, 0, -1}, //P
      {-1, 1, 0}  //S
  };

  public static final Comparator<RpsBreed> REFEREE =
      (rps1, rps2) -> DOMINANCE[rps1.ordinal()][rps2.ordinal()];
}
