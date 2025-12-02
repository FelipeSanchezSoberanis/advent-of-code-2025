package org.example.day02;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class Day02 {
  private final String inputsDir;

  public Day02() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day02")
            .toString();
  }

  public List<IdRange> parseInput(String filename) throws IOException {
    List<IdRange> idRanges = new ArrayList<>();
    try (Scanner scanner = new Scanner(Paths.get(inputsDir, filename))) {
      while (scanner.hasNextLine()) {
        idRanges.addAll(
            Arrays.stream(scanner.nextLine().split(","))
                .map(
                    idRange -> {
                      String[] startEnd = idRange.split("-");
                      return IdRange.builder()
                          .start(Long.parseLong(startEnd[0]))
                          .end(Long.parseLong(startEnd[1]))
                          .build();
                    })
                .collect(Collectors.toList()));
        ;
      }
    }
    return idRanges;
  }

  public Long solvePart01(List<IdRange> idRanges) {
    return idRanges.stream()
        .flatMap(
            idRange ->
                LongStream.rangeClosed(idRange.getStart(), idRange.getEnd())
                    .mapToObj(Long::toString))
        .filter(
            id -> {
              if (id.length() % 2 != 0) {
                return false;
              }
              if (!id.substring(0, id.length() / 2).equals(id.substring(id.length() / 2))) {
                return false;
              }
              return true;
            })
        .mapToLong(Long::parseLong)
        .sum();
  }

  @Builder
  @Getter
  @ToString
  public static class IdRange {
    private final Long start;
    private final Long end;
  }
}
