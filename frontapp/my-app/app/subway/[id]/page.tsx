"use client";
import Detail from "@/app/components/Detail";
import { useEffect, useState } from "react";

export default function SubwayArticle() {
    const [typeArray, setTypes] = useState<string[]>([]);
    useEffect(() => {
        setTypes(["subwayMenu", "bread", "cheese", "vegetable", "sauce"]);
    }, []);
    return (
        <>
            <section className="text-gray-600 body-font">
                <Detail color="green" types={typeArray} />
            </section>
        </>
    );
}
