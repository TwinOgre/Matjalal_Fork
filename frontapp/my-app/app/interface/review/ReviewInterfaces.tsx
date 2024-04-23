import { MemberInterface } from "../member/MemberInterfaces";
export interface ReviewInterface {
    id: number;
    content: string;
    createdDate: string;
    modifiedDate: string;
    author: MemberInterface;
}
