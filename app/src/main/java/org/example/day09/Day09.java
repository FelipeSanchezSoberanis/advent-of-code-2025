package org.example.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import tools.jackson.databind.ObjectMapper;

public class Day09 {
  private final String inputsDir;

  public Day09() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day09")
            .toString();
  }

  public List<Coordinate> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename))
        .map(
            line -> {
              String[] parts = line.split(",");
              return new Coordinate(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
            })
        .collect(Collectors.toList());
  }

  public Long solvePart01(List<Coordinate> redTiles) {
    return IntStream.range(0, redTiles.size() - 1)
        .boxed()
        .flatMap(
            i -> {
              return IntStream.range(i + 1, redTiles.size())
                  .boxed()
                  .map(
                      j -> {
                        return new Rectangle(redTiles.get(i), redTiles.get(j)).getArea();
                      });
            })
        .sorted(Comparator.reverseOrder())
        .collect(Collectors.toList())
        .getFirst();
  }

  public Long solvePart02(List<Coordinate> redTiles) {
    List<LineSegment> lineSegments =
        IntStream.range(0, redTiles.size() - 1)
            .boxed()
            .map(
                i -> {
                  Coordinate redTileOne = redTiles.get(i);
                  Coordinate redTileTwo = redTiles.get(i + 1);
                  return new LineSegment(redTileOne, redTileTwo);
                })
            .collect(Collectors.toList());
    lineSegments.add(new LineSegment(redTiles.getLast(), redTiles.getFirst()));

    return IntStream.range(0, redTiles.size() - 1)
        .boxed()
        .flatMap(
            i ->
                IntStream.range(i + 1, redTiles.size())
                    .boxed()
                    .map(j -> new Rectangle(redTiles.get(i), redTiles.get(j))))
        .sorted(Comparator.comparingLong(Rectangle::getArea).reversed())
        .filter(
            rectangle ->
                rectangle.getVertices().stream()
                    .allMatch(coordinate -> coordinate.isContainedInPolygon(lineSegments)))
        .collect(Collectors.toList())
        .getFirst()
        .getArea();
  }

  @Getter
  public static class Coordinate {
    private final Long x;
    private final Long y;

    public Coordinate(Long x, Long y) {
      this.x = x;
      this.y = y;
    }

    public boolean isContainedInPolygon(List<LineSegment> lineSegments) {
      if (lineSegments.stream().anyMatch(lineSegment -> isOnLineSegment(lineSegment))) {
        return true;
      }

      LineSegment lineSegmentToStart = new LineSegment(new Coordinate(0L, getY()), this);
      LineSegment lineSegmentToTop = new LineSegment(new Coordinate(getX(), 0L), this);

      Long verticalCollisions =
          lineSegments.stream()
              .filter(lineSegmentToStart::collidesWith)
              .collect(Collectors.counting());
      Long horizontalCollisions =
          lineSegments.stream()
              .filter(lineSegmentToTop::collidesWith)
              .collect(Collectors.counting());

      return verticalCollisions % 2 != 0 && horizontalCollisions % 2 != 0;
    }

    public boolean isOnLineSegment(LineSegment lineSegment) {
      Long x = getX();
      Long y = getY();
      Long ax = lineSegment.getStart().getX();
      Long ay = lineSegment.getStart().getY();
      Long bx = lineSegment.getEnd().getX();
      Long by = lineSegment.getEnd().getY();

      return (x <= Math.max(ax, bx)
          && x >= Math.min(ax, bx)
          && y <= Math.max(ay, by)
          && y >= Math.min(ay, by));
    }

    public static Orientation getTripletOrientation(Coordinate a, Coordinate b, Coordinate c) {
      Long ax = a.getX();
      Long ay = a.getY();
      Long bx = b.getX();
      Long by = b.getY();
      Long cx = c.getX();
      Long cy = c.getY();

      Long orientation = (by - ay) * (cx - bx) - (bx - ax) * (cy - by);

      if (orientation == 0) return Orientation.COLINEAR;
      if (orientation > 0) return Orientation.CLOCKWISE;
      return Orientation.COUNTERCLOCKWISE;
    }

    @Override
    public String toString() {
      return new ObjectMapper().writeValueAsString(this);
    }
  }

  @Getter
  public static class LineSegment {
    private final Coordinate start;
    private final Coordinate end;

    public LineSegment(Coordinate start, Coordinate end) {
      this.start = start;
      this.end = end;
    }

    public Direction getDirection() {
      if (getStart().getX().equals(getEnd().getX())) return Direction.VERTICAL;
      if (getStart().getY().equals(getEnd().getY())) return Direction.HORIZONTAL;
      throw new RuntimeException(
          String.format(
              "Could not get Direction for LineSegment with start '%s' and end '%s'",
              this.getStart(), this.getEnd()));
    }

    public boolean collidesWith(LineSegment lineSegment) {
      if (getStart().isOnLineSegment(lineSegment) && getEnd().isOnLineSegment(lineSegment)) {
        return true;
      }

      if (lineSegment.getStart().isOnLineSegment(this)
          && lineSegment.getEnd().isOnLineSegment(this)) {
        return true;
      }

      Orientation orientationOne =
          Coordinate.getTripletOrientation(getStart(), getEnd(), lineSegment.getStart());
      Orientation orientationTwo =
          Coordinate.getTripletOrientation(getStart(), getEnd(), lineSegment.getEnd());
      Orientation orientationThree =
          Coordinate.getTripletOrientation(
              lineSegment.getStart(), lineSegment.getEnd(), getStart());
      Orientation orientationFour =
          Coordinate.getTripletOrientation(lineSegment.getStart(), lineSegment.getEnd(), getEnd());

      return !orientationOne.equals(orientationTwo) && !orientationThree.equals(orientationFour);
    }

    @Override
    public String toString() {
      return new ObjectMapper().writeValueAsString(this);
    }
  }

  @Getter
  public static class Rectangle {
    private final Coordinate vertexOne;
    private final Coordinate vertexTwo;
    private final Coordinate vertexThree;
    private final Coordinate vertexFour;

    public Rectangle(Coordinate vertexOne, Coordinate vertexTwo) {
      this.vertexOne = vertexOne;
      this.vertexTwo = vertexTwo;
      this.vertexThree = new Coordinate(vertexOne.getX(), vertexTwo.getY());
      this.vertexFour = new Coordinate(vertexTwo.getX(), vertexOne.getY());
    }

    public List<Coordinate> getVertices() {
      return List.of(getVertexOne(), getVertexTwo(), getVertexThree(), getVertexFour());
    }

    public Long getArea() {
      return (Math.abs(getVertexOne().getY() - getVertexTwo().getY()) + 1)
          * (Math.abs(getVertexOne().getX() - getVertexTwo().getX()) + 1);
    }

    @Override
    public String toString() {
      return new ObjectMapper().writeValueAsString(this);
    }
  }

  public static enum Direction {
    VERTICAL,
    HORIZONTAL
  }

  public static enum Orientation {
    CLOCKWISE,
    COUNTERCLOCKWISE,
    COLINEAR
  }
}
