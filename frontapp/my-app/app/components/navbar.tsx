import Link from "next/link";
import HamburgerMenu from "./HamburgerMenu";

export default function Navbar() {
    return (
        <>
            <div className="sticky top-5 flex justify-between mx-5 my-5">
                <HamburgerMenu />
                <Link href="/member/login"><img className="size-10" src="/lock-icon.svg" alt="Locker Icon Image" /></Link>
            </div>
        </>
    );
}