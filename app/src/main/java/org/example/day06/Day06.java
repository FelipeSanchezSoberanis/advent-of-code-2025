package org.example.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    List<String> lines = Files.lines(Paths.get(inputsDir, filename)).collect(Collectors.toList());

    String lastLine = lines.removeLast();
    List<Integer> indexes = new ArrayList<>();
    IntStream.range(0, lastLine.length())
        .forEach(
            i -> {
              char character = lastLine.charAt(i);
              if (character == ' ') return;
              indexes.add(i);
              Operator operator = null;
              switch (character) {
                case '+':
                  operator = Operator.ADD;
                  break;
                case '*':
                  operator = Operator.MULTIPLY;
                  break;
                default:
                  throw new UnexpectedOperatorException(character);
              }
              problems.add(Problem.builder().operator(operator).numbers(new ArrayList<>()).build());
            });
    indexes.add(lastLine.length());

    lines.forEach(
        line -> {
          IntStream.range(0, indexes.size() - 2)
              .forEach(
                  i -> {
                    problems
                        .get(i)
                        .getNumbers()
                        .add(line.substring(indexes.get(i), indexes.get(i + 1) - 1));
                  });

          problems
              .get(indexes.size() - 2)
              .getNumbers()
              .add(line.substring(indexes.get(indexes.size() - 2)));
        });

    return problems;
  }

  public Long solvePart01(List<Problem> problems) {
    return problems.stream()
        .reduce(
            0L,
            (problemAcc, problem) -> {
              long problemAccInitialValue = Long.parseLong(problem.getNumbers().getFirst().strip());
              return problemAcc
                  + problem.getNumbers().stream()
                      .skip(1)
                      .reduce(
                          problemAccInitialValue,
                          (numberAcc, number) -> {
                            long parsedNumber = Long.parseLong(number.strip());
                            switch (problem.getOperator()) {
                              case ADD:
                                return numberAcc + parsedNumber;
                              case MULTIPLY:
                                return numberAcc * parsedNumber;
                              default:
                                throw new UnexpectedOperatorException(problem.getOperator());
                            }
                          },
                          Long::sum);
            },
            Long::sum);
  }

  public Long solvePart02(List<Problem> problems) {
    return problems.stream()
        .reduce(
            0L,
            (problemAcc, problem) ->
                problemAcc
                    + IntStream.range(0, problem.getNumbers().getFirst().length())
                        .mapToObj(i -> concatenateNumberAtColumn(problem, i))
                        .skip(1)
                        .reduce(
                            concatenateNumberAtColumn(problem, 0),
                            (numberAcc, number) -> {
                              switch (problem.getOperator()) {
                                case ADD:
                                  return numberAcc + number;
                                case MULTIPLY:
                                  return numberAcc * number;
                                default:
                                  throw new UnexpectedOperatorException(problem.getOperator());
                              }
                            },
                            Long::sum),
            Long::sum);
  }

  private long concatenateNumberAtColumn(Problem problem, Integer i) {
    return Long.parseLong(
        problem.getNumbers().stream()
            .collect(
                () -> new StringBuilder(),
                (acc, number) -> {
                  Character character = number.charAt(i);
                  if (character != ' ') acc.append(character);
                },
                StringBuilder::append)
            .toString());
  }

  @Builder
  @Getter
  @Setter
  @ToString
  public static class Problem {
    private List<String> numbers;
    private Operator operator;
  }

  public static enum Operator {
    MULTIPLY,
    ADD
  }

  public static class UnexpectedOperatorException extends RuntimeException {
    public UnexpectedOperatorException(Character operator) {
      super(String.format("Unexpected operator '%s'", operator));
    }

    public UnexpectedOperatorException(Operator operator) {
      super(String.format("Unexpected operator '%s'", operator));
    }
  }
}
