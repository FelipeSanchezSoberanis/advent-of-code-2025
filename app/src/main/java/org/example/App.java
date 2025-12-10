package org.example;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.day01.Day01;
import org.example.day02.Day02;
import org.example.day02.Day02.IdRange;
import org.example.day03.Day03;
import org.example.day04.Day04;
import org.example.day05.Day05;
import org.example.day06.Day06;
import org.example.day07.Day07;
import org.example.day08.Day08;
import org.example.day09.Day09;

public class App {
  public static <T> Long timeWarmedUpFunction(Supplier<T> func) {
    for (int i = 0; i < 100; i++) func.get();

    List<Long> durations = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      long start = System.nanoTime();
      func.get();
      long end = System.nanoTime();
      durations.add(end - start);
    }

    return Math.round(durations.stream().mapToLong(duration -> duration).average().orElseThrow());
  }

  public static DayStats getDay01Stats() throws IOException {
    Day01 day01 = new Day01();
    List<Day01.Rotation> rotations = day01.parseInput("input.txt");
    return DayStats.builder()
        .day(1)
        .partOneDurationNs(timeWarmedUpFunction(() -> day01.solvePart01(rotations)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day01.solvePart02(rotations)))
        .build();
  }

  public static DayStats getDay02Stats() throws IOException {
    Day02 day02 = new Day02();
    List<IdRange> idRanges = day02.parseInput("input.txt");
    return DayStats.builder()
        .day(2)
        .partOneDurationNs(timeWarmedUpFunction(() -> day02.solvePart01(idRanges)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day02.solvePart02(idRanges)))
        .build();
  }

  public static DayStats getDay03Stats() throws IOException {
    Day03 day03 = new Day03();
    List<List<Long>> banks = day03.parseInput("input.txt");
    return DayStats.builder()
        .day(3)
        .partOneDurationNs(timeWarmedUpFunction(() -> day03.solvePart01(banks)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day03.solvePart02(banks)))
        .build();
  }

  public static DayStats getDay04Stats() throws IOException {
    Day04 day04 = new Day04();
    List<List<Character>> rollsOfPaper = day04.parseInput("input.txt");
    return DayStats.builder()
        .day(4)
        .partOneDurationNs(timeWarmedUpFunction(() -> day04.solvePart01(rollsOfPaper)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day04.solvePart02(rollsOfPaper)))
        .build();
  }

  public static DayStats getDay05Stats() throws IOException {
    Day05 day05 = new Day05();
    Day05.Input input = day05.parseInput("input.txt");
    return DayStats.builder()
        .day(5)
        .partOneDurationNs(timeWarmedUpFunction(() -> day05.solvePart01(input)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day05.solvePart02(input)))
        .build();
  }

  public static DayStats getDay06Stats() throws IOException {
    Day06 day06 = new Day06();
    List<Day06.Problem> problems = day06.parseInput("input.txt");
    return DayStats.builder()
        .day(6)
        .partOneDurationNs(timeWarmedUpFunction(() -> day06.solvePart01(problems)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day06.solvePart02(problems)))
        .build();
  }

  public static DayStats getDay07Stats() throws IOException {
    Day07 day07 = new Day07();
    List<String> rows = day07.parseInput("input.txt");
    return DayStats.builder()
        .day(7)
        .partOneDurationNs(timeWarmedUpFunction(() -> day07.solvePart01(rows)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day07.solvePart02(rows)))
        .build();
  }

  public static DayStats getDay08Stats() throws IOException {
    Day08 day08 = new Day08();
    List<Day08.JunctionBox> junctionBoxes = day08.parseInput("input.txt");
    return DayStats.builder()
        .day(8)
        .partOneDurationNs(timeWarmedUpFunction(() -> day08.solvePart01(junctionBoxes, 1000)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day08.solvePart02(junctionBoxes)))
        .build();
  }

  public static DayStats getDay09Stats() throws IOException {
    Day09 day09 = new Day09();
    List<Day09.RedTile> redTiles = day09.parseInput("input.txt");
    return DayStats.builder()
        .day(9)
        .partOneDurationNs(timeWarmedUpFunction(() -> day09.solvePart01(redTiles)))
        .build();
  }

  public static void main(String[] args) throws IOException {
    List<DayStats> stats = new ArrayList<>();
    stats.add(getDay01Stats());
    stats.add(getDay02Stats());
    stats.add(getDay03Stats());
    stats.add(getDay04Stats());
    stats.add(getDay05Stats());
    stats.add(getDay06Stats());
    stats.add(getDay07Stats());
    stats.add(getDay08Stats());
    stats.add(getDay09Stats());

    Files.writeString(
        Paths.get(System.getProperty("user.dir"), "build", "stats.json"), new Gson().toJson(stats));
  }

  @Builder
  @ToString
  @Getter
  public static class DayStats {
    private Integer day;
    private Long partOneDurationNs;
    private Long partTwoDurationNs;
  }
}
