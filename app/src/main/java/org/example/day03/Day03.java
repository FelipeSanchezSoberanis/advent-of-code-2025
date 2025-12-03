package org.example.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 {
  private final String inputsDir;

  public Day03() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day03")
            .toString();
  }

  public List<List<Integer>> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename))
        .map(
            line ->
                Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  public Integer solvePart01(List<List<Integer>> banks) {
    return banks.stream()
        .mapToInt(
            batteries -> {
              int leftBattery = Integer.MIN_VALUE;
              int rightBattery = Integer.MIN_VALUE;

              int i = 0;
              while (i < batteries.size()) {
                Integer battery = batteries.get(i);
                boolean isLastBattery = i >= batteries.size() - 1;

                if (battery > leftBattery && !isLastBattery) {
                  leftBattery = battery;
                  rightBattery = Integer.MIN_VALUE;
                } else if (battery > rightBattery) {
                  rightBattery = battery;
                }

                i++;
              }

              return leftBattery * 10 + rightBattery;
            })
        .sum();
  }

  public Integer solvePart02(List<List<Integer>> banks) {
    return 0;
  }
}
