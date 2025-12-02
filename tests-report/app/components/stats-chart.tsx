"use client";

import { Chart, registerables } from "chart.js";
import { DayStats } from "../page";
import { nsToMs } from "../utils/time";
Chart.register(...registerables);

export function StatsChart({
  stats,
  className,
}: {
  stats: DayStats[];
  className?: string;
}) {
  const data = {
    labels: stats.reduce(
      (acc, { day, partOneDurationNs, partTwoDurationNs }) => {
        if (partOneDurationNs) acc.push(`Day ${day}, part 1`);
        if (partTwoDurationNs) acc.push(`Day ${day}, part 2`);
        return acc;
      },
      [] as string[],
    ),
    datasets: [
      {
        data: stats.reduce((acc, { partOneDurationNs, partTwoDurationNs }) => {
          if (partOneDurationNs) acc.push(nsToMs(partOneDurationNs));
          if (partTwoDurationNs) acc.push(nsToMs(partTwoDurationNs));
          return acc;
        }, [] as number[]),
        labels: () => {
          return "label";
        },
        backgroundColor: [
          "#1F77B4",
          "#AEC7E8",
          "#FF7F0E",
          "#FFBB78",
          "#2CA02C",
          "#98DF8A",
          "#D62728",
          "#FF9896",
          "#9467BD",
          "#C5B0D5",
          "#8C564B",
          "#C49C94",
          "#E377C2",
          "#F7B6D2",
          "#7F7F7F",
          "#C7C7C7",
          "#BCBD22",
          "#DBDB8D",
          "#17BECF",
          "#9EDAE5",
          "#393B79",
          "#6B6ECF",
          "#637939",
          "#8CA252",
        ],
        hoverOffset: 4,
      },
    ],
  };
  const config = {
    type: "doughnut",
    data: data,
  } as const;

  return (
    <div className={className}>
      <canvas
        ref={(ref) => {
          if (!ref) return;
          const chart = new Chart(ref, config);
          return () => chart.destroy();
        }}
      ></canvas>
    </div>
  );
}
