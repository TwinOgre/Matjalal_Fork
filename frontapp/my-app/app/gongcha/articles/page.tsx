'use client'
import ArticleBox from "@/app/components/ArticleBox";
import { MemberInterface } from "@/app/interface/user/MemberInterfaces";
import api from "@/app/utils/api";
import Link from "next/link"
import { useEffect, useState } from "react";
export default function GongchaArticles() {
    const [member, setMember] = useState<MemberInterface>();

    useEffect(() => {
        api
            .get("/members/me")
            .then((response) => setMember(response.data.data.memberDTO))
            .catch((err) => {

                console.log(err);
            });

    }, []);
    return (
        <>
            <section className="text-gray-600 body-font">
                <div className="container px-5 py-24 mx-auto">
                    <div className="flex flex-wrap w-full mb-20 flex-col items-center text-center">
                        <h1 className="sm:text-3xl text-2xl font-medium title-font mb-2 text-gray-900">
                            🍵 Matjalal GongCha 🧉
                        </h1>
                        <p className="lg:w-1/2 w-full leading-relaxed text-gray-500">
                            공차 꿀조합을 찾아봐요
                        </p>
                    </div>
                    <div className="flex flex-wrap -m-4">
                        <ArticleBox brand="gongcha" />
                    </div>
                    {member && (
                        <button className="flex mx-auto mt-16 text-white bg-red-500 border-0 py-2 px-8 focus:outline-none hover:bg-red-600 rounded text-lg">
                            <Link href="http://localhost:3000/gongcha/forms">나만의 레시피 등록하기</Link>
                        </button>
                    )}
                </div>
            </section>
        </>
    );
}