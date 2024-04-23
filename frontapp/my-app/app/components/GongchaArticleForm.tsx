"use client";

// import { useParams, useRouter } from "next/navigation";
import { useState, useEffect, ChangeEvent } from "react";
import IngredientCheckBox from "./IngredientCheckBox";
import { useRouter } from "next/navigation";
import api from "../utils/api";
import axios from "axios";
import { MemberInterface } from "../interface/member/MemberInterfaces";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { IngredientInterface } from "../interface/ingredient/IngredientInterfaces";

type ImageState = File | null;

export default function GongchaArticleForm() {
    const router = useRouter();
    const [member, setMember] = useState<MemberInterface>();
    const [selectedIngredients, setSelectedIngredients] = useState<IngredientInterface[]>([]);
    const [article, setArticle] = useState({ subject: "", content: "" });
    const [image, setImage] = useState<ImageState>(null);

    useEffect(() => {
        api.get("/members/me")
            .then((response) => setMember(response.data.data.memberDTO))
            .catch((err) => {
                console.log(err);
            });
    }, []);
    const queryClient = useQueryClient();

    const getMember = async () => {
        return await api.get("/members/me").then((res) => res.data.data);
    };

    const { isLoading, error, data } = useQuery({
        queryKey: ["memberKey"],
        queryFn: getMember,
    });

    const onIngredientChange = (checkedIngredients: IngredientInterface[]) => {
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
    const handleImageChange = (e: ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0]; // 선택된 이미지 파일 가져오기

        if (file) {
            // 이미지 파일 처리 (예: 상태에 저장)
            setImage(file);
        }
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const formData = new FormData(); // FormData 객체 생성

            // 폼 데이터에 필드 추가

            await api.post("/articles", {
                subject: article.subject,
                content: article.content,
                author: member,
                ingredients: selectedIngredients,
                brand: "gongcha",
            });
            formData.append("subject", article.subject);
            formData.append("content", article.content);
            if (image) {
                formData.append("image", image);
            }
            console.log("Gongcha Article created successfully!");
            await fetch("http://localhost:8090/api/v1/image-data/articles", {
                method: "POST",
                body: formData,
            });
            router.push("/gongcha/articles");

            // 추가적인 로직이 필요한 경우 여기에 작성
        } catch (error) {
            console.error("An error occurred while creating the Gongcha article:", error);
            // 에러 처리 로직을 추가할 수 있습니다. 예를 들어, 사용자에게 오류 메시지를 표시하거나 다시 시도할 수 있도록 유도할 수 있습니다.
        }
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setArticle({ ...article, [name]: value });
        console.log({ ...article, [name]: value });
    };

    return (
        <>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <div className="flex flex-wrap lg:w-2/3 w-full  flex-col mx-auto px-8 sm:space-x-4  space-y-4 sm:px-0 ">
                    {/* 재료 체크박스 */}
                    <div className="flex space-x-4 pl-4">
                        <IngredientCheckBox
                            onIngredientChange={onIngredientChange}
                            onIngredientRemove={onIngredientRemove}
                            ingredientType="GongChaMenu"
                            maxChecked={1}
                        />
                        <IngredientCheckBox
                            onIngredientChange={onIngredientChange}
                            onIngredientRemove={onIngredientRemove}
                            ingredientType="ice"
                            maxChecked={1}
                        />
                        <IngredientCheckBox
                            onIngredientChange={onIngredientChange}
                            onIngredientRemove={onIngredientRemove}
                            ingredientType="sweet"
                            maxChecked={1}
                        />
                        <IngredientCheckBox
                            onIngredientChange={onIngredientChange}
                            onIngredientRemove={onIngredientRemove}
                            ingredientType="gongChaTopping"
                            maxChecked={3}
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
                            onChange={handleChange}
                        />
                    </div>
                    <label htmlFor="image" className="leading-7 text-sm text-gray-600">
                        이미지
                    </label>
                    <input
                        type="file"
                        id="image"
                        name="image"
                        accept="image/*" // 이미지 파일만 허용하도록 설정
                        className="w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-green-500 focus:bg-transparent focus:ring-2 focus:ring-green-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out"
                        onChange={handleImageChange} // 이미지가 변경될 때 호출되는 핸들러 함수
                    />
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
