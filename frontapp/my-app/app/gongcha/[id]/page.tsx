"use client";
import Detail from "@/app/components/Detail";
import { useEffect, useState } from "react";

export default function GongchaArticle() {
    const [typeArray, setTypes] = useState<string[]>([]);
    useEffect(() => {
        setTypes(["GongChaMenu", "ice", "sweet", "gongChaTopping"]);
    }, []);
    return (
        <>
            <section className="text-gray-600 body-font">
                <Detail color="red" types={typeArray} />
            </section>
        </>
    );
}
