package org.example.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.example.day01.Day01.Machine;
import org.junit.jupiter.api.Test;

public class Day01Test {
  private final Day01 day01;
  private final List<Machine> exampleInput;
  private final List<Machine> input;

  public Day01Test() throws IOException {
    this.day01 = new Day01();
    this.exampleInput = day01.parseInput("input.example.txt");
    this.input = day01.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(new BigDecimal("480"), day01.solveCase01(exampleInput));
  }

  @Test
  public void testProblem01() {
    assertEquals(new BigDecimal("36758"), day01.solveCase01(input));
  }

  @Test
  public void testProblem02() {
    assertEquals(0, day01.solveCase02(input));
  }
}
