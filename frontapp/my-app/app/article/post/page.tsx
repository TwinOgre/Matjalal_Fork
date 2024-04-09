'use client'
import { redirect } from "next/navigation";
import { useState } from "react";

export default function Post() {

  function CreateArticle() {

  }
  return (
    <div>
      게시글을 등록합니다. 📌
      <br />
      <br />
      <ArticleForm />
    </div>
  );
}
function ArticleForm() {

  const [article, setArticle] = useState({ title: '', content: '' });
  const handleSubmit = async (e: any) => {
    e.preventDefault();

    const response = await fetch("http://localhost:8090/api/v1/articles", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(article)
    });

    if (response.ok) {
      alert('게시물이 성공적으로 등록되었습니다.');
      
    } else {
      alert('게시물 등록에 실패했습니다.');
    }

  }
  const handleChange = (e: any) => {
    const { name, value } = e.target;
    // const name: any = e.target.name;
    // const value = e.target.value;
    setArticle({ ...article, [name]: value });
    console.log({ ...article, [name]: value })
  }


  return (
    <>
      <form onSubmit={handleSubmit}>
        <span>제목</span>
        <input type="text" name="title" className="inputer" id="articleTitle" value={article.title} onChange={handleChange} />
        <br />
        <br />
        <span>내용</span>
        <input type="text" className="inputer" id="articleContent" name="content" value={article.content} onChange={handleChange} />
        <button type="submit">등록</button>
      </form>
    </>
  );
}