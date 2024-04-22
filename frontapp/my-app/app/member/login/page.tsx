'use client'
import LoginForm from "@/app/components/loginForm";
import api from "@/app/utils/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";

export default function Login() {
  const queryClient = useQueryClient();

  const handleLogout = async () => {
    await api.post('/members/logout');
    queryClient.setQueryData(['memberKey'], null);
  }

  return (
    <>
      <button onClick={handleLogout}>로그아웃</button>
      <LoginForm />
    </>
  );
}