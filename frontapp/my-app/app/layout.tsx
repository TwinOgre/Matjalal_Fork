import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import "./home.css";
import Footer from './components/Footer.tsx';

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Matjalal",
  description: "food custom and Recomandation",
  icons: {
    icon: "/favicon-16x16.png",
  },
};


export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className={inter.className}>
        {children}
        <Footer />
      </body>
    </html>
  );
}
