package org.example.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day01Test {
  private final Day01 day01;
  private final List<Day01.Rotation> exampleRotations;
  private final List<Day01.Rotation> rotations;

  public Day01Test() throws IOException {
    this.day01 = new Day01();
    this.exampleRotations = day01.parseInput("input.example.txt");
    this.rotations = day01.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(3, day01.solvePartOne(exampleRotations));
  }

  @Test
  public void testPart01() {
    assertEquals(1191, day01.solvePartOne(rotations));
  }

  @Test
  public void testExample02() {
    assertEquals(6, day01.solvePartTwo(exampleRotations));
  }

  @Test
  public void testPart02() {
    assertEquals(6858, day01.solvePartTwo(rotations));
  }
}
