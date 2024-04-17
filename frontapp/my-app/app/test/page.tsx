'use client'

import { useState } from 'react';

export default function Test() {

    const [sidebarOpen, setSidebarOpen] = useState(false);

    return (
        <div className="flex">
            {/* 햄버거 메뉴 아이콘 */}
            <img className="size-10" onClick={() => setSidebarOpen(!sidebarOpen)} src="/menu-icon.svg" alt="Menu Icon Image" />

            {/* 사이드바 메뉴 */}
            <div
                className={`fixed lg:w-64 bg-gray-200 ${sidebarOpen ? 'left-0' : '-left-64'
                    } transition-all duration-300 ease-in-out`}
                style={{ height: '100vh' }}
            >
                {/* 사이드바 내용 */}
                <ul>
                    <li>
                        <a href="#">메뉴 항목 1</a>
                    </li>
                    <li>
                        <a href="#">메뉴 항목 2</a>
                    </li>
                    <li>
                        <a href="#">메뉴 항목 3</a>
                    </li>
                </ul>
            </div>

            {/* 메인 콘텐츠 */}
            <div className="flex-1">
                {/* 여기에 메인 콘텐츠가 옵니다 */}
            </div>
        </div>
    );
}