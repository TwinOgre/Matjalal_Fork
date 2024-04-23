"use client";

import Link from "next/link";
import api from "@/app/utils/api";
import { useRouter } from "next/navigation";

export default function HomeIndex() {
    const router = useRouter();

    const handleRandomMenu = async (brand: string) => {
        await api.get(`/articles/${brand}/random`).then((res) => {
            router.push(`/${brand}/${res.data.data}`);
        });
    };
    return (
        <>
            <div className="text-6xl flex justify-center mt-24">
                <img src="/main-title.svg" alt="MainTitle Image" />
            </div>
            <div className="flex justify-around mx-80 mt-20">
                <div className="grid grid-col-1">
                    <img src="/subway-title.svg" className="w-full h-40" alt="Subway Image" />
                    <Link
                        href="/subway/articles"
                        className="flex justify-center w-60 mt-5 py-2 px-3 bg-green-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-yellow-500 text-sm font-semibold rounded-md shadow"
                    >
                        서브웨이 메뉴 추천
                    </Link>
                    <button
                        onClick={() => handleRandomMenu("subway")}
                        className="w-60 mt-5 py-2 px-3 bg-green-400 hover:bg-indigo-700 disabled:bg-indigo-300 text-yellow-100 text-sm font-semibold rounded-md shadow"
                    >
                        서브웨이 랜덤 글 바로가기
                    </button>
                </div>
                <div className="grid grid-col-1">
                    <img src="/gongcha-title.svg" className="w-full h-40" alt="Gongcha Image" />
                    <Link
                        href="/gongcha/articles"
                        className="flex justify-center w-60 mt-5 py-2 px-3 bg-rose-800 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow"
                    >
                        공차 메뉴 추천
                    </Link>
                    <button
                        onClick={() => handleRandomMenu("gongcha")}
                        className="w-60 mt-5 py-2 px-3 bg-rose-400 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow"
                    >
                        공차 랜덤 글 바로가기
                    </button>
                </div>
            </div>
        </>
    );
}
