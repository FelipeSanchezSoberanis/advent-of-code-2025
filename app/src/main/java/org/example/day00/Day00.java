package org.example.day00;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class Day00 {
  private final String inputsDir;

  public Day00() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day00")
            .toString();
  }

  public Input parseInput(String filename) throws FileNotFoundException {
    List<Integer> firstList = new ArrayList<>();
    List<Integer> secondList = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(Paths.get(inputsDir, filename).toString()))) {
      int i = 0;
      while (scanner.hasNextInt()) {
        if (i % 2 == 0) {
          firstList.add(scanner.nextInt());
        } else {
          secondList.add(scanner.nextInt());
        }
        i++;
      }
    }
    return Input.builder().firstList(firstList).secondList(secondList).build();
  }

  public Integer solveCase01(Input input) {
    List<Integer> firstList = input.getFirstList();
    List<Integer> secondList = input.getSecondList();

    firstList.sort(Integer::compare);
    secondList.sort(Integer::compare);

    int result = 0;
    for (int i = 0; i < Math.min(firstList.size(), secondList.size()); i++) {
      result += Math.abs(firstList.get(i) - secondList.get(i));
    }

    return result;
  }

  public Integer solveCase02(Input input) {
    Map<Integer, Integer> frequency =
        input.getSecondList().stream()
            .collect(
                () -> new HashMap<Integer, Integer>(),
                (map, number) -> {
                  map.put(number, map.getOrDefault(number, 0) + 1);
                },
                Map::putAll);

    return input.getFirstList().stream()
        .mapToInt((number) -> number * frequency.getOrDefault(number, 0))
        .sum();
  }

  @Builder
  @Getter
  @ToString
  public static class Input {
    private final List<Integer> firstList;
    private final List<Integer> secondList;
  }
}
