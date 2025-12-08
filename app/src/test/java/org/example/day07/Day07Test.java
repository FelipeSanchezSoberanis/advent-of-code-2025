package org.example.day07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day07Test {
  private final Day07 day07;
  private final List<String> exampleRows;
  private final List<String> rows;

  public Day07Test() throws IOException {
    this.day07 = new Day07();
    this.exampleRows = day07.parseInput("input.example.txt");
    this.rows = day07.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(21, day07.solvePart01(exampleRows));
  }

  @Test
  public void testPart01() {
    assertEquals(1656, day07.solvePart01(rows));
  }

  // @Test
  // public void testExample02() {
  //   assertEquals(3263827L, day07.solvePart02(exampleRows));
  // }

  // @Test
  // public void testPart02() {
  //   assertEquals(9029931401920L, day07.solvePart02(rows));
  // }
}
