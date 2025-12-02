package org.example.day02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Day02Test {
  private final Day02 day02;
  private final List<Day02.IdRange> exampleIdRanges;
  private final List<Day02.IdRange> idRanges;

  public Day02Test() throws IOException {
    this.day02 = new Day02();
    this.exampleIdRanges = day02.parseInput("input.example.txt");
    this.idRanges = day02.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(1227775554L, day02.solvePart01(exampleIdRanges));
  }

  @Test
  public void testPart01() {
    assertEquals(22062284697L, day02.solvePart01(idRanges));
  }

  @Test
  public void testPart02() {
    assertEquals(46666175279L, day02.solvePart02(idRanges));
  }
}
