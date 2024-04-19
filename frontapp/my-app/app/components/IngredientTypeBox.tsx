import { ArticleInterface } from "../interface/article/ArticleInterfaces";
interface IngredientTypeBoxProps {
  article: ArticleInterface;
  ingredientType: string;
}
const IngredientTypeBox: React.FC<IngredientTypeBoxProps> = ({
  article,
  ingredientType,
}) => {
  return (
    <div
      key={ingredientType}
      className="flex border-t border-b border-gray-200 py-2"
    >
      <span className="text-gray-500">{ingredientType}</span>
      {article.ingredients.map(
        (ingredient) =>
          ingredient.type.includes(ingredientType) && (
            <span key={ingredient.id} className="ml-auto text-gray-900 ">
              <span className="border-l border-gray-200 mx-1"></span>{" "}
              {ingredient.name}{" "}
              <span className="border-r border-gray-200 mx-1"></span>
            </span>
          )
      )}
    </div>
  );
};

export default IngredientTypeBox;
