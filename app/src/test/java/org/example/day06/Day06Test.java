package org.example.day06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day06Test {
  private final Day06 day06;
  private final List<Day06.Problem> exampleProblems;
  private final List<Day06.Problem> problems;

  public Day06Test() throws IOException {
    this.day06 = new Day06();
    this.exampleProblems = day06.parseInput("input.example.txt");
    this.problems = day06.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(4277556L, day06.solvePart01(exampleProblems));
  }

  @Test
  public void testPart01() {
    assertEquals(4693419406682L, day06.solvePart01(problems));
  }

  @Test
  public void testExample02() {
    // assertEquals(0, day06.solvePart02(exampleProblems));
  }

  @Test
  public void testPart02() {
    // assertEquals(0, day06.solvePart02(problems));
  }
}
