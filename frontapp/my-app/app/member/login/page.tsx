"use client";
import LoginForm from "@/app/components/loginForm";
import api from "@/app/utils/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";

export default function Login() {
    return (
        <>
            <LoginForm />
        </>
    );
}
