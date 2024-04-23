"use client";
import { useParams, useRouter } from "next/navigation";
import { useState, useEffect, FC } from "react";
import api from "@/app/utils/api";
import IngredientTypeBox from "./IngredientTypeBox";
import ReviewBox from "./ReviewBox";
import ReviewForm from "./ReviewForm";
import { ArticleInterface } from "../interface/article/ArticleInterfaces";
import { ReviewInterface } from "../interface/review/ReviewInterfaces";
import Link from "next/link";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { MemberInterface } from "../interface/member/MemberInterfaces";
import { ImageDataInterface } from "../interface/imageData/ImageDataInterfaces";
import Image from "next/image";

interface DetailProps {
    color: string;
    types: string[];
}

const Detail: React.FC<DetailProps> = ({ color, types }) => {
    const router = useRouter();
    const [article, setArticle] = useState<ArticleInterface>({
        id: 0,
        createdDate: "",
        modifiedDate: "",
        subject: "",
        content: "",
        brand: "",
        author: {
            id: 0,
            createdDate: "",
            modifiedDate: "",
            username: "",
            email: "",
        },
        ingredients: [],
    });
    const params = useParams();
    const [reviews, setReviews] = useState<ReviewInterface[]>([]);
    const [member, setMember] = useState<MemberInterface>();
    const fetchImage = async () => {
        return await api.get(`/image-data/${params.id}/articles`).then((response) => response.data.data.imageData);
    };
    const { isLoading, error, data } = useQuery<ImageDataInterface>({
        queryKey: [`imageData_articleId:${article.id}`],
        queryFn: fetchImage,
    });

    useEffect(() => {
        api.get("/members/me")
            .then((response) => setMember(response.data.data.memberDTO))
            .catch((err) => {
                console.log(err);
            });
    }, []);

    const fetchArticle = () => {
        api
            .get(`/articles/${params.id}`)
            .then((response) => setArticle(response.data.data.articleDTO))
            .catch((err) => {
                console.log(err);
            }),
            [];
    };

    const fetchReviews = () => {
        api
            .get(`/reviews/${params.id}/articles`)
            .then((response) => setReviews(response.data.data.reviews))
            .catch((err) => {
                console.log(err);
            }),
            [];
    };

    useEffect(() => {
        fetchArticle();
        fetchReviews();
    }, []);

    // YY:MM:DD 형식으로 날짜를 표시하기 위한 함수
    const formatDate = (dateString: string): string => {
        const date = new Date(dateString);
        const year = date.getFullYear().toString().slice(-2);
        const month = ("0" + (date.getMonth() + 1)).slice(-2);
        const day = ("0" + date.getDate()).slice(-2);
        return `${year}-${month}-${day}`;
    };
    const deleteArticle = async (id: number) => {
        await api.delete(`/articles/${id}`);
        router.push(`/${article.brand}/articles`);
    };
    const queryClient = useQueryClient();
    const mutation = useMutation({
        mutationFn: deleteArticle,
        onSuccess: () => {
            // Invalidate and refetch
            queryClient.invalidateQueries({ queryKey: ["articleDTO"] });
        },
    });
    return (
        <section className="text-gray-600 body-font overflow-hidden">
            <div className="container px-5 py-24 mx-auto">
                <div className="lg:w-4/5 mx-auto flex flex-wrap mb-10">
                    <div className="lg:w-1/2 w-full lg:pr-10 lg:py-6 mb-6 lg:mb-0">
                        <h2 className="text-sm title-font text-gray-500 tracking-widest">{article.brand}</h2>
                        <h1 className="text-gray-900 text-3xl title-font font-medium mb-4">{article.subject}</h1>
                        <div className="flex mb-4">
                            <a className={`flex-grow text-black-500 border-b-2 border-${color}-500 py-2 text-lg px-1`}>
                                Description
                            </a>
                            <a className={`flex-grow border-b-2 border-${color}-500 py-2 text-lg px-1`}>
                                📆 {formatDate(article.createdDate)}
                            </a>
                            {/* 멤버 추가시 주석 해제 */}
                            <a className="flex-grow border-b-2 border-green-500  py-2 text-lg px-1">
                                👩‍🍳 {article.author.username}
                            </a>
                        </div>
                        <p className="leading-relaxed mb-4">{article.content}</p>
                        {/* 반복시키기 */}
                        {types.map((type) => (
                            <div key={type}>
                                <IngredientTypeBox article={article} ingredientType={type} />
                            </div>
                        ))}
                        <div className="flex mt-2">
                            <span className="title-font font-medium text-2xl text-gray-900">✅</span>
                            {member?.id === article.author.id && (
                                <button
                                    className={`flex ml-auto text-white bg-blue-500 border-0 py-2 px-6 focus:outline-none hover:bg-blue-600 rounded`}
                                >
                                    <Link href={`/${article.brand}/${article.id}/patch`}>수정하기</Link>
                                </button>
                            )}
                        </div>
                        <div className="mt-2">
                            {member?.id === article.author.id && (
                                <button
                                    className={`flex ml-auto text-white bg-red-500 border-0 py-2 px-6 focus:outline-none hover:bg-red-600 rounded`}
                                    onClick={() => mutation.mutate(article.id)}
                                >
                                    삭제하기
                                </button>
                            )}
                        </div>
                    </div>
                    {/* 추후 이미지 추가 시 주석 */}
                    {/* <img
                        alt="article"
                        className="lg:w-1/2 w-full lg:h-auto h-64 object-cover object-center rounded"
                        src={`${data?.uploadPath}`}
                    /> */}

                    <Image src={`/${data?.uploadPath}`} width={400} height={400} alt="article_image" />
                </div>
                {reviews.map((review) => (
                    <div key={review.id} className="lg:w-4/5 w-full mx-auto border border-gray-300 mt-15 mb-10">
                        <ReviewBox review={review} formatDate={formatDate} fetchReviews={fetchReviews} />
                    </div>
                ))}
                {/* 비로그인 시 안보이게 만들기 */}
                <div className="lg:w-4/5 w-full mx-auto border border-gray-300 mt-15 mb-10">
                    <ReviewForm formColor={color} article={article} fetchReviews={fetchReviews} />
                </div>
            </div>
        </section>
    );
};

export default Detail;
