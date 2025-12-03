package org.example.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03 {
  private final String inputsDir;

  public Day03() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day03")
            .toString();
  }

  public List<List<Long>> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename))
        .map(
            line -> Arrays.stream(line.split("")).map(Long::parseLong).collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  public Long solvePart01(List<List<Long>> banks) {
    return banks.stream()
        .mapToLong(
            batteries -> {
              long leftBattery = Long.MIN_VALUE;
              long rightBattery = Long.MIN_VALUE;

              int i = 0;
              while (i < batteries.size()) {
                Long battery = batteries.get(i);
                boolean isLastBattery = i >= batteries.size() - 1;

                if (battery > leftBattery && !isLastBattery) {
                  leftBattery = battery;
                  rightBattery = Long.MIN_VALUE;
                } else if (battery > rightBattery) {
                  rightBattery = battery;
                }

                i++;
              }

              return leftBattery * 10 + rightBattery;
            })
        .sum();
  }

  public Long solvePart02(List<List<Long>> banks) {
    return banks.stream()
        .mapToLong(
            batteries -> {
              long[] res = new long[12];
              Arrays.fill(res, Integer.MIN_VALUE);

              int i = 0;
              while (i < batteries.size()) {
                Long battery = batteries.get(i);
                int j = Math.max(res.length - (batteries.size() - i), 0);

                while (j < res.length) {
                  if (battery > res[j]) {
                    res[j] = battery;
                    j++;
                    while (j < res.length) {
                      res[j] = Long.MIN_VALUE;
                      j++;
                    }
                  }

                  j++;
                }

                i++;
              }

              return IntStream.range(0, res.length)
                  .mapToLong(index -> res[index] * Math.powExact(10L, res.length - index - 1))
                  .sum();
            })
        .sum();
  }
}
