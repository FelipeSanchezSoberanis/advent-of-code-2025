package org.example.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day07 {
  private final String inputsDir;

  public Day07() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day07")
            .toString();
  }

  public List<String> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename)).collect(Collectors.toList());
  }

  public Integer solvePart01(List<String> rows) {
    Set<Integer> beams = new HashSet<>();
    beams.add(rows.getFirst().indexOf("S"));
    return rows.stream()
        .skip(1)
        .reduce(
            0,
            (acc, row) -> {
              List<Integer> splitters =
                  IntStream.range(0, row.length())
                      .boxed()
                      .filter(i -> row.charAt(i) == '^')
                      .collect(Collectors.toList());
              splitters.forEach(
                  splitter -> {
                    if (splitter != row.length() - 1) beams.add(splitter + 1);
                    if (splitter != 0) beams.add(splitter - 1);
                  });
              int collisions =
                  splitters.stream().filter(beams::contains).collect(Collectors.toList()).size();
              beams.removeAll(splitters);
              return acc + collisions;
            },
            Integer::sum);
  }

  public Long solvePart02(List<String> rows) {
    return rows.stream()
        .skip(1)
        .collect(
            () -> {
              Map<Integer, Long> beams = new HashMap<>();
              beams.put(rows.getFirst().indexOf("S"), 1L);
              return beams;
            },
            (beams, row) -> {
              List<Integer> splitters =
                  IntStream.range(0, row.length())
                      .boxed()
                      .filter(i -> row.charAt(i) == '^')
                      .collect(Collectors.toList());
              List<Integer> collisions =
                  splitters.stream()
                      .filter(beams::containsKey)
                      .distinct()
                      .collect(Collectors.toList());
              collisions.forEach(
                  collision -> {
                    Long numBeams = beams.get(collision);
                    if (collision > 0) {
                      beams.put(collision - 1, beams.getOrDefault(collision - 1, 0L) + numBeams);
                    }
                    if (collision < row.length() - 1) {
                      beams.put(collision + 1, beams.getOrDefault(collision + 1, 0L) + numBeams);
                    }
                  });
              splitters.forEach(beams::remove);
            },
            (acc, beams) -> acc.putAll(beams))
        .values()
        .stream()
        .mapToLong(Long::longValue)
        .sum();
  }
}
