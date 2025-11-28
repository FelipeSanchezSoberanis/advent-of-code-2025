package org.example.day01;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class Day01 {
  private final String inputsDir;
  private final Integer buttonACost;
  private final Integer buttonBCost;

  public Day01() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day01")
            .toString();
    this.buttonACost = 3;
    this.buttonBCost = 1;
  }

  public List<Machine> parseInput(String filename) throws IOException {
    List<Machine> machines = new ArrayList<>();
    try (Scanner scanner = new Scanner(Paths.get(inputsDir, filename))) {
      int i = 0;
      Button buttonA = null;
      Button buttonB = null;
      Prize prize = null;
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        switch (i % 4) {
          case 0:
            buttonA =
                Button.builder()
                    .x(Integer.parseInt(line.substring(line.indexOf("X+") + 2, line.indexOf(","))))
                    .y(Integer.parseInt(line.substring(line.indexOf("Y+") + 2)))
                    .build();
            break;
          case 1:
            buttonB =
                Button.builder()
                    .x(Integer.parseInt(line.substring(line.indexOf("X+") + 2, line.indexOf(","))))
                    .y(Integer.parseInt(line.substring(line.indexOf("Y+") + 2)))
                    .build();
            break;
          case 2:
            prize =
                Prize.builder()
                    .x(Integer.parseInt(line.substring(line.indexOf("X=") + 2, line.indexOf(","))))
                    .y(Integer.parseInt(line.substring(line.indexOf("Y=") + 2)))
                    .build();
            break;
          case 3:
            machines.add(Machine.builder().buttonA(buttonA).buttonB(buttonB).prize(prize).build());
            break;
        }
        i++;
      }
    }
    return machines;
  }

  public Optional<Long> getMinimumTokens(Machine machine, Long prizeOffset, Long maxButtonPresses) {
    Prize prize = machine.getPrize();
    Long prizeX = prize.getX() + prizeOffset;
    Long prizeY = prize.getY() + prizeOffset;

    Button buttonA = machine.getButtonA();
    Button buttonB = machine.getButtonB();

    double buttonAPresses =
        ((buttonB.getY() * prizeX - buttonB.getX() * prizeY) * 1.0)
            / ((buttonB.getY() * buttonA.getX() - buttonB.getX() * buttonA.getY()) * 1.0);

    if (buttonAPresses <= 0
        || buttonAPresses > maxButtonPresses
        || Math.rint(buttonAPresses) != buttonAPresses) return Optional.empty();

    return Optional.of(
        (prizeX - Math.round(buttonAPresses) * buttonA.getX()) / (buttonB.getX()) * buttonBCost
            + buttonACost * Math.round(buttonAPresses));
  }

  public long solveCase01(List<Machine> machines) {
    return machines.stream()
        .map(machine -> getMinimumTokens(machine, 0L, 100L))
        .filter(Optional::isPresent)
        .mapToLong(Optional::get)
        .sum();
  }

  public long solveCase02(List<Machine> machines) {
    return machines.stream()
        .map(machine -> getMinimumTokens(machine, 10000000000000L, Long.MAX_VALUE))
        .filter(Optional::isPresent)
        .mapToLong(Optional::get)
        .sum();
  }

  @Builder
  @Getter
  @ToString
  public static class Button {
    private final Integer x;
    private final Integer y;
  }

  @Builder
  @Getter
  @ToString
  public static class Prize {
    private final Integer x;
    private final Integer y;
  }

  @Builder
  @Getter
  @ToString
  public static class Machine {
    private final Button buttonA;
    private final Button buttonB;
    private final Prize prize;
  }
}
