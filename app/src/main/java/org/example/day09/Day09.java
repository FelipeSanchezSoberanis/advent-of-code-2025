package org.example.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
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
              return Coordinate.builder()
                  .x(Long.parseLong(parts[0]))
                  .y(Long.parseLong(parts[1]))
                  .build();
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
                        Coordinate redTileOne = redTiles.get(i);
                        Coordinate redTileTwo = redTiles.get(j);
                        return (Math.abs(redTileOne.getY() - redTileTwo.getY()) + 1)
                            * (Math.abs(redTileOne.getX() - redTileTwo.getX()) + 1);
                      });
            })
        .sorted(Comparator.reverseOrder())
        .collect(Collectors.toList())
        .getFirst();
  }

  public Long solvePart02(List<Coordinate> redTiles) {
    return null;
  }

  @Builder
  @Getter
  public static class Coordinate {
    private final Long x;
    private final Long y;

    @Override
    public String toString() {
      return new ObjectMapper().writeValueAsString(this);
    }
  }
}
