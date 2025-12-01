package org.example.day01;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Day01 {
  private final String inputsDir;

  public Day01() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day01")
            .toString();
  }

  public List<Rotation> parseInput(String filename) throws IOException {
    List<Rotation> rotations = new ArrayList<>();
    try (Scanner scanner = new Scanner(Paths.get(inputsDir, filename))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        switch (line.charAt(0)) {
          case 'R':
            rotations.add(
                Rotation.builder()
                    .direction(Direction.RIGHT)
                    .value(Integer.parseInt(line.substring(1)))
                    .build());
            break;
          case 'L':
            rotations.add(
                Rotation.builder()
                    .direction(Direction.LEFT)
                    .value(Integer.parseInt(line.substring(1)))
                    .build());
            break;
        }
      }
    }
    return rotations;
  }

  public Integer solvePartOne(List<Rotation> rotations) {
    return rotations.stream()
        .collect(
            () -> Tracker.builder().position(50).value(0).build(),
            (acc, rotation) -> {
              int position = acc.getPosition();
              switch (rotation.getDirection()) {
                case LEFT:
                  position -= rotation.getValue() % 100;
                  break;
                case RIGHT:
                  position += rotation.getValue() % 100;
                  break;
              }
              if (position > 99) {
                position -= 100;
              } else if (position < 0) {
                position += 100;
              }
              if (position == 0) acc.setValue(acc.getValue() + 1);
              acc.setPosition(position);
            },
            (BiConsumer<Tracker, Tracker>)
                (acc, tracker) -> {
                  acc.setValue(acc.getValue() + tracker.getValue());
                })
        .getValue();
  }

  public Integer solvePartTwo(List<Rotation> rotations) {
    return rotations.stream()
        .collect(
            () -> Tracker.builder().position(50).value(0).build(),
            (acc, rotation) -> {
              int position = acc.getPosition();
              boolean startedAtZero = position == 0;
              int fullRotations = Math.floorDiv(rotation.getValue(), 100);

              switch (rotation.getDirection()) {
                case LEFT:
                  position -= rotation.getValue() % 100;
                  break;
                case RIGHT:
                  position += rotation.getValue() % 100;
                  break;
              }

              if (position > 99) {
                position -= 100;
                if (!startedAtZero) acc.setValue(acc.getValue() + 1);
              } else if (position < 0) {
                position += 100;
                if (!startedAtZero) acc.setValue(acc.getValue() + 1);
              } else if (position == 0) {
                acc.setValue(acc.getValue() + 1);
              }
              acc.setValue(acc.getValue() + fullRotations);

              acc.setPosition(position);
            },
            (BiConsumer<Tracker, Tracker>)
                (acc, tracker) -> {
                  acc.setValue(acc.getValue() + tracker.getValue());
                })
        .getValue();
  }

  @Builder
  @Getter
  @Setter
  @ToString
  public static class Tracker {
    private Integer value;
    private Integer position;
  }

  @Builder
  @Getter
  @ToString
  public static class Rotation {
    private Direction direction;
    private Integer value;
  }

  public static enum Direction {
    LEFT,
    RIGHT;
  }
}
