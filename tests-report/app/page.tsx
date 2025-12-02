import { readFile } from "node:fs/promises";
import path from "node:path";
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { SquareArrowOutUpRight } from "lucide-react";
import { ThemeToggler } from "./components/theme-toggler";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Progress } from "@/components/ui/progress";
import { StatsChart } from "./components/stats-chart";
import { nsToMs } from "./utils/time";
import { cn } from "@/lib/utils";

export type DayStats = {
  day: number;
  partOneDurationNs?: number;
  partTwoDurationNs?: number;
};

export default async function Home() {
  const stats = JSON.parse(
    await readFile(path.join("..", "app", "build", "stats.json"), {
      encoding: "utf-8",
    }),
  ) as DayStats[];

  return (
    <main className="p-4">
      <div className="container mx-auto">
        <div className="flex justify-between">
          <h1 className="text-2xl">Advent of Code 2025</h1>
          <ThemeToggler />
        </div>
        <div className="flex gap-2 items-center">
          <Avatar>
            <AvatarImage
              src="https://avatars.githubusercontent.com/u/59965745"
              alt="FelipeSanchezSoberanis"
            />
            <AvatarFallback>FSS</AvatarFallback>
          </Avatar>
          <h2 className="text-xl">Felipe Sánchez Soberanis</h2>
        </div>
        <h3 className="pt-1">
          Total time:{" "}
          {nsToMs(
            stats.reduce(
              (acc, stat) =>
                acc +
                (stat.partOneDurationNs || 0) +
                (stat.partTwoDurationNs || 0),
              0,
            ),
          )}{" "}
          ms
        </h3>
        <div className="py-4 flex flex-col gap-2">
          Currently on day {stats.length} of 12
          <Progress value={(stats.length / 12) * 100} />
        </div>
        <div className="md:flex md:gap-4">
          <StatsChart
            stats={stats}
            className={cn("w-3/4 mx-auto", "md:flex-2")}
          />
          <Accordion
            type="multiple"
            defaultValue={stats.map((stat) => stat.day.toString())}
            className="md:flex-3"
          >
            {stats.map((stat) => {
              return (
                <AccordionItem
                  key={stat.day.toString()}
                  value={stat.day.toString()}
                >
                  <AccordionTrigger className="text-base">
                    <div className="w-full">Day {stat.day}</div>
                    <div className="whitespace-nowrap">
                      {nsToMs(
                        (stat.partOneDurationNs || 0) +
                          (stat.partTwoDurationNs || 0),
                      )}{" "}
                      ms
                    </div>
                  </AccordionTrigger>
                  <AccordionContent className="text-base">
                    {!!stat.partOneDurationNs && (
                      <div className="flex justify-between">
                        <div>Part 1</div>
                        <div>{nsToMs(stat.partOneDurationNs)} ms</div>
                      </div>
                    )}
                    {!!stat.partTwoDurationNs && (
                      <div className="flex justify-between">
                        <div>Part 2</div>
                        <div>{nsToMs(stat.partTwoDurationNs)} ms</div>
                      </div>
                    )}
                    <div className="pt-4 text-blue-600">
                      <a
                        href={`https://github.com/FelipeSanchezSoberanis/advent-of-code-2025/blob/main/app/src/main/java/org/example/day${stat.day.toString().padStart(2, "0")}/Day${stat.day.toString().padStart(2, "0")}.java`}
                        target="_blank"
                        rel="noreferrer noopener"
                      >
                        <div className="flex items-center gap-1">
                          <div>See code for day {stat.day}</div>
                          <SquareArrowOutUpRight className="size-4" />
                        </div>
                      </a>
                    </div>
                  </AccordionContent>
                </AccordionItem>
              );
            })}
          </Accordion>
        </div>
      </div>
    </main>
  );
}
