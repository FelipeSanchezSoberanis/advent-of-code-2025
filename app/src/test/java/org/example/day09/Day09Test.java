package org.example.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import org.example.day09.Day09.Coordinate;
import org.example.day09.Day09.Direction;
import org.example.day09.Day09.LineSegment;
import org.junit.jupiter.api.Test;

public class Day09Test {
  private final Day09 day09;
  private final List<Coordinate> exampleRedTiles;
  private final List<Coordinate> redTiles;

  public Day09Test() throws IOException {
    this.day09 = new Day09();
    this.exampleRedTiles = day09.parseInput("input.example.txt");
    this.redTiles = day09.parseInput("input.txt");
  }

  @Test
  public void testExample01() {
    assertEquals(50L, day09.solvePart01(exampleRedTiles));
  }

  @Test
  public void testPart01() {
    assertEquals(4781546175L, day09.solvePart01(redTiles));
  }

  @Test
  public void testGetDirection() {
    assertEquals(
        Direction.VERTICAL,
        new LineSegment(new Coordinate(0L, 0L), new Coordinate(0L, 10L)).getDirection());
    assertEquals(
        Direction.HORIZONTAL,
        new LineSegment(new Coordinate(0L, 0L), new Coordinate(10L, 0L)).getDirection());
  }

  @Test
  public void testCollidesWith() {
    assertEquals(
        true,
        new LineSegment(new Coordinate(5L, 5L), new Coordinate(9L, 5L))
            .collidesWith(new LineSegment(new Coordinate(7L, 7L), new Coordinate(7L, 3L))));
    assertEquals(
        true,
        new LineSegment(new Coordinate(9L, 5L), new Coordinate(5L, 5L))
            .collidesWith(new LineSegment(new Coordinate(7L, 7L), new Coordinate(7L, 3L))));
    assertEquals(
        true,
        new LineSegment(new Coordinate(7L, 7L), new Coordinate(7L, 3L))
            .collidesWith(new LineSegment(new Coordinate(5L, 5L), new Coordinate(9L, 5L))));
    assertEquals(
        true,
        new LineSegment(new Coordinate(7L, 7L), new Coordinate(7L, 3L))
            .collidesWith(new LineSegment(new Coordinate(9L, 5L), new Coordinate(5L, 5L))));
  }

  @Test
  public void testIsOnLineSegment() {
    LineSegment horizontalLineSegment =
        new LineSegment(new Coordinate(5L, 5L), new Coordinate(9L, 5L));
    LineSegment verticalLineSegment =
        new LineSegment(new Coordinate(7L, 7L), new Coordinate(7L, 3L));
    assertEquals(true, new Coordinate(7L, 5L).isOnLineSegment(horizontalLineSegment));
    assertEquals(true, new Coordinate(5L, 5L).isOnLineSegment(horizontalLineSegment));
    assertEquals(false, new Coordinate(7L, 6L).isOnLineSegment(horizontalLineSegment));
    assertEquals(true, new Coordinate(7L, 5L).isOnLineSegment(verticalLineSegment));
    assertEquals(true, new Coordinate(7L, 7L).isOnLineSegment(verticalLineSegment));
    assertEquals(false, new Coordinate(8L, 5L).isOnLineSegment(verticalLineSegment));
    assertEquals(
        false,
        new Coordinate(2L, 1L)
            .isOnLineSegment(new LineSegment(new Coordinate(2L, 3L), new Coordinate(2L, 5L))));
    assertEquals(
        false,
        new Coordinate(2L, 1L)
            .isOnLineSegment(new LineSegment(new Coordinate(7L, 1L), new Coordinate(11L, 1L))));
  }

  @Test
  public void testIsContainedInPolygon() {
    List<LineSegment> lineSegments =
        List.of(
            new LineSegment(new Coordinate(5L, 5L), new Coordinate(5L, 9L)),
            new LineSegment(new Coordinate(5L, 9L), new Coordinate(9L, 9L)),
            new LineSegment(new Coordinate(9L, 9L), new Coordinate(9L, 5L)),
            new LineSegment(new Coordinate(9L, 5L), new Coordinate(5L, 5L)));
    assertEquals(true, new Coordinate(7L, 7L).isContainedInPolygon(lineSegments));
    assertEquals(true, new Coordinate(9L, 7L).isContainedInPolygon(lineSegments));
    assertEquals(false, new Coordinate(11L, 7L).isContainedInPolygon(lineSegments));
    assertEquals(false, new Coordinate(7L, 3L).isContainedInPolygon(lineSegments));
  }

  @Test
  public void testExample02() {
    assertEquals(24L, day09.solvePart02(exampleRedTiles));
  }

  // @Test
  // public void testPart02() {
  //   assertEquals(0L, day09.solvePart02(redTiles));
  // }
}
