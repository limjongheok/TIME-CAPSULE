
import { useParams } from "react-router";

export default function RedirectToken(){

    const params = useParams();
    const token:string = params.token as string;
    localStorage.clear();
    localStorage.setItem("ACCESS_TOKEN","Bearer "+ token);
    console.log("Bearer "+token)
    window.location.href = "/main";
    return(
        <div>
        </div>

    )
}