"use client";
import { useParams } from "next/navigation";
import { useState, useEffect } from "react";
import Link from "next/link";
import api from "@/app/utils/api";

type articleType = {
    id: number;
    createdDate: string;
    modifiedDate: string;
    title: string;
    content: string;
};
export default function Article() {
    const [article, setArticle] = useState<articleType>();
    const params = useParams();

    const fetchArticle = () => {
        api.get(`/articles/${params.id}`)
            .then((response) => setArticle(response.data.data.article))
            .catch((err) => {
                console.log(err);
            });
    };

    useEffect(() => {
        fetchArticle();
    }, []);

    return (
        <div>
            {article?.id} | {article?.title} | {article?.content} | {article?.createdDate} | {article?.modifiedDate}
            <br />
            <Link href={`/article/${article?.id}/patch`}>🅿수정</Link>
            <br />
        </div>
    );
}
