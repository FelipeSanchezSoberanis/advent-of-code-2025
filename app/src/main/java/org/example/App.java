package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.example.day01.Day01;

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
        .partOneDurationNs(timeWarmedUpFunction(() -> day01.solvePartOne(rotations)))
        .partTwoDurationNs(timeWarmedUpFunction(() -> day01.solvePartTwo(rotations)))
        .build();
  }

  public static Gson getGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(
        Duration.class,
        new JsonSerializer<Duration>() {
          @Override
          public JsonElement serialize(
              Duration src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
          }
        });
    return gsonBuilder.create();
  }

  public static void main(String[] args) throws IOException {
    Gson gson = getGson();

    List<DayStats> stats = new ArrayList<>();
    stats.add(getDay01Stats());

    Files.writeString(
        Paths.get(System.getProperty("user.dir"), "build", "stats.json"), gson.toJson(stats));
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
