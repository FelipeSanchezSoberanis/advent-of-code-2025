package org.example.day05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class Day05Test {
  private final Day05 day05;
  private final Day05.Input exampleInput;
  private final Day05.Input input;

  public Day05Test() throws IOException {
    this.day05 = new Day05();
    this.exampleInput = day05.parseInput("input.example.txt");
    this.input = day05.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(3L, day05.solvePart01(exampleInput));
  }

  @Test
  public void testPart01() {
    assertEquals(615L, day05.solvePart01(input));
  }

  @Test
  public void testExample02() {
    assertEquals(14L, day05.solvePart02(exampleInput));
  }

  @Test
  public void testPart02() {
    assertEquals(353716783056994L, day05.solvePart02(input));
  }
}
