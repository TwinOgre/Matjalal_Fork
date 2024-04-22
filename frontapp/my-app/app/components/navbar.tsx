"use client";

import Link from "next/link";
import HamburgerMenu from "./HamburgerMenu";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import api from "../utils/api";

export default function Navbar() {
    const queryClient = useQueryClient();

    const getMember = async () => {
        return await api.get("/members/me").then((res) => res.data.data);
    };

    const { isLoading, error, data } = useQuery({
        queryKey: ["memberKey"],
        queryFn: getMember,
    });

    if (error) {
        queryClient.setQueryData(["memberKey"], null);
    }

    if (isLoading) <>Loading...</>;

    const handleLogout = async () => {
        await api.post("/members/logout");
        queryClient.setQueryData(["memberKey"], null);
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
                    <div>
                        <img className="size-10" src="/user-icon.svg" alt="User Icon Image" />
                        <img
                            onClick={handleLogout}
                            className="size-10"
                            src="/logout-icon.svg"
                            alt="Logout Icon Image"
                        />
                    </div>
                )}
            </div>
        </>
    );
}
