import { MemberInterface } from "../member/MemberInterfaces";
export interface ReviewInterface {
  id: string;
  content: string;
  createdDate: string;
  modifiedDate: string;
  author: MemberInterface;
}
