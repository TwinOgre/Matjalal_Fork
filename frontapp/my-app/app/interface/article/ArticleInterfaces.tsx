import { MemberInterface } from "../user/MemberInterfaces";
import { IngredientInterface } from "../ingredient/IngredientInterfaces";
export interface ArticleInterface {
  id: number;
  createdDate: string;
  modifiedDate: string;
  subject: string;
  content: string;
  brand: string;
  author: MemberInterface;
  ingredients: IngredientInterface[];
}
