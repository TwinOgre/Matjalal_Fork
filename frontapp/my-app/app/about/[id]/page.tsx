"use client";
import { useState, useEffect } from "react";
import api from "../../utils/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useParams } from "next/navigation";

type userInterface = {
    id: number;
    createdDate: string;
    modifiedDate: string;
    username: string;
    email: string;
};
export default function AboutUser() {
    const param = useParams();

    const getUserInfo = async () => {
        return await api.get(`/members/${param.id}`);
    };

    const { data, isLoading, isError } = useQuery({
        queryKey: ["findUserInfo"],
        queryFn: getUserInfo,
    });

    if (isLoading) <div>... Loding</div>;

    if (isError) console.log(data?.data);

    if (data) {
        const resData = data.data.data;

        //date 처리 로직 시작
        const dateString = resData.createdDate;
        const date = new Date(dateString);

        const year = date.getFullYear();
        const month = (1 + date.getMonth()).toString().padStart(2, "0");
        const day = date.getDate().toString().padStart(2, "0");

        const formattedDate = `${year}년 ${month}월 ${day}일`;
        ////date 처리 로직 끝

        return (
            <>
                <div className="grid grid-rows-1 gap-4 content-center mx-96">
                    <div className="flex justify-center border-b-2 border-black">
                        <h1 className="text-gray-900 text-3xl font-medium mb-3">유저 정보 페이지</h1>
                        <h2 className="content-center title-font text-gray-500 ml-2">user-infomation-page</h2>
                    </div>
                    <div className="grid grid-col-1 gap-10 content-center mt-10">
                        <div className="border-b-2  text-lg">🙂 유저명 : {resData.username}</div>
                        <div className="border-b-2  text-lg">📧 이메일 : {resData.email}</div>
                        <div className="border-b-2  text-lg">📆 가입일 : {formattedDate}</div>
                    </div>
                </div>
            </>
        );
    }
}
