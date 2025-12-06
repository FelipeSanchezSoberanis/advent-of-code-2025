package org.example.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class Day05 {
  private final String inputsDir;

  public Day05() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day05")
            .toString();
  }

  public Input parseInput(String filename) throws IOException {
    List<Range> ranges = new ArrayList<>();
    List<Long> ids = new ArrayList<>();
    try (Stream<String> lines = Files.lines(Paths.get(inputsDir, filename))) {
      lines
          .map(String::strip)
          .filter(line -> line.length() > 0)
          .forEach(
              line -> {
                if (line.contains("-")) {
                  String[] parts = line.split("-");
                  ranges.add(
                      Range.builder()
                          .start(Long.parseLong(parts[0]))
                          .end(Long.parseLong(parts[1]))
                          .build());
                } else {
                  ids.add(Long.parseLong(line));
                }
              });
    }
    return Input.builder().ranges(ranges).ids(ids).build();
  }

  public List<Range> joinRanges(List<Range> ranges) {
    List<Range> joinedRanges = new ArrayList<>();

    ranges.stream()
        .sorted(Comparator.comparing(Range::getStart))
        .forEach(
            range -> {
              if (joinedRanges.isEmpty()) {
                joinedRanges.add(range);
                return;
              }

              Range lastJoinedRange = joinedRanges.removeLast();
              if (lastJoinedRange.getEnd() >= range.getStart()) {
                joinedRanges.add(
                    Range.builder()
                        .start(lastJoinedRange.getStart())
                        .end(Math.max(lastJoinedRange.getEnd(), range.getEnd()))
                        .build());
                return;
              }

              joinedRanges.add(lastJoinedRange);
              joinedRanges.add(range);
            });

    return joinedRanges;
  }

  public Long solvePart01(Input input) {
    List<Range> joinedRanges = joinRanges(input.getRanges());
    return input.getIds().stream()
        .filter(
            id ->
                joinedRanges.stream()
                    .anyMatch(range -> id >= range.getStart() && id <= range.getEnd()))
        .count();
  }

  public Long solvePart02(Input input) {
    return joinRanges(input.getRanges()).stream()
        .mapToLong(range -> range.getEnd() - range.getStart() + 1)
        .sum();
  }

  @Builder
  @Getter
  @ToString
  public static class Input {
    private final List<Range> ranges;
    private final List<Long> ids;
  }

  @Builder
  @Getter
  @ToString
  public static class Range {
    private final Long start;
    private final Long end;
  }
}
