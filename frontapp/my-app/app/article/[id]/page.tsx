'use client'
import { useParams } from "next/navigation";
import { useState, useEffect } from 'react'
import Link from "next/link";

type articleInterface = {
  id: number,
  createdDate: string,
  modifiedDate: string,
  title: string,
  content: string
}
export default function Article() {
  const [article, setArticle] = useState<articleInterface>();
  const params = useParams();

  useEffect(() => {
    fetch(`http://localhost:8090/api/v1/articles/${params.id}`,{
        method: 'GET',
        credentials: 'include', // 핵심 변경점
    })
    .then(result => result.json())
    .then(result => setArticle(result.data.article))
}, [])

  return (
    <div>
      {article?.id} | {article?.title} | {article?.content} | {article?.createdDate} | {article?.modifiedDate}
      <br />
      <Link href={`/article/${article?.id}/patch`}>🅿수정</Link>
      <br />
    </div>
  );
}