import { MemberInterface } from "../member/MemberInterfaces";

export interface ImageDataInterface {
    id: number;
    createdDate: string;
    modifiedDate: string;
    name: string;
    type: string;
    filePath: string;
    uploadPath: string;
    // member: MemberInterface;
}
