package org.example.day01;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class Day01 {
  private final String inputsDir;
  private final BigDecimal buttonACost;
  private final BigDecimal buttonBCost;

  public Day01() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day01")
            .toString();
    this.buttonACost = new BigDecimal("3");
    this.buttonBCost = new BigDecimal("1");
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

  public boolean bigDecimalIsInteger(BigDecimal bigDecimal) {
    return bigDecimal.stripTrailingZeros().scale() <= 0;
  }

  public Optional<BigDecimal> getMinimumTokens(
      Machine machine, BigDecimal prizeOffset, BigDecimal maxButtonPresses) {
    Prize prize = machine.getPrize();
    BigDecimal prizeX = prizeOffset.add(BigDecimal.valueOf(prize.getX()));
    BigDecimal prizeY = prizeOffset.add(BigDecimal.valueOf(prize.getY()));

    Button buttonA = machine.getButtonA();
    BigDecimal buttonAX = BigDecimal.valueOf(buttonA.getX());
    BigDecimal buttonAY = BigDecimal.valueOf(buttonA.getY());

    Button buttonB = machine.getButtonB();
    BigDecimal buttonBX = BigDecimal.valueOf(buttonB.getX());
    BigDecimal buttonBY = BigDecimal.valueOf(buttonB.getY());

    BigDecimal buttonAPresses =
        buttonBY
            .multiply(prizeX)
            .subtract(buttonBX.multiply(prizeY))
            .divide(
                buttonBY.multiply(buttonAX).subtract(buttonBX.multiply(buttonAY)),
                10,
                RoundingMode.HALF_UP)
            .stripTrailingZeros();

    if (buttonAPresses.compareTo(new BigDecimal("0")) < 0
        || (maxButtonPresses != null && buttonAPresses.compareTo(maxButtonPresses) > 0)
        || !bigDecimalIsInteger(buttonAPresses)) return Optional.empty();

    BigDecimal buttonBPresses =
        prizeX
            .subtract(buttonAPresses.multiply(buttonAX))
            .divide(buttonBX, 10, RoundingMode.HALF_UP);

    if (!bigDecimalIsInteger(buttonBPresses)) {
      System.out.println("prizeX: " + prizeX);
      System.out.println("buttonAPresses: " + buttonAPresses);
      System.out.println("buttonAX: " + buttonAX);
      System.out.println("buttonBX: " + buttonBX);
      System.out.println("divide: " + buttonBPresses);
    }

    return Optional.of(
        buttonBPresses
            .multiply(buttonBCost)
            .add(buttonACost.multiply(buttonAPresses))
            .stripTrailingZeros());
  }

  public BigDecimal solveCase01(List<Machine> machines) {
    BinaryOperator<BigDecimal> binaryOperator = (a, b) -> a.add(b);
    return machines.stream()
        .map(machine -> getMinimumTokens(machine, new BigDecimal("0"), new BigDecimal("100")))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(
            new BigDecimal("0"), (acc, minimumTokens) -> acc.add(minimumTokens), binaryOperator);
  }

  public BigDecimal solveCase02(List<Machine> machines) {
    BinaryOperator<BigDecimal> binaryOperator = (a, b) -> a.add(b);
    return machines.stream()
        .map(machine -> getMinimumTokens(machine, new BigDecimal("10000000000000"), null))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(
            new BigDecimal("0"), (acc, minimumTokens) -> acc.add(minimumTokens), binaryOperator);
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
