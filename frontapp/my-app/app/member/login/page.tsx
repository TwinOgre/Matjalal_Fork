'use client'
import api from "@/app/utils/api";
import { useState } from "react";

export default function Login() {
    const [member, setMember] = useState({username:'', password:''})
    
    
    const handleSubmit = async (e: any) => {
        e.preventDefault();
        const response = await api.post("/members/login" , {
            username: member.username,
            password: member.password
          });
    }
    const handleChange = (e: any) => {
        const { name, value } = e.target;
        // const name: any = e.target.name;
        // const value = e.target.value;
        setMember({ ...member, [name]: value });
        console.log({ ...member, [name]: value })
      }
    
    return (
      <>
       <form onSubmit={handleSubmit}>
        <span>아이디:</span>
        <input type="text" name="username" onChange={handleChange} className="inputer"/>
        <br/>
        <span>비밀번호</span>
        <input type="password" name="password" onChange={handleChange} className="inputer"/>
        <button type="submit">로그인</button>
       </form>
      </>
    );
  }