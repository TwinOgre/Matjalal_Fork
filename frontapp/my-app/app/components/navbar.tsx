"use client";

import Link from "next/link";
import HamburgerMenu from "./HamburgerMenu";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import api from "../utils/api";
import { useRouter } from "next/navigation";

export default function Navbar() {
    const queryClient = useQueryClient();
    const router = useRouter();

    const getMember = async () => {
        return await api.get("/members/me").then((res) => res.data.data.memberDTO);
    };

    const { isLoading, error, data } = useQuery({
        queryKey: ["memberKey"],
        queryFn: getMember,
    });

    if (error) {
        queryClient.setQueryData(["memberKey"], null);
    }

    const handleLogout = async () => {
        await api.post("/members/logout").then((res) => {
            if (res.data.msg === "로그아웃 성공") {
                alert("로그아웃 되었습니다.");
                queryClient.setQueryData(["memberKey"], null);
                router.replace("/home/index");
            } else {
                alert("로그아웃 실패");
            }
        });
    };

    return (
        <>
            <div className="sticky top-5 flex justify-between mx-5 my-5">
                <HamburgerMenu />
                {/* <MemberStateCheck /> */}
                {data === null ? (
                    <Link href="/member/login">
                        <img className="size-10" src="/lock-icon.svg" alt="Locker Icon Image" />
                    </Link>
                ) : (
                    <div className="flex">
                        <Link href={"/about/me"}>
                            <img className="size-10" src="/user-icon.svg" alt="User Icon Image" />
                        </Link>
                        <img
                            onClick={handleLogout}
                            className="size-10 hover:cursor-pointer"
                            src="/logout-icon.svg"
                            alt="Logout Icon Image"
                        />
                    </div>
                )}
            </div>
        </>
    );
}
