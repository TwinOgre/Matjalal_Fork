"use client";

import Link from "next/link";
import { useEffect, useRef, useState } from "react";

export default function HamburgerMenu() {
    const [sidebarOpen, setSidebarOpen] = useState(false);
    const sidebarRef = useRef(null);

    const toggleSidebar = () => {
        setSidebarOpen(!sidebarOpen);
    };

    useEffect(() => {
        const handleClick = (event: React.MouseEvent) => {
            if (sidebarRef.current && sidebarRef.current.contains(event.target)) {
                return;
            }
            setSidebarOpen(false);
        };

        document.documentElement.addEventListener("click", handleClick);
        document.body.addEventListener("click", handleClick);

        return () => {
            document.documentElement.removeEventListener("click", handleClick);
            document.body.removeEventListener("click", handleClick);
        };
    }, []);

    return (
        <div className="flex">
            <button onClick={toggleSidebar}>
                {sidebarOpen ? (
                    <img className="size-10" src="/close-icon.svg" alt="Close Icon Image" />
                ) : (
                    <img className="size-10" src="/menu-icon.svg" alt="Menu Icon Image" />
                )}
            </button>
            <div
                ref={sidebarRef}
                className={`fixed lg:w-64 bg-gray-800 ${
                    sidebarOpen ? "left-0" : "-left-64"
                } mt-12 transition-all opacity-100 duration-300 ease-in-out`}
                style={{ height: "100vh" }}
            >
                <div className="flex flex-col">
                    <div className="flex justify-center">
                        <Link
                            className="flex hover:bg-sky-500 rounded-lg text-white mt-4 py-1 w-3/4"
                            href="/home/index"
                        >
                            <img src="/home-icon.svg" alt="home-icon" />
                            <span>홈</span>
                        </Link>
                    </div>
                    <div className="flex justify-center">
                        <Link
                            className="flex hover:bg-sky-500 rounded-lg text-white mt-4 py-1 w-3/4"
                            href="/subway/articles"
                        >
                            <img src="/fastfood-icon.svg" alt="home-icon" />
                            <span>서브웨이 게시판</span>
                        </Link>
                    </div>
                    <div className="flex justify-center">
                        <Link
                            className="flex hover:bg-sky-500 rounded-lg text-white mt-4 py-1 w-3/4"
                            href="/gongcha/articles"
                        >
                            <img src="/tea-icon.svg" alt="home-icon" />
                            <span>공차 메뉴 게시판</span>
                        </Link>
                    </div>
                    <div className="flex justify-center">
                        <Link className="flex hover:bg-sky-500 rounded-lg text-white mt-4 py-1 w-3/4" href="#">
                            <img src="/dice-icon.svg" alt="home-icon" />
                            <span>랜덤 메뉴 추천</span>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}
