'use client'

import Link from "next/link"

export default function HomeIndex() {

    return (
        <>
            <div className="flex justify-between mx-5 my-5">
                <img className="size-10" src="/menu-icon.svg" alt="Menu Icon Image" />
                <Link href="/member/login"><img className="size-10" src="/lock-icon.svg" alt="Locker Icon Image" /></Link>
            </div>
            <div className="text-6xl flex justify-center mt-24">
                <img src="/main-title.svg" alt="MainTitle Image" />
            </div>
            <div className="flex justify-around">
                <img src="/subway-title.svg" alt="Subway Image" />
                <div className="w-500 h-0 border-t-2 border-black transform -rotate-45"></div>
                <img src="/gongcha-title.svg" alt="Gongcha Image" />
                <div className="w-500 h-0 border-t-2 border-black transform -rotate-45"></div>
                <img src="/randombox-title.svg" alt="Gongcha Image" />
            </div>
            <br></br>
        </>
    );
}