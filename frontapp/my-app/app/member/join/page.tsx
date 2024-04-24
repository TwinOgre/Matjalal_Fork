'use client'

import { useState } from "react";
import api from "@/app/utils/api";
import { useRouter } from "next/navigation";

export default function Join() {
    const [member, setMember] = useState({ username: "", email: "", password: "" });
    const router = useRouter();

    const handleChange = (e: any) => {
        const { name, value } = e.target;
        // const name: any = e.target.name;
        // const value = e.target.value;
        setMember({ ...member, [name]: value });
        console.log({ ...member, [name]: value });
    };

    const login = async (e: any) => {
        e.preventDefault();

        return await api
            .post("/members/join", {
                username: member.username,
                email: member.email,
                password: member.password,
            })
            .then((res) => {
                console.log(res)
                if(res.data.resultCode==="F-1"){
                    alert(res.data.msg);
                } else if(res.data.resultCode==="F-2"){
                    alert(res.data.msg);
                }else{
                    alert(res.data.msg);
                    router.replace("/member/login");
                }
                
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <>
            <section className="flex justify-center m-40">
                <div className="grid grid-rows-2 grid-flow-col gap-1 items-center">
                    <div className="grid gap-1">
                        <div>
                            <span>아이디</span>
                            <input
                                type="text"
                                name="username"
                                onChange={handleChange}
                                className="flex mt-1 border rounded-md p-1.5 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                                placeholder="ID를 입력하세요"
                            />
                        </div>
                        <div>
                            <span>이메일</span>
                            <input
                                type="email"
                                name="email"
                                onChange={handleChange}
                                className="flex mt-1 border rounded-md p-1.5 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                                placeholder="Email을 입력하세요"
                            />
                        </div>
                        <div>
                            <span>비밀번호</span>
                            <input
                                type="password"
                                name="password"
                                onChange={handleChange}
                                className="flex mt-1 border rounded-md p-1.5 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                                placeholder="PASSWORD를 입력하세요"
                            />
                        </div>
                        <div>
                            <button
                                className="w-full py-2 px-3 mt-5 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow"
                                type="button"
                                onClick={login}
                            >
                                회원가입
                            </button>
                        </div>
                    </div>
                </div>
            </section>
        </>
    );
}
