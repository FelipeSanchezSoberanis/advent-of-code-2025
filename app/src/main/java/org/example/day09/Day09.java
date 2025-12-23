package org.example.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;

public class Day09 {
  private final String inputsDir;

  public Day09() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day09")
            .toString();
  }

  public List<Coordinate> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename))
        .map(line -> line.split(","))
        .map(parts -> new Coordinate(Long.parseLong(parts[0]), Long.parseLong(parts[1])))
        .collect(Collectors.toList());
  }

  public Long solvePart01(List<Coordinate> redTiles) {
    return IntStream.range(0, redTiles.size() - 1)
        .boxed()
        .flatMap(
            i ->
                IntStream.range(i + 1, redTiles.size())
                    .boxed()
                    .map(j -> new Rectangle(redTiles.get(i), redTiles.get(j)).getArea()))
        .max(Comparator.comparing(Long::longValue))
        .orElseThrow();
  }

  public Long solvePart02(List<Coordinate> redTiles) {
    return null;
  }

  @Getter
  public static class Coordinate {
    private final Long x;
    private final Long y;

    public Coordinate(Long x, Long y) {
      this.x = x;
      this.y = y;
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
  }

  @Getter
  public static class Rectangle {
    private final Coordinate vertexOne;
    private final Coordinate vertexTwo;
    private final Coordinate vertexThree;
    private final Coordinate vertexFour;

    public Rectangle(Coordinate vertexOne, Coordinate vertexThree) {
      this.vertexOne = vertexOne;
      this.vertexTwo = new Coordinate(vertexOne.getX(), vertexThree.getY());
      this.vertexThree = vertexThree;
      this.vertexFour = new Coordinate(vertexThree.getX(), vertexOne.getY());
    }

    public Long getArea() {
      return (Math.abs(getVertexOne().getY() - getVertexThree().getY()) + 1)
          * (Math.abs(getVertexOne().getX() - getVertexThree().getX()) + 1);
    }
  }
}
