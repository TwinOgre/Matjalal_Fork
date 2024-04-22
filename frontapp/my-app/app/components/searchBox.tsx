import { useState } from 'react';
import api from '../utils/api';
export default function SearchBox({ brand, setArticles }) {
    const [keyword, setKeyword] = useState('');

    const changeSearchWordHandler = (event) => {
        event.preventDefault();

        setKeyword(event.target.value);
        console.log(event.target.value);
    };

    const searchSubmit = async () => {
        api.get(`/articles/search?brand=${brand}&keyword=${keyword}`).then((response) =>
            setArticles(response.data.data.articles)
        );
        // await fetch(`http://localhost:8090/api/v1/articles/search?brand=${brand}&keyword=${keyword}`)
        //   .then(res => console.log(res))
    };
    return (
        <>
            <div className="flex justify-end my-10 h-10">
                <div className="border rounded-md w-1/6">
                    <input
                        className="ml-2 w-11/12 h-full"
                        onChange={changeSearchWordHandler}
                        type="text"
                        placeholder="검색어를 입력하세요."
                    />
                </div>
                <button className="border rounded-md ml-2 w-12" onClick={searchSubmit}>
                    찾기
                </button>
            </div>
        </>
    );
}
