"use client";

import { Moon, Sun } from "lucide-react";
import { Button } from "@/components/ui/button";

export function ThemeToggler() {
  const handleClick = () => {
    const theme = document.documentElement.getAttribute("class");
    if (theme === "dark") {
      document.documentElement.setAttribute("class", "light");
      document.documentElement.setAttribute("style", "color-scheme: light;");
    } else if (theme === "light") {
      document.documentElement.setAttribute("class", "dark");
      document.documentElement.setAttribute("style", "color-scheme: dark;");
    }
  };

  return (
    <Button variant="outline" size="icon" onClick={handleClick}>
      <Sun className="h-[1.2rem] w-[1.2rem] scale-100 rotate-0 transition-all dark:scale-0 dark:-rotate-90" />
      <Moon className="absolute h-[1.2rem] w-[1.2rem] scale-0 rotate-90 transition-all dark:scale-100 dark:rotate-0" />
      <span className="sr-only">Toggle theme</span>
    </Button>
  );
}
