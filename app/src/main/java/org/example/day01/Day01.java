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
  private final Long buttonACost;
  private final Long buttonBCost;

  public Day01() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day01")
            .toString();
    this.buttonACost = 3L;
    this.buttonBCost = 1L;
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
            machines.add(Machine.builder().buttonA(buttonA).buttonB(buttonB).prize(prize).build());
            break;
          case 3:
            break;
        }
        i++;
      }
    }
    return machines;
  }

  public Optional<Long> getMinimumTokens(Machine machine, Long prizeOffset, Long maxButtonPresses) {
    Prize prize = machine.getPrize();
    Long prizeX = prizeOffset + prize.getX();
    Long prizeY = prizeOffset + prize.getY();

    Button buttonA = machine.getButtonA();
    Long buttonAX = Long.valueOf(buttonA.getX());
    Long buttonAY = Long.valueOf(buttonA.getY());

    Button buttonB = machine.getButtonB();
    Long buttonBX = Long.valueOf(buttonB.getX());
    Long buttonBY = Long.valueOf(buttonB.getY());

    Double buttonAPresses =
        ((buttonBY * prizeX - buttonBX * prizeY) * 1.0)
            / ((buttonBY * buttonAX - buttonBX * buttonAY) * 1.0);

    if (buttonAPresses < 0 || buttonAPresses > maxButtonPresses || buttonAPresses % 1 != 0) {
      return Optional.empty();
    }

    Double buttonBPresses =
        ((prizeX - (buttonAPresses.longValue() * buttonAX)) * 1.0) / (buttonBX * 1.0);

    if (buttonBPresses < 0 || buttonBPresses > maxButtonPresses || buttonBPresses % 1 != 0) {
      return Optional.empty();
    }

    return Optional.of(
        buttonBPresses.longValue() * buttonBCost + buttonAPresses.longValue() * buttonACost);
  }

  public Long solvePart01(List<Machine> machines) {
    return machines.stream()
        .map(machine -> getMinimumTokens(machine, 0L, 100L))
        .filter(Optional::isPresent)
        .mapToLong(Optional::get)
        .sum();
  }

  public Long solvePart02(List<Machine> machines) {
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
