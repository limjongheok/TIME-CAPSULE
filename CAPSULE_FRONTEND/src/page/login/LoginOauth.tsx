import "../../styles/login/login_mx_900/loginOauth.css";
import Kakao from "../../images/kakao.svg";
import Naver from "../../images/naver.svg";
import Google from "../../images/google.svg";
import { API_BASE_URL } from "../../api/App-config";

export default function LoginOauth(){

    const kakao = () : void=> {
        window.location.href = API_BASE_URL + "/api/oauth2/authorization/kakao"
    }

    const naver =  () : void => {
        window.location.href = API_BASE_URL+"/api/oauth2/authorization/naver"
    }
    const google =  () : void => {
        window.location.href = API_BASE_URL + "/api/oauth2/authorization/google"
    }

    return(
        <div className="login_oauth">
            <img src={Kakao} alt="카카오 oauth" onClick={kakao}/>
            <img src={Naver} alt="카카오 oauth" onClick={naver}/>
            <img src={Google} alt="카카오 oauth" onClick={google}/>
        </div>
    )
}