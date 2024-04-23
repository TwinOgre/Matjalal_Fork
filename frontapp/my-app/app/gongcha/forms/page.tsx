import GongchaArticleForm from "@/app/components/GongchaArticleForm";
export default function GongchaCreateForm() {
    return (
        <section className="text-gray-600 body-font">
            <div className="container px-5 py-24 mx-auto">
                <div className="flex flex-col text-center w-full mb-12">
                    <h1 className="sm:text-3xl text-2xl font-medium title-font mb-4 text-gray-900">
                        🍹 Gongcha 꿀조합 만들기 🍹
                    </h1>
                    <p className="lg:w-2/3 mx-auto leading-relaxed text-base">
                        원하는 음료, 당도, 얼음량, 토핑을 골라 나만의 꿀조합을 공유하세요!
                    </p>
                </div>
                <GongchaArticleForm />
            </div>
        </section>
    );
}
