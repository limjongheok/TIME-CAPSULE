import "../../../styles/login/login_mx_900/loginMx900.css"
import Icon from "../../../images/capsule_blue.svg";
import LoginInput from "../LoginInput";
import LoginOauth from "../LoginOauth";

export default function LoginMx900(){
    return(
        <div className="login_mx_900">
            <img src={Icon} alt="icon"/>
            <LoginInput/>
            <LoginOauth/>
            <p>아직 회원이 아니라면? <a href="/signup">회원가입하기</a></p>
        </div>
    )
}