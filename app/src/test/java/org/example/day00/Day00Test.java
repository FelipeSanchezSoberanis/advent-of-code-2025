package org.example.day00;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

public class Day00Test {
  private final Day00 day00;
  private final Day00.Input exampleInput;
  private final Day00.Input input;

  public Day00Test() throws FileNotFoundException {
    this.day00 = new Day00();
    this.exampleInput = day00.parseInput("input.example.txt");
    this.input = day00.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(11, day00.solveCase01(exampleInput));
  }

  @Test
  public void testProblem01() {
    assertEquals(1941353, day00.solveCase01(input));
  }

  @Test
  public void testExample02() {
    assertEquals(31, day00.solveCase02(exampleInput));
  }

  @Test
  public void testProblem02() {
    assertEquals(22539317, day00.solveCase02(input));
  }
}
