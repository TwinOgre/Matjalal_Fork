"use client";
import { useState, useEffect } from "react";
import api from "../../utils/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";

type memberInterface = {
    id: number;
    createdDate: string;
    modifiedDate: string;
    username: string;
    email: string;
};
export default function About() {
    const queryClient = useQueryClient();
    const member = queryClient.getQueryData<memberInterface>(["memberKey"]);

    // useEffect(() => {
    //     api.get("/members/me")
    //         .then((response) => setMember(response.data.data.memberDTO))
    //         .catch((err) => {
    //             console.log(err);
    //         });
    // }, []);

    console.log(member);

    return (
        <>
            <div className="grid grid-rows-1 gap-4 content-center mx-96">
                <div className="flex justify-center border-b-2 border-black">
                    <h1 className="text-gray-900 text-3xl font-medium mb-3">마이페이지</h1>
                    <h2 className="content-center title-font text-gray-500 ml-2">my-page</h2>
                </div>
                <div className="grid grid-col-1 gap-10 content-center mt-10">
                    <div className="border-b-2  text-lg">🙂 이름 : {member?.username}</div>
                    <div className="border-b-2  text-lg">📧이메일 : {member?.email}</div>
                    <div className="border-b-2  text-lg">📆 회원가입일 : {member?.createdDate}</div>
                </div>
            </div>
            {/* <div className="lg:w-4/5 mx-auto flex flex-wrap mb-10">
                <div className="lg:w-1/2 w-full lg:pr-10 lg:py-6 mb-6 lg:mb-0">
                    <div className="flex">
                        <h1 className="text-gray-900 text-3xl title-font font-medium mb-4">나의 정보</h1>
                        <h2 className="content-center text-sm title-font text-gray-500 tracking-widest ml-2">
                            my-page
                        </h2>
                    </div>
                    <div className="flex mb-4">
                        <div className="flex-grow text-black-500 border-b-2 border-black py-2 text-lg px-1">
                            Description
                        </div>
                        <div className="flex-grow border-b-2 border-black py-2 text-lg px-1">📆 가입일</div>
                        <div className="flex-grow border-b-2 border-black  py-2 text-lg px-1">🙂 유저 이름</div>
                    </div>
                    <p className="leading-relaxed mb-4">기타 내용</p>
                </div>
            </div> */}
            {/* <div>
                <h1>😎소개 페이지 입니다.</h1>
                아이디: {member?.id} | <br />
                이름: {member?.username} | <br />
                이메일: {member?.email} | <br />
                생성일: {member?.createdDate} |
            </div> */}
        </>
    );
}
