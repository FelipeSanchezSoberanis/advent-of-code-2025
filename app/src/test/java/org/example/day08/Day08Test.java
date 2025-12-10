package org.example.day08;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day08Test {
  private final Day08 day08;
  private final List<Day08.JunctionBox> exampleJunctionBoxes;
  private final List<Day08.JunctionBox> junctionBoxes;

  public Day08Test() throws IOException {
    this.day08 = new Day08();
    this.exampleJunctionBoxes = day08.parseInput("input.example.txt");
    this.junctionBoxes = day08.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(40, day08.solvePart01(exampleJunctionBoxes, 10));
  }

  @Test
  public void testPart01() {
    assertEquals(63920, day08.solvePart01(junctionBoxes, 1000));
  }

  @Test
  public void testExample02() {
    assertEquals(25272, day08.solvePart02(exampleJunctionBoxes));
  }

  @Test
  public void testPart02() {
    assertEquals(1026594680, day08.solvePart02(junctionBoxes));
  }
}
