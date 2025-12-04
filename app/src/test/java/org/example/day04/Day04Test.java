package org.example.day04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day04Test {
  private final Day04 day04;
  private final List<List<Character>> exampleRollsOfPaper;
  private final List<List<Character>> rollsOfPaper;

  public Day04Test() throws IOException {
    this.day04 = new Day04();
    this.exampleRollsOfPaper = day04.parseInput("input.example.txt");
    this.rollsOfPaper = day04.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(13, day04.solvePart01(exampleRollsOfPaper));
  }

  @Test
  public void testPart01() {
    assertEquals(1372, day04.solvePart01(rollsOfPaper));
  }

  @Test
  public void testExample02() {
    assertEquals(43, day04.solvePart02(exampleRollsOfPaper));
  }

  @Test
  public void testPart02() {
    assertEquals(7922, day04.solvePart02(rollsOfPaper));
  }
}
