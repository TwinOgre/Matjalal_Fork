'use client'
import { useState, useEffect } from "react";
import api from "../utils/api";
interface ingredientsInterface {
    id: string,
    name: string,
    type: string
}
interface IngredientCheckBoxProps {
    onIngredientChange: (ingredients: ingredientsInterface[]) => void;
    onIngredientRemove:(removedIngredient: ingredientsInterface) => void;
    ingredientType: string
    maxChecked: number;
}
const IngredientCheckBox: React.FC<IngredientCheckBoxProps> = ({ onIngredientChange, onIngredientRemove , ingredientType, maxChecked }) => {

    // 불러올 재료 
    const [ingredients, setIngredients] = useState<ingredientsInterface[]>([]);
    useEffect(() => {
        api.get(`/ingredients/type/${ingredientType}`)
            .then(response => setIngredients(response.data.data.ingredients))
            .catch(err => {
                console.log(err)
            })
    }, [])


    const [checkedCount, setCheckedCount] = useState<number>(0); // 체크된 재료의 수
    // 체크된 재료
    const [checkedIngredients, setCheckedIngredients] = useState<ingredientsInterface[]>([]);

    const handleIngredientChange = (e: React.ChangeEvent<HTMLInputElement>, ingredient: ingredientsInterface) => {
        // 현재 체크된 재료의 수
        const isChecked = e.target.checked;
        // 체크 가능 수를 초과하는지 확인
        const currentCheckedCount = isChecked ? checkedCount + 1 : checkedCount - 1;
        if (currentCheckedCount > maxChecked) {
            // 초과하면 체크 이벤트 무시하고 이전 상태로 되돌리기
            e.preventDefault();
            e.target.checked = !isChecked; // 체크를 해제합니다.
            return;
        }
        // 체크된 재료의 수 갱신
        setCheckedCount(currentCheckedCount);



        const updatedIngredients = isChecked
            ? [...checkedIngredients, ingredient]
            : checkedIngredients.filter(item => ingredient !== ingredient);

        if (isChecked) {
            // 체크된 경우, 해당 재료를 추가합니다.
            setCheckedIngredients(prevCheckedIngredients => [...prevCheckedIngredients, ingredient]);
        } else {
            // 체크가 해제된 경우, 해당 재료를 제거합니다.
            setCheckedIngredients(prevCheckedIngredients => prevCheckedIngredients.filter(item => item !== ingredient));
            onIngredientRemove(ingredient)
        }
        
    }
    useEffect(() => {
        onIngredientChange(checkedIngredients);
        console.log(checkedIngredients);
    }, [checkedIngredients]);

    return (
        <div className="relative flex-grow w-full">
            <label className="text-lg font-bold">{ingredientType}</label>
            <br />
            <label className="text-sm font-bold">최대 {maxChecked}개!</label>
            {ingredients.map(ingredient =>
                <div key={ingredient.id.toString()}>
                    <input type="checkbox" id={ingredient.name} name={ingredient.name} onChange={(e) => handleIngredientChange(e, ingredient)} className="w-4 h-4 mr-1" />
                    <label htmlFor={ingredient.name}>{ingredient.name}</label>
                </div>)}
        </div>
    )
}
export default IngredientCheckBox;