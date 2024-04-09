import Link from "next/link";
export default function ArticleLayout({
    children,
  }: Readonly<{
    children: React.ReactNode;
  }>) {
    return (
        <>
        <h1>게시판입니다.✔</h1> <span>|</span> <Link href="/article/post">게시글 등록 페이지</Link>
        
        {children}
        </>
    )
  }