"use client";

import { Moon, Sun } from "lucide-react";
import { Button } from "@/components/ui/button";
import { useEffect } from "react";
import { Chart } from "chart.js";

export function ThemeToggler() {
  const setTheme = (theme: string) => {
    document.documentElement.setAttribute("class", theme);
    document.documentElement.setAttribute("style", `color-scheme: ${theme};`);
    localStorage.setItem("theme", theme);
    if (theme === "dark" || theme === "system dark") {
      Chart.defaults.color = "oklch(0.985 0 0)";
    } else {
      Chart.defaults.color = "oklch(0.145 0 0)";
    }
    Object.values(Chart.instances).forEach((chart) => chart.update());
  };

  const handleClick = () => {
    const theme = document.documentElement.getAttribute("class");
    if (theme === "dark" || theme === "system dark") setTheme("light");
    else setTheme("dark");
  };

  useEffect(() => {
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme) setTheme(savedTheme);
  }, []);

  return (
    <Button variant="outline" size="icon" onClick={handleClick}>
      <Sun className="h-[1.2rem] w-[1.2rem] scale-100 rotate-0 transition-all dark:scale-0 dark:-rotate-90" />
      <Moon className="absolute h-[1.2rem] w-[1.2rem] scale-0 rotate-90 transition-all dark:scale-100 dark:rotate-0" />
      <span className="sr-only">Toggle theme</span>
    </Button>
  );
}
