import { XMLParser } from "fast-xml-parser";
import { readFile } from "node:fs/promises";
import path from "node:path";

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
  const days = [...Array(2).keys()];

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

  return days.map((day, i) => {
    const report = reports[i];
    const partOne = report.testsuite.testcase[0];
    const partTwo = report.testsuite.testcase[1];
    return (
      <div key={report.testsuite["@_name"]}>
        <div>
          <div>Day {day}</div>
          <div>
            {report.testsuite.testcase.reduce(
              (acc, testcase) =>
                acc + (testcase ? Number(testcase["@_time"]) * 1000 : 0),
              0,
            )}
            ms
          </div>
        </div>
        <div>
          {!!partOne && (
            <div>
              <div>Part 1</div>
              <div>{Number(partOne["@_time"]) * 1000} ms</div>
            </div>
          )}
          {!!partTwo && (
            <div>
              <div>Part 2</div>
              <div>{Number(partTwo["@_time"]) * 1000} ms</div>
            </div>
          )}
        </div>
      </div>
    );
  });
}
