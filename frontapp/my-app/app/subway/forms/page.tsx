import SubwayArticleForm from "@/app/components/SubwayArticleForm";
export default function ArticleCreateForm() {
    return (
        <section className="text-gray-600 body-font">
            <div className="container px-5 py-24 mx-auto">
                <div className="flex flex-col text-center w-full mb-12">
                    <h1 className="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">
                        Subway 꿀조합 만들기
                    </h1>
                    <p className="lg:w-2/3 mx-auto leading-relaxed text-base">
                        원하는 메뉴, 빵, 치즈, 야채, 소스를 골라 나만의 꿀조합을 공유하세요!
                    </p>
                </div>
                <SubwayArticleForm />
            </div>
        </section>
    );
}
