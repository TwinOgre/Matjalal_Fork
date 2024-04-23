"use client";
import { useState, useEffect } from "react";
import Link from "next/link";
import axios from "axios";
import api from "../utils/api";
interface articlesInterface {
    id: number;
    createdDate: string;
    modifiedDate: string;
    title: string;
    content: string;
}

export default function Article() {
    const [articles, setArticles] = useState<articlesInterface[]>([]);

    const fetchArticles = () => {
        api.get("/articles")
            .then((response) => setArticles(response.data.data.articles))
            .catch((err) => {
                console.log(err);
            });
    };

    useEffect(() => {
        fetchArticles();
    }, []);

    const handleDelete = async (articleId: number) => {
        const fetchParam: string = "/articles/" + articleId;
        const response = await api.delete(fetchParam);
    };
    return (
        <>
            <h1>Articles</h1>
            <h2>articlies</h2>
            <ul>
                {articles.map((article) => (
                    <li key={article.id}>
                        <Link href={`/article/${article.id}`}>
                            {article.id}| {article.title} | {article.content}
                        </Link>{" "}
                        | {article.createdDate} | {article.modifiedDate}
                        <button onClick={() => handleDelete(article.id)}>💥삭제</button>
                    </li>
                ))}
            </ul>
        </>
    );
}
