package org.example.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;

public class Day08 {
  private final String inputsDir;

  public Day08() {
    this.inputsDir =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "inputs", "day08")
            .toString();
  }

  public List<JunctionBox> parseInput(String filename) throws IOException {
    return Files.lines(Paths.get(inputsDir, filename))
        .map(line -> line.split(","))
        .map(
            array ->
                JunctionBox.builder()
                    .x(Integer.parseInt(array[0].strip()))
                    .y(Integer.parseInt(array[1].strip()))
                    .z(Integer.parseInt(array[2].strip()))
                    .build())
        .collect(Collectors.toList());
  }

  public Integer solvePart01(List<JunctionBox> junctionBoxes, Integer limit) {
    return IntStream.range(0, junctionBoxes.size() - 1)
        .boxed()
        .flatMap(
            i ->
                IntStream.range(i + 1, junctionBoxes.size())
                    .boxed()
                    .map(
                        j ->
                            JunctionBoxPairWithDistance.builder()
                                .junctionBoxOne(junctionBoxes.get(i))
                                .junctionBoxTwo(junctionBoxes.get(j))
                                .distance(
                                    getStraightLineDistance(
                                        junctionBoxes.get(i), junctionBoxes.get(j)))
                                .build()))
        .sorted(Comparator.comparing(JunctionBoxPairWithDistance::getDistance))
        .limit(limit)
        .collect(
            () -> (List<Set<JunctionBox>>) new ArrayList<Set<JunctionBox>>(),
            (circuits, junctionBoxPairWithDistance) -> {
              JunctionBox junctionBoxOne = junctionBoxPairWithDistance.getJunctionBoxOne();
              JunctionBox junctionBoxTwo = junctionBoxPairWithDistance.getJunctionBoxTwo();

              Optional<Set<JunctionBox>> circuitWithJunctionBoxOne =
                  circuits.stream().filter(circuit -> circuit.contains(junctionBoxOne)).findFirst();
              Optional<Set<JunctionBox>> circuitWithJunctionBoxTwo =
                  circuits.stream().filter(circuit -> circuit.contains(junctionBoxTwo)).findFirst();

              if (circuitWithJunctionBoxOne
                  .flatMap(
                      _circuitWithJunctionBoxOne ->
                          circuitWithJunctionBoxTwo.map(
                              _circuitWithJunctionBoxTwo -> {
                                if (_circuitWithJunctionBoxOne == _circuitWithJunctionBoxTwo) {
                                  return true;
                                }
                                _circuitWithJunctionBoxOne.addAll(_circuitWithJunctionBoxTwo);
                                circuits.remove(_circuitWithJunctionBoxTwo);
                                return true;
                              }))
                  .orElse(false)) {
                return;
              }

              if (circuitWithJunctionBoxOne
                  .map(
                      _circuitWithJunctionBoxOne -> {
                        _circuitWithJunctionBoxOne.add(junctionBoxTwo);
                        return true;
                      })
                  .orElse(false)) {
                return;
              }

              if (circuitWithJunctionBoxTwo
                  .map(
                      _circuitWithJunctionBoxTwo -> {
                        _circuitWithJunctionBoxTwo.add(junctionBoxOne);
                        return true;
                      })
                  .orElse(false)) {
                return;
              }

              Set<JunctionBox> circuit = new HashSet<>();
              circuit.add(junctionBoxOne);
              circuit.add(junctionBoxTwo);
              circuits.add(circuit);
            },
            (circuitsAcc, circuits) -> circuitsAcc.addAll(circuits))
        .stream()
        .sorted(Comparator.comparing(Set<JunctionBox>::size).reversed())
        .limit(3)
        .map(Set::size)
        .reduce(1, (a, b) -> a * b);
  }

  public Integer solvePart02(List<JunctionBox> junctionBoxes) {
    List<JunctionBoxPairWithDistance> junctionBoxPairsWithDistance =
        IntStream.range(0, junctionBoxes.size() - 1)
            .boxed()
            .flatMap(
                i ->
                    IntStream.range(i + 1, junctionBoxes.size())
                        .boxed()
                        .map(
                            j ->
                                JunctionBoxPairWithDistance.builder()
                                    .junctionBoxOne(junctionBoxes.get(i))
                                    .junctionBoxTwo(junctionBoxes.get(j))
                                    .distance(
                                        getStraightLineDistance(
                                            junctionBoxes.get(i), junctionBoxes.get(j)))
                                    .build()))
            .sorted(Comparator.comparing(JunctionBoxPairWithDistance::getDistance))
            .collect(Collectors.toList());

    List<Set<JunctionBox>> circuits = new ArrayList<>();
    for (JunctionBoxPairWithDistance junctionBoxPairWithDistance : junctionBoxPairsWithDistance) {
      JunctionBox junctionBoxOne = junctionBoxPairWithDistance.getJunctionBoxOne();
      JunctionBox junctionBoxTwo = junctionBoxPairWithDistance.getJunctionBoxTwo();

      Optional<Set<JunctionBox>> circuitWithJunctionBoxOne =
          circuits.stream().filter(circuit -> circuit.contains(junctionBoxOne)).findFirst();
      Optional<Set<JunctionBox>> circuitWithJunctionBoxTwo =
          circuits.stream().filter(circuit -> circuit.contains(junctionBoxTwo)).findFirst();

      if (circuitWithJunctionBoxOne
          .flatMap(
              _circuitWithJunctionBoxOne ->
                  circuitWithJunctionBoxTwo.map(
                      _circuitWithJunctionBoxTwo -> {
                        if (_circuitWithJunctionBoxOne == _circuitWithJunctionBoxTwo) {
                          return true;
                        }
                        _circuitWithJunctionBoxOne.addAll(_circuitWithJunctionBoxTwo);
                        circuits.remove(_circuitWithJunctionBoxTwo);
                        return true;
                      }))
          .orElse(false)) {
        if (circuits.getFirst().size() == junctionBoxes.size()) {
          return junctionBoxOne.getX() * junctionBoxTwo.getX();
        }
        continue;
      }

      if (circuitWithJunctionBoxOne
          .map(
              _circuitWithJunctionBoxOne -> {
                _circuitWithJunctionBoxOne.add(junctionBoxTwo);
                return true;
              })
          .orElse(false)) {
        if (circuits.getFirst().size() == junctionBoxes.size()) {
          return junctionBoxOne.getX() * junctionBoxTwo.getX();
        }
        continue;
      }

      if (circuitWithJunctionBoxTwo
          .map(
              _circuitWithJunctionBoxTwo -> {
                _circuitWithJunctionBoxTwo.add(junctionBoxOne);
                return true;
              })
          .orElse(false)) {
        if (circuits.getFirst().size() == junctionBoxes.size()) {
          return junctionBoxOne.getX() * junctionBoxTwo.getX();
        }
        continue;
      }

      Set<JunctionBox> circuit = new HashSet<>();
      circuit.add(junctionBoxOne);
      circuit.add(junctionBoxTwo);
      circuits.add(circuit);

      if (circuits.getFirst().size() == junctionBoxes.size()) {
        return junctionBoxOne.getX() * junctionBoxTwo.getX();
      }
    }

    throw new RuntimeException("Couldn't join all junction boxes");
  }

  private double getStraightLineDistance(JunctionBox junctionBoxOne, JunctionBox junctionBoxTwo) {
    return Math.sqrt(
        Math.pow(junctionBoxTwo.getX() - junctionBoxOne.getX(), 2)
            + Math.pow(junctionBoxTwo.getY() - junctionBoxOne.getY(), 2)
            + Math.pow(junctionBoxTwo.getZ() - junctionBoxOne.getZ(), 2));
  }

  @Builder
  @Getter
  public static class JunctionBox {
    private final Integer x;
    private final Integer y;
    private final Integer z;

    @Override
    public String toString() {
      return String.format("{ \"x\": %s, \"y\": %s, \"z\": %s }", getX(), getY(), getZ());
    }
  }

  @Builder
  @Getter
  public static class JunctionBoxPairWithDistance {
    private final JunctionBox junctionBoxOne;
    private final JunctionBox junctionBoxTwo;
    private Double distance;

    @Override
    public String toString() {
      return String.format(
          "{ \"junctionBoxOne\": %s, \"junctionBoxTwo\": %s, \"distance\": %s }",
          getJunctionBoxOne(), getJunctionBoxTwo(), getDistance());
    }
  }
}
