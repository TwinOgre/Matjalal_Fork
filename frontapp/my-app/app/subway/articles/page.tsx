import SubwayArticleBox from "@/app/components/SubwayArticleBox";
export default function SubwayArticles() {
    return (
        <>
            <section className="text-gray-600 body-font">
                <div className="container px-5 py-24 mx-auto">
                    <div className="flex flex-wrap w-full mb-20 flex-col items-center text-center">
                        <h1 className="sm:text-3xl text-2xl font-medium title-font mb-2 text-gray-900">
                            Matjalal Subway
                        </h1>
                        <p className="lg:w-1/2 w-full leading-relaxed text-gray-500">
                            서브웨이 꿀조합을 찾아봐요
                        </p>
                    </div>
                    
                    <div className="flex flex-wrap -m-4">
                        <div className="xl:w-1/3 md:w-1/2 p-4">
                            <div className="border border-gray-200 p-6 rounded-lg">
                                <div className="w-10 h-10 inline-flex items-center justify-center rounded-full bg-green-100 text-green-500 mb-4">
                                    <svg
                                        fill="none"
                                        stroke="currentColor"
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth={2}
                                        className="w-6 h-6"
                                        viewBox="0 0 24 24"
                                    >
                                        <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
                                    </svg>
                                </div>
                                <h2 className="text-lg text-gray-900 font-medium title-font mb-2">
                                    Shooting Stars
                                </h2>
                                <p className="leading-relaxed text-base">
                                    Fingerstache flexitarian street art 8-bit waist co, subway tile poke
                                    farm.
                                </p>
                            </div>
                        </div>
                        <SubwayArticleBox />
                    </div>
                    <button className="flex mx-auto mt-16 text-white bg-green-500 border-0 py-2 px-8 focus:outline-none hover:bg-green-600 rounded text-lg">
                        Button
                    </button>
                </div>
            </section>

        </>
    );
}