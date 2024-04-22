import { useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import api from "../utils/api";
import { useRouter } from "next/navigation";

export default function LoginForm() {
  const [member, setMember] = useState({ username: '', password: '' })
  const queryClient = useQueryClient();
  const router = useRouter();

  const loginhandleSubmit = async (e: any) => {
    e.preventDefault();
    const response = await fetch("http://localhost:8090/api/v1/members/login", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(member),
    });
    if (response.ok) {
      alert("로그인 성공");
    } else {
      alert("로그인 실패");
    }
  };
  const handleChange = (e: any) => {
    const { name, value } = e.target;
    // const name: any = e.target.name;
    // const value = e.target.value;
    setMember({ ...member, [name]: value });
    console.log({ ...member, [name]: value })
  }

  const login = async (e: any) => {
    e.preventDefault()

    return await api
      .post("/members/login", {
        username: member.username,
        password: member.password
      })
      .then(res => {
          queryClient.setQueryData(['memberKey'], res.data.data.memberDto);
          router.replace("/");
      }
      )
      .catch((error)=>{
        console.log(error);
      })
  }


    return (
        <>
            <section className="flex justify-center m-40">
                <div className="grid grid-rows-2 grid-flow-col gap-1 items-center">
                    <div className="grid gap-2">
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
                            <span>비밀번호</span>
                            <input
                                type="password"
                                name="password"
                                onChange={handleChange}
                                className="flex mt-1 border rounded-md p-1.5 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                                placeholder="PASSWORD를 입력하세요"
                            />
                        </div>
                    </div>
                    <div className="grid gap-2">
                        <button
                            onClick={login}
                            className="w-full py-2 px-3 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow"
                            type="button"
                        >
                            로그인
                        </button>
                        <button
                            className="w-full py-2 px-3 bg-indigo-600 hover:bg-indigo-700 disabled:bg-indigo-300 text-white text-sm font-semibold rounded-md shadow"
                            type="button"
                        >
                            회원가입
                        </button>
                    </div>
                </div>
            </section>
        </>
    );
}
