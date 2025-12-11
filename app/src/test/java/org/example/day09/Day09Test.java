package org.example.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day09Test {
  private final Day09 day09;
  private final List<Day09.Coordinate> exampleRedTiles;
  private final List<Day09.Coordinate> redTiles;

  public Day09Test() throws IOException {
    this.day09 = new Day09();
    this.exampleRedTiles = day09.parseInput("input.example.txt");
    this.redTiles = day09.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(50L, day09.solvePart01(exampleRedTiles));
  }

  @Test
  public void testPart01() {
    assertEquals(4781546175L, day09.solvePart01(redTiles));
  }

  @Test
  public void testExample02() {
    assertEquals(24L, day09.solvePart02(exampleRedTiles));
  }

  // @Test
  // public void testPart02() {
  //   assertEquals(1026594680, day09.solvePart02(redTiles));
  // }
}
