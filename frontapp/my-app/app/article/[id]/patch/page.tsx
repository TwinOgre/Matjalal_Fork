'use client'
import api from "@/app/utils/api";
import { useParams, useRouter } from "next/navigation";
import { useState } from "react";

export default function Patch(){

    return (
        <>
         | 게시글을 수정합니다. 📌
      <br />
      <br />
      <ArticleModifyForm />
        </>
    )
}
function ArticleModifyForm() {
    const idParam = useParams();
    const router = useRouter();
    const fetchParam : string = "http://localhost:8090/api/v1/articles/" + idParam.id;
    const [article, setArticle] = useState({ title: '', content: '' });
    const handleSubmit = async (e: any) => {
      e.preventDefault();
      
      try {
        await api.patch(fetchParam , {
            title: article.title,
            content: article.content
        });
        console.log("Article updated successfully!");
        // 추가적인 로직이 필요한 경우 여기에 작성
    } catch (error) {
        console.error("An error occurred while updating the article:", error);
        // 에러 처리 로직을 추가할 수 있습니다. 예를 들어, 사용자에게 오류 메시지를 표시하거나 다시 시도할 수 있도록 유도할 수 있습니다.
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
          <input type="text" name="title" className="inputer" id="articleTitle" value={article.title} 
          onChange={handleChange} />
          <br />
          <br />
          <span>내용</span>
          <input type="text" className="inputer" id="articleContent" name="content" value={article.content} 
          onChange={handleChange} />
          <button type="submit">수정</button>
        </form>
      </>
    );
  }

