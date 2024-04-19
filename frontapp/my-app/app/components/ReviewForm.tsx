"use client";
import { ChangeEvent, useEffect, useState } from "react";
import { ArticleInterface } from "../interface/article/ArticleInterfaces";
import { MemberInterface } from "../interface/user/MemberInterfaces";
import api from "../utils/api";
import { useRouter } from "next/navigation";

interface ReviewFormProps {
  formColor: string;
  article: ArticleInterface;
  fetchReviews: () => void;
}
const ReviewForm: React.FC<ReviewFormProps> = ({
  formColor,
  article,
  fetchReviews,
}) => {
  const router = useRouter();
  const [content, setContent] = useState<string>("");
  const [member, setMember] = useState<MemberInterface>();
  useEffect(() => {
    api
      .get("/members/me")
      .then((response) => setMember(response.data.data.memberDTO))
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await api.post("/reviews", {
        content: content,
        author: member,
        article: article,
      });
      console.log(response.data.msg);
      // 추가적인 로직이 필요한 경우 여기에 작성
      if (response.data.resultCode === "S-3") {
        fetchReviews();
        console.log("Review Fetched!");
      } else if (response.data.resultCode === "F-3M") {
        alert(response.data.msg);
        router.push(`/member/login`);
      }
    } catch (error) {
      console.error("An error occurred while creating the article:", error);
      // 에러 처리 로직을 추가 가능.
    }
  };
  const handleChange = (
    e: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { value } = e.target;
    setContent(value);
    console.log(value);
  };
  return (
    <section className="text-gray-600 body-font relative">
      <div className="container px-5 py-3 mx-auto">
        <div className="lg:w-1/1 md:w-3/3 mx-auto">
          {/* 아래 div부터 form 태그로 묶기 */}
          <form className="flex flex-wrap -m-2" onSubmit={handleSubmit}>
            <div className="p-2 w-full">
              <div className="relative">
                <label
                  htmlFor="message"
                  className="leading-7 text-lg text-gray-600"
                >
                  Review
                </label>
                <textarea
                  id="message"
                  name="message"
                  className={`w-full bg-gray-100 bg-opacity-50 rounded border border-gray-300 focus:border-${formColor}-500 focus:bg-white focus:ring-2 focus:ring-${formColor}-200 h-32 text-base outline-none text-gray-700 py-1 px-3 resize-none leading-6 transition-colors duration-200 ease-in-out`}
                  defaultValue={""}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div className="p-2 w-full">
              <button
                type="submit"
                className="flex mx-auto text-white bg-green-500 border-0 py-2 px-8 focus:outline-none hover:bg-green-600 rounded text-lg"
              >
                등록
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  );
};
export default ReviewForm;
