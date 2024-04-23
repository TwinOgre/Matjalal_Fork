"use client";
import api from "../utils/api";
import { useState, useEffect } from "react";
import IngredientCheckBox from "./IngredientCheckBox";
import { useParams, useRouter } from "next/navigation";
import { MemberInterface } from "../interface/member/MemberInterfaces";
import { IngredientInterface } from "../interface/ingredient/IngredientInterfaces";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { ArticleInterface } from "../interface/article/ArticleInterfaces";
function SubwayArticleModifyForm() {
    const params = useParams();
    const fetchArticle = async () => {
        return await api.get(`/articles/${params.id}`).then((response) => response.data.data.articleDTO);
    };
    const { isLoading, error, data } = useQuery<ArticleInterface>({
        queryKey: ["articleDTO", params.id],
        queryFn: fetchArticle,
    });
    const router = useRouter();
    const [member, setMember] = useState<MemberInterface>();
    const [selectedIngredients, setSelectedIngredients] = useState<IngredientInterface[]>([]);

    const [postArticle, setPostArticle] = useState({ subject: ``, content: `` });
    useEffect(() => {
        if (data) {
            setPostArticle({ subject: data.subject, content: data.content });
        }
    }, [data]);

    useEffect(() => {
        api.get("/members/me")
            .then((response) => setMember(response.data.data.memberDTO))
            .catch((err) => {
                console.log(err);
            });
    }, []);

    const onIngredientChange = (checkedIngredients: IngredientInterface[]) => {
        console.log(selectedIngredients);
        const isDuplicated = (ingredient: IngredientInterface) => {
            return selectedIngredients.some((item) => item === ingredient);
        };

        // 중복된 재료가 없는 새로운 재료 배열
        const newIngredients = checkedIngredients.filter((ingredient) => !isDuplicated(ingredient));

        // 새로운 재료 배열을 기존 선택된 재료 배열에 추가
        setSelectedIngredients((prevIngredients) => [...prevIngredients, ...newIngredients]);
        // Remove previously selected ingredients of the same type
    };

    const onIngredientRemove = (removedIngredient: IngredientInterface) => {
        // 선택된 재료 배열에서 제거할 재료를 제외하고 새로운 배열 생성
        const updatedIngredients = selectedIngredients.filter((ingredient) => ingredient !== removedIngredient);
        setSelectedIngredients(updatedIngredients);
    };

    const handleSubmit = async (e: any) => {
        e.preventDefault();

        try {
            // api.patch 호출 및 완료 대기
            await api.patch(`/articles/${params.id}`, {
                subject: postArticle.subject,
                content: postArticle.content,
                ingredients: selectedIngredients,
            });
            // api.patch가 완료된 후에 실행될 코드
            console.log("Article updated successfully!");
            router.push(`/subway/${params.id}`); // 원하는 경로로 이동
        } catch (error) {
            console.error("An error occurred while updating the article:", error);
            // 오류 처리 로직 추가 가능
        }
    };
    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: handleSubmit,
        onSuccess: () => {
            // Invalidate and refetch
            queryClient.invalidateQueries({ queryKey: ["articleDTO"] });
        },
    });

    const handleChange = (e: any) => {
        const { name, value } = e.target;
        // const name: any = e.target.name;
        // const value = e.target.value;
        setPostArticle({ ...postArticle, [name]: value });
        console.log({ ...postArticle, [name]: value });
    };

    if (data) {
        return (
            <>
                <form onSubmit={mutation.mutate}>
                    <div className="flex flex-wrap lg:w-2/3 w-full  flex-col mx-auto px-8 sm:space-x-4  space-y-4 sm:px-0 ">
                        {/* 재료 체크박스 */}
                        <div className="flex space-x-4 pl-4">
                            <IngredientCheckBox
                                onIngredientChange={onIngredientChange}
                                onIngredientRemove={onIngredientRemove}
                                ingredientType="subwayMenu"
                                maxChecked={1}
                                // defaultCheckedIngredients={data.ingredients}
                            />
                            <IngredientCheckBox
                                onIngredientChange={onIngredientChange}
                                onIngredientRemove={onIngredientRemove}
                                ingredientType="bread"
                                maxChecked={1}
                                // defaultCheckedIngredients={data.ingredients}
                            />
                            <IngredientCheckBox
                                onIngredientChange={onIngredientChange}
                                onIngredientRemove={onIngredientRemove}
                                ingredientType="cheese"
                                maxChecked={1}
                                // defaultCheckedIngredients={data.ingredients}
                            />
                            <IngredientCheckBox
                                onIngredientChange={onIngredientChange}
                                onIngredientRemove={onIngredientRemove}
                                ingredientType="vegetable"
                                maxChecked={8}
                                // defaultCheckedIngredients={data.ingredients}
                            />
                            <IngredientCheckBox
                                onIngredientChange={onIngredientChange}
                                onIngredientRemove={onIngredientRemove}
                                ingredientType="sauce"
                                maxChecked={3}
                                // defaultCheckedIngredients={data.ingredients}
                            />
                        </div>
                        {/* 제목 */}
                        <div className="relative flex-grow w-full">
                            <label htmlFor="subject" className="leading-7 text-sm text-gray-600">
                                제목
                            </label>
                            <input
                                type="text"
                                id="subject"
                                name="subject"
                                className="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-green-500 focus:bg-transparent focus:ring-2 focus:ring-green-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                                defaultValue={data.subject}
                                onChange={handleChange}
                            />
                        </div>
                        {/* 내용 */}
                        <div className="relative flex-grow w-full">
                            <label htmlFor="content" className="leading-7 text-sm text-gray-600">
                                내용
                            </label>
                            <textarea
                                id="content"
                                name="content"
                                className="w-full h-40 bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-green-500 focus:bg-transparent focus:ring-2 focus:ring-green-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                                defaultValue={data.content}
                                onChange={handleChange}
                            />
                        </div>
                        <button
                            type="submit"
                            className="text-white  bg-green-500 border-0 py-2 px-8 focus:outline-none hover:bg-green-600 rounded text-lg"
                        >
                            create
                        </button>
                    </div>
                </form>
            </>
        );
    }
}

export default SubwayArticleModifyForm;
