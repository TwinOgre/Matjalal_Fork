import type { Metadata } from "next";
import { Inter } from "next/font/google";
// import "./globals.css";
import Link from "next/link";

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
      <Link href="/">홈</Link> <span> | </span>
      <Link href="/article">게시글</Link> <span> | </span>
      <Link href="/member/login">로그인</Link> <span> | </span>
      <Link href="/about">소개</Link>
      <br/> 
        {children}</body>
    </html>
  );
}
