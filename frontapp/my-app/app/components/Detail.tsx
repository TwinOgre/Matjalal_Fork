
const Detail = () => {
    return (
        <>
            <div className="container px-5 py-24 mx-auto">
                <div className="flex flex-wrap w-full mb-20 flex-col items-center text-center">
                    <h1 className="sm:text-3xl text-2xl font-medium title-font mb-2 text-gray-900">
                    ⚡ 제목 
                    </h1>
                    <p className="lg:w-1/2 w-full leading-relaxed text-gray-500">
                        작가 <span> | </span><span>생성일</span><span> | </span><span> 수정일 </span>
                    </p>
                </div>
                <div className="flex flex-wrap -m-4">
                    (재료컴포넌트_재료타입A : 재료1, 재료2 )
                </div>
                <button className="flex mx-auto mt-16 text-white bg-red-500 border-0 py-2 px-8 focus:outline-none hover:bg-red-600 rounded text-lg">
                    일단 버튼
                </button>
            </div>
        </>
    )
}

export default Detail;