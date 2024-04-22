'use client';
import { useParams } from 'next/navigation';
import { useState, useEffect, FC } from 'react';
import api from '@/app/utils/api';
import IngredientTypeBox from './IngredientTypeBox';
import ReviewBox from './ReviewBox';
import ReviewForm from './ReviewForm';
import { ArticleInterface } from '../interface/article/ArticleInterfaces';
import { ReviewInterface } from '../interface/review/ReviewInterfaces';

interface DetailProps {
    color: string;
    types: string[];
}

const Detail: React.FC<DetailProps> = ({ color, types }) => {
    const [article, setArticle] = useState<ArticleInterface>({
        id: 0,
        createdDate: '',
        modifiedDate: '',
        subject: '',
        content: '',
        brand: '',
        author: {
            id: '',
            createdDate: '',
            modifiedDate: '',
            username: '',
            email: '',
        },
        ingredients: [],
    });
    const params = useParams();
    const [reviews, setReviews] = useState<ReviewInterface[]>([]);

    const fetchArticle = () => {
        api.get(`/articles/${params.id}`)
            .then((response) => setArticle(response.data.data.articleDTO))
            .catch((err) => {
                console.log(err);
            });
    };

    const fetchReviews = () => {
        api.get(`/reviews`)
            .then((response) => setReviews(response.data.data.reviews))
            .catch((err) => {
                console.log(err);
            });
    };

    useEffect(() => {
        fetchArticle();
        fetchReviews();
    }, []);

    // YY:MM:DD 형식으로 날짜를 표시하기 위한 함수
    const formatDate = (dateString: string): string => {
        const date = new Date(dateString);
        const year = date.getFullYear().toString().slice(-2);
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);
        return `${year}-${month}-${day}`;
    };
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
                            {/* <a className="flex-grow border-b-2 border-green-500  py-2 text-lg px-1">
                                👩‍🍳 {article.author.username}
                            </a> */}
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
                            <button
                                className={`flex ml-auto text-white bg-${color}-500 border-0 py-2 px-6 focus:outline-none hover:bg-${color}-600 rounded`}
                            >
                                Button
                            </button>
                            <button
                                className={`rounded-full w-10 h-10 bg-gray-200 p-0 border-0 inline-flex items-center justify-center text-${color}-500 ml-4`}
                            >
                                <svg
                                    fill="currentColor"
                                    strokeLinecap="round"
                                    strokeLinejoin="round"
                                    strokeWidth={2}
                                    className="w-5 h-5"
                                    viewBox="0 0 24 24"
                                >
                                    <path d="M20.84 4.61a5.5 5.5 0 00-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 00-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 000-7.78z" />
                                </svg>
                            </button>
                        </div>
                    </div>
                    {/* 추후 이미지 추가 시 주석 */}
                    <img
                        alt="article"
                        className="lg:w-1/2 w-full lg:h-auto h-64 object-cover object-center rounded"
                        src="https://dummyimage.com/400x400"
                    />
                </div>
                {reviews.map((review) => (
                    <div key={review.id} className="lg:w-4/5 w-full mx-auto border border-gray-300 mt-15 mb-10">
                        <ReviewBox review={review} formatDate={formatDate} />
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
