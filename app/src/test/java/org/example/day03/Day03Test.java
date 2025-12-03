package org.example.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day03Test {
  private final Day03 day03;
  private final List<List<Integer>> exampleBanks;
  private final List<List<Integer>> banks;

  public Day03Test() throws IOException {
    this.day03 = new Day03();
    this.exampleBanks = day03.parseInput("input.example.txt");
    this.banks = day03.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(357, day03.solvePart01(exampleBanks));
  }

  @Test
  public void testPart01() {
    assertEquals(17311, day03.solvePart01(banks));
  }

  @Test
  public void testPart02() {}
}
