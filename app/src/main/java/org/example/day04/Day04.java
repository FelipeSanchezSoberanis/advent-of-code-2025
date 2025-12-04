package org.example.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 {
  private final String inputsDir;

  public Day04() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day04")
            .toString();
  }

  public List<List<Character>> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename))
        .map(
            line ->
                IntStream.range(0, line.length())
                    .mapToObj(i -> Character.valueOf(line.charAt(i)))
                    .collect(Collectors.toList()))
        .collect(Collectors.toList());
  }

  public Integer solvePart01(List<List<Character>> rollsOfPaper) {
    int res = 0;

    int rows = rollsOfPaper.size();
    int cols = rollsOfPaper.get(0).size();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Character character = rollsOfPaper.get(i).get(j);
        if (!character.equals('@')) continue;
        int numSurroundingRollsOfPaper = 0;

        for (int k = -1; k < 2; k++) {
          int y = i + k;
          if (y < 0 || y > rows - 1) continue;

          for (int l = -1; l < 2; l++) {
            int x = j + l;
            if (x < 0 || x > cols - 1 || (k == 0 && l == 0)) continue;

            Character surroundingCharacter = rollsOfPaper.get(y).get(x);
            if (surroundingCharacter.equals('.')) continue;

            numSurroundingRollsOfPaper++;
            if (numSurroundingRollsOfPaper > 3) break;
          }
          if (numSurroundingRollsOfPaper > 3) break;
        }

        if (numSurroundingRollsOfPaper > 3) continue;
        res++;
      }
    }

    return res;
  }

  public Integer solvePart02(List<List<Character>> rollsOfPaper) {
    int res = 0;

    int rows = rollsOfPaper.size();
    int cols = rollsOfPaper.get(0).size();

    boolean rollOfPaperRemoved = true;
    boolean[][] removed = new boolean[rows][cols];
    for (boolean[] array : removed) Arrays.fill(array, false);

    while (rollOfPaperRemoved) {
      rollOfPaperRemoved = false;

      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          Character character = rollsOfPaper.get(i).get(j);
          if (!character.equals('@') || removed[i][j]) continue;
          int numSurroundingRollsOfPaper = 0;

          for (int k = -1; k < 2; k++) {
            int y = i + k;
            if (y < 0 || y > rows - 1) continue;

            for (int l = -1; l < 2; l++) {
              int x = j + l;
              if (x < 0 || x > cols - 1 || (k == 0 && l == 0)) continue;

              Character surroundingCharacter = rollsOfPaper.get(y).get(x);
              if (surroundingCharacter.equals('.') || removed[y][x]) continue;

              numSurroundingRollsOfPaper++;
              if (numSurroundingRollsOfPaper > 3) break;
            }
            if (numSurroundingRollsOfPaper > 3) break;
          }

          if (numSurroundingRollsOfPaper > 3) continue;
          res++;
          removed[i][j] = true;
          rollOfPaperRemoved = true;
        }
      }
    }

    return res;
  }
}
