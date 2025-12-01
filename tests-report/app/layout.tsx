import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import { ThemeProvider } from "./components/theme-provider";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Felipe's 2025 Advent of Code stats",
  description: "Check out my 2025 Advent of Code stats!",
  authors: { name: "Felipe Sánchez Soberanis" },
  openGraph: {
    title: "Felipe's 2025 Advent of Code stats",
    description: "Check out my 2025 Advent of Code stats!",
    authors: "Felipe Sánchez Soberanis",
    images: "https://avatars.githubusercontent.com/u/59965745",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body
        className={`${geistSans.variable} ${geistMono.variable} antialiased`}
      >
        <ThemeProvider
          attribute="class"
          defaultTheme="system"
          enableSystem
          disableTransitionOnChange
        >
          {children}
        </ThemeProvider>
      </body>
    </html>
  );
}
