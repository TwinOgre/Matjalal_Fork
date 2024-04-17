'use client'
import api from "@/app/utils/api";
// import { useParams, useRouter } from "next/navigation";
import { useState, useEffect } from "react";
import IngredientCheckBox from "./IngredientCheckBox";
import { useRouter } from "next/navigation";
type memberInterface = {
    createdDate: string,
    modifiedDate: string,
    username: string,
    email: string
}
interface ingredientsInterface {
    id: string,
    name: string,
    type: string
}

export default function GongchaArticleForm() {
    const router = useRouter();
    const [member, setMember] = useState<memberInterface>();
    const [selectedIngredients, setSelectedIngredients] = useState<ingredientsInterface[]>([]);
    const [article, setArticle] = useState({ subject: '', content: '' });

    useEffect(() => {
        api.get("/members/me")
            .then(response => setMember(response.data.data.memberDTO))
            .catch(err => {
                console.log(err)
            })
    }, [])



    const onIngredientChange = (checkedIngredients: ingredientsInterface[]) => {
        const isDuplicated = (ingredient: ingredientsInterface) => {
            return selectedIngredients.some(item => item === ingredient);
        };

        // 중복된 재료가 없는 새로운 재료 배열
        const newIngredients = checkedIngredients.filter(ingredient => !isDuplicated(ingredient));

        // 새로운 재료 배열을 기존 선택된 재료 배열에 추가
        setSelectedIngredients(prevIngredients => [...prevIngredients, ...newIngredients]);
        // Remove previously selected ingredients of the same type
    };

    const onIngredientRemove = (removedIngredient: ingredientsInterface) => {
        // 선택된 재료 배열에서 제거할 재료를 제외하고 새로운 배열 생성
        const updatedIngredients = selectedIngredients.filter(ingredient => ingredient !== removedIngredient);
        setSelectedIngredients(updatedIngredients);
    };

    const handleSubmit = async (e: any) => {
        e.preventDefault();

        try {
            await api.post("/articles", {
                subject: article.subject,
                content: article.content,
                author: member,
                ingredients: selectedIngredients,
                brand: "gongcha"
            });
            console.log("Gongcha Article created successfully!");
            router.push("/gongcha/articles")

            // 추가적인 로직이 필요한 경우 여기에 작성
        } catch (error) {
            console.error("An error occurred while creating the Gongcha article:", error);
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
                <div className="flex flex-wrap lg:w-2/3 w-full  flex-col mx-auto px-8 sm:space-x-4  space-y-4 sm:px-0 ">
                    {/* 재료 체크박스 */}
                    <div className="flex space-x-4 pl-4">
                        <IngredientCheckBox onIngredientChange={onIngredientChange} onIngredientRemove={onIngredientRemove} ingredientType="GongChaMenu" maxChecked={1} />
                        <IngredientCheckBox onIngredientChange={onIngredientChange} onIngredientRemove={onIngredientRemove} ingredientType="ice" maxChecked={1} />
                        <IngredientCheckBox onIngredientChange={onIngredientChange} onIngredientRemove={onIngredientRemove} ingredientType="sweet" maxChecked={1} />
                        <IngredientCheckBox onIngredientChange={onIngredientChange} onIngredientRemove={onIngredientRemove} ingredientType="gongChaTopping" maxChecked={3} />
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
                    <button type="submit" className="text-white  bg-green-500 border-0 py-2 px-8 focus:outline-none hover:bg-green-600 rounded text-lg">
                        create
                    </button>
                </div>
            </form>
        </>
    );
}