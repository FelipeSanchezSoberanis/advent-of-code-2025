import { XMLParser } from "fast-xml-parser";
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

type TestReport = {
  "?xml": { "@_version": string; "@_encoding": string };
  testsuite: {
    properties: string;
    testcase: ({
      "@_name": string;
      "@_classname": string;
      "@_time": string;
    } | null)[];
    "system-out": string;
    "system-err": string;
    "@_name": string;
    "@_tests": string;
    "@_skipped": string;
    "@_failures": string;
    "@_errors": string;
    "@_timestamp": string;
    "@_hostname": string;
    "@_time": string;
  };
};

export default async function Home() {
  const days = [
    ...Array(1)
      .keys()
      .map((day) => day + 1),
  ];

  const files = days.map((day) =>
    path.join(
      "..",
      "app",
      "build",
      "test-results",
      "test",
      `TEST-org.example.day${day.toString().padStart(2, "0")}.Day${day.toString().padStart(2, "0")}Test.xml`,
    ),
  );

  const xmlParser = new XMLParser({ ignoreAttributes: false });
  const reports: TestReport[] = await Promise.all(
    files.map(async (file) => {
      const fileContent = await readFile(file, { encoding: "utf-8" });
      return xmlParser.parse(fileContent);
    }),
  );

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
          {reports.reduce((acc, report) => {
            return (
              acc +
              report.testsuite.testcase.reduce((accTwo, testcase) => {
                if (!testcase || testcase["@_name"].includes("Part00")) {
                  return accTwo;
                }
                return accTwo + Number(testcase["@_time"]) * 1000;
              }, 0)
            );
          }, 0)}
          ms
        </h3>
        <div className="py-4 flex flex-col gap-2">
          Currently on day {days.length} of 12
          <Progress value={(days.length / 12) * 100} />
        </div>
        <Accordion
          type="multiple"
          defaultValue={days.map((_, i) => reports[i].testsuite["@_name"])}
        >
          {days.map((day, i) => {
            const report = reports[i];
            const partOne = report.testsuite.testcase.find(
              (testcase) => !!testcase && testcase["@_name"].includes("Part01"),
            );
            const partTwo = report.testsuite.testcase.find(
              (testcase) => !!testcase && testcase["@_name"].includes("Part02"),
            );
            return (
              <AccordionItem
                key={report.testsuite["@_name"]}
                value={report.testsuite["@_name"]}
              >
                <AccordionTrigger className="text-base">
                  <div className="w-full">Day {day}</div>
                  <div className="whitespace-nowrap">
                    {report.testsuite.testcase.reduce((acc, testcase) => {
                      if (!testcase || testcase["@_name"].includes("Part00")) {
                        return acc;
                      }
                      return acc + Number(testcase["@_time"]) * 1000;
                    }, 0)}{" "}
                    ms
                  </div>
                </AccordionTrigger>
                <AccordionContent className="text-base">
                  {!!partOne && (
                    <div className="flex justify-between">
                      <div>Part 1</div>
                      <div>{Number(partOne["@_time"]) * 1000} ms</div>
                    </div>
                  )}
                  {!!partTwo && (
                    <div className="flex justify-between">
                      <div>Part 2</div>
                      <div>{Number(partTwo["@_time"]) * 1000} ms</div>
                    </div>
                  )}
                  <div className="pt-4 text-blue-600">
                    <a
                      href={`https://github.com/FelipeSanchezSoberanis/advent-of-code-2025/blob/main/app/src/main/java/org/example/day${day.toString().padStart(2, "0")}/Day${day.toString().padStart(2, "0")}.java`}
                      target="_blank"
                      rel="noreferrer noopener"
                    >
                      <div className="flex items-center gap-1">
                        <div>See code for day {day}</div>
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
    </main>
  );
}
