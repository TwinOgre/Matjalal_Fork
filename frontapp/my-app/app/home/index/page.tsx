'use client'

import Link from "next/link"

export default function HomeIndex() {

    return (
        <>
            <div className="text-6xl flex justify-center mt-24">
                <img src="/main-title.svg" alt="MainTitle Image" />
            </div>
            <div className="flex justify-around mx-80">
                <Link href="/subway/articles" className="mt-20">
                    <img src="/subway-title.svg" className="w-56 h-40" alt="Subway Image" />
                    <button className="w-full mt-5 py-2 px-3 bg-green-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-yellow-500 text-sm font-semibold rounded-md shadow">서브웨이 메뉴 추천</button>
                </Link>
                <div className="p-0 h-96 text-12 border-black border-2 rounded-5 origin-center rotate-12"></div>
                <Link href="/gongcha/articles" className="mt-20">
                    <img src="/gongcha-title.svg" className="w-56 h-40" alt="Gongcha Image" />
                    <button className="w-full mt-5 py-2 px-3 bg-rose-800 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow">공차 메뉴 추천</button>
                </Link>
                <div className="p-0 h-35 text-12 border-black border-2 rounded-5 origin-center rotate-12"></div>
                <Link href="/subway/articles" className="mt-20">
                    <img src="/randombox-title.svg" className="w-56 h-40" alt="Gongcha Image" />
                    <button className="w-full mt-5 py-2 px-3 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow">무작위 메뉴 추천</button>
                </Link>
            </div>
            <br></br>
        </>
    );
}