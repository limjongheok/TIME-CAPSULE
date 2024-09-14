import { useEffect, useState } from "react"
import "../../styles/login/loginPage.css"
import LoginMx900 from "./login_mx_900/LoginMx900";
import { getToken } from "../../util/firebase";
import axios from "axios";
import { API_BASE_URL } from "../../api/App-config";


export default function LoginPage(){
    const [screenWidth, setScreenWidth] = useState<number>(window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
    const accessToken = localStorage.getItem("ACCESS_TOKEN");

    useEffect(()=>{
        localStorage.clear();
    },[])
    useEffect(() => {
        const handleResize = () : void => {
             setScreenWidth(window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth);
        };
        window.addEventListener('resize', handleResize);
        return (): void => {
            window.removeEventListener('resize', handleResize);
        };

    },[]);

    return(
        <div className="page">
                <LoginMx900/>
        </div>
    )
}