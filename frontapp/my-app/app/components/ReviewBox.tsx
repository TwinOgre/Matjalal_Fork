"use client";
import { ChangeEvent, useEffect, useState } from "react";
import { ReviewInterface } from "../interface/review/ReviewInterfaces";
import { MemberInterface } from "../interface/member/MemberInterfaces";
import api from "../utils/api";
import { useMutation, useQueryClient } from "@tanstack/react-query";
interface ReviewBoxProps {
    review: ReviewInterface;
    formatDate: (dateString: string) => string;
    fetchReviews: () => void;
}

const ReviewBox: React.FC<ReviewBoxProps> = ({ review, formatDate, fetchReviews }) => {
    const [editMode, setEditMode] = useState<boolean>(false);
    const [editedContent, setEditedContent] = useState<string>(review.content);
    const [member, setMember] = useState<MemberInterface>();

    useEffect(() => {
        api.get("/members/me")
            .then((response) => setMember(response.data.data.memberDTO))
            .catch((err) => {
                console.log(err);
            });
    }, []);
    const handleEdit = () => {
        setEditMode(true);
    };

    const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
        setEditedContent(e.target.value);
        console.log(editedContent);
    };

    const handleSubmit = async (e: any) => {
        e.preventDefault();
        await api.patch(`/reviews/${review.id}`, {
            content: editedContent,
        });
        setEditMode(false);
        fetchReviews();
    };
    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: handleSubmit,
        onSuccess: () => {
            // Invalidate and refetch
            queryClient.invalidateQueries({ queryKey: ["reviewDTO"] });
        },
    });
    const deleteReview = async (id: number) => {
        await api.delete(`/reviews/${id}`);
        fetchReviews();
    };

    const mutation2 = useMutation({
        mutationFn: deleteReview,
        onSuccess: () => {
            // Invalidate and refetch
            queryClient.invalidateQueries({ queryKey: ["articleDTO"] });
        },
    });

    return (
        <section key={review.id} className="text-gray-600 body-font overflow-hidden mx-auto">
            <div className="container px-5 py-12 mx-auto">
                <div className="-my-8 divide-y-2 divide-gray-100">
                    <div className="py-8 flex flex-wrap md:flex-nowrap">
                        <div className="md:w-64 md:mb-0 mb-6 flex-shrink-0 flex flex-col">
                            <span className="font-semibold title-font text-gray-700">{review.author.username}</span>
                            <span className="mt-1 text-gray-500 text-sm">{formatDate(review.createdDate)}</span>
                        </div>
                        <div className="md:flex-grow">
                            {editMode ? (
                                <textarea
                                    className="w-full bg-gray-100 rounded border border-gray-400 focus:outline-none focus:border-indigo-500 text-base px-4 py-2 resize-none"
                                    value={editedContent}
                                    onChange={handleChange}
                                ></textarea>
                            ) : (
                                <p className="leading-relaxed">{review.content}</p>
                            )}
                            <div className="flex flex-wrap items-center justify-end">
                                {member?.id === review.author.id && !editMode && (
                                    <button
                                        onClick={handleEdit}
                                        className={`flex ml-auto text-white bg-blue-500 border-0 py-2 px-6 focus:outline-none hover:bg-blue-600 rounded`}
                                    >
                                        수정하기
                                    </button>
                                )}
                                {editMode && (
                                    <>
                                        <button
                                            onClick={mutation.mutate}
                                            className={`flex ml-auto text-white bg-blue-500 border-0 py-2 px-6 focus:outline-none hover:bg-blue-600 rounded`}
                                        >
                                            저장하기
                                        </button>
                                        <button
                                            onClick={() => setEditMode(false)}
                                            className={`flex ml-4 text-white bg-gray-500 border-0 py-2 px-6 focus:outline-none hover:bg-gray-600 rounded`}
                                        >
                                            취소
                                        </button>
                                    </>
                                )}
                                {member?.id === review.author.id && (
                                    <button
                                        onClick={() => mutation2.mutate(review.id)}
                                        className={`flex ml-4 text-white bg-red-500 border-0 py-2 px-6 focus:outline-none hover:bg-red-600 rounded`}
                                    >
                                        삭제하기
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default ReviewBox;
