package org.example.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class Day06 {
  private final String inputsDir;

  public Day06() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day06")
            .toString();
  }

  public List<Problem> parseInput(String filename) throws IOException {
    List<Problem> problems = new ArrayList<>();

    Files.lines(Paths.get(inputsDir, filename))
        .forEach(
            line -> {
              if (line.isBlank()) return;

              if (line.startsWith("+") || line.startsWith("*")) {
                List<Operator> operators =
                    Arrays.stream(line.split(" "))
                        .map(String::strip)
                        .filter(part -> !part.isBlank())
                        .map(
                            part -> {
                              switch (part) {
                                case "+":
                                  return Operator.ADD;
                                case "*":
                                  return Operator.MULTIPLY;
                                default:
                                  throw new RuntimeException(
                                      String.format("Unexpected operator '%s'", part));
                              }
                            })
                        .collect(Collectors.toList());

                IntStream.range(0, operators.size())
                    .forEach(
                        i -> {
                          problems.get(i).setOperator(operators.get(i));
                        });
                return;
              }

              List<Long> numbers =
                  Arrays.stream(line.split(" "))
                      .map(String::strip)
                      .filter(part -> !part.isBlank())
                      .map(Long::parseLong)
                      .collect(Collectors.toList());

              if (problems.size() < numbers.size()) {
                IntStream.range(0, numbers.size())
                    .forEach(
                        (_) -> problems.add(Problem.builder().numbers(new ArrayList<>()).build()));
              }

              IntStream.range(0, numbers.size())
                  .forEach(
                      i -> {
                        problems.get(i).getNumbers().add(numbers.get(i));
                      });
            });

    return problems;
  }

  public Long solvePart01(List<Problem> problems) {
    return problems.stream()
        .reduce(
            0L,
            (problemAcc, problem) ->
                problemAcc
                    + problem.getNumbers().stream()
                        .skip(1)
                        .reduce(
                            problem.getNumbers().getFirst(),
                            (numberAcc, number) -> {
                              switch (problem.getOperator()) {
                                case ADD:
                                  return numberAcc + number;
                                case MULTIPLY:
                                  return numberAcc * number;
                                default:
                                  throw new RuntimeException(
                                      String.format(
                                          "Unexpected operator '%s'", problem.getOperator()));
                              }
                            },
                            Long::sum),
            Long::sum);
  }

  public Long solvePart02(List<Problem> problems) {
    return null;
  }

  @Builder
  @Getter
  @Setter
  @ToString
  public static class Problem {
    private List<Long> numbers;
    private Operator operator;
  }

  public static enum Operator {
    MULTIPLY,
    ADD
  }
}
