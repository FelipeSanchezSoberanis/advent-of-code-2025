package org.example.day00;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import org.example.day00.Day00.Input;
import org.junit.jupiter.api.Test;

public class Day00Test {
  @Test
  public void testFirstExample() throws FileNotFoundException {
    Day00 day00 = new Day00();
    Input input = day00.parseInput("00.example.txt");
    assertEquals(11, day00.distanceBetweenLists(input));
  }

  @Test
  public void testFirstProblem() throws FileNotFoundException {
    Day00 day00 = new Day00();
    Input input = day00.parseInput("01.txt");
    assertEquals(1941353, day00.distanceBetweenLists(input));
  }

  @Test
  public void testSecondExample() throws FileNotFoundException {
    Day00 day00 = new Day00();
    Input input = day00.parseInput("00.example.txt");
    assertEquals(31, day00.similarityBetweenLists(input));
  }

  @Test
  public void testSecondProblem() throws FileNotFoundException {
    Day00 day00 = new Day00();
    Input input = day00.parseInput("01.txt");
    assertEquals(22539317, day00.similarityBetweenLists(input));
  }
}
