import { API_BASE_URL } from "./App-config";
import axios from "axios";

const ACCESS_TOKEN = "ACCESS_TOKEN";

//로그인
export const login = (userDto: userDto): void => {
  axios
    .post(API_BASE_URL + "/api/login", userDto)
    .then((res) => {
      let jwt = res.headers['authorization'];

      if (jwt !== null) {
        localStorage.setItem(ACCESS_TOKEN, jwt);
        window.location.href = "/main";
      }
    })
    .catch((err) => {
      if (err.status !== 200) {
        window.location.href = "/login";
      }
    });
};

// 인증 번호 전송
export const sendAuth = (sendAuthDto :sendAuthDto , props: props) : void =>{
  axios
    .post(API_BASE_URL + "/api/mail/send/auth", sendAuthDto)
    .then((res) =>{
      alert("인증메일이 전송되었습니다.")
      props.handleState(false);
    })
    .catch((err) => {
      if(err.status !== 200){
        alert("인증메일이 전송실패");
      }
    })
}

// 인증 번호 체크
export const checkAuth = (chechAuthDto : chechAuthDto , props: props) :void =>{
  axios
  .post(API_BASE_URL + "/api/mail/check", chechAuthDto)
  .then((res)=> {
    props.handleState(true);
  }).catch((err) => {
    if(err.status !== 200){
      console.log("인증 번호가 틀렸습니다.")
      props.handleState(false);
    }
  })
}

//회원가입 
export const signup = (signupDto :signupDto) : void =>{
  axios
  .post(API_BASE_URL + "/api/user/join", signupDto)
  .then((res)=> {
    window.location.href = "/login";
  }).catch((err) => {
    console.log(err);
    if(err.status !== 200){
      alert("회원가입에 실패하였습니다.");
      window.location.href = "/join";
    }
  })
} 


interface userDto {
  email: string;
  password: string;
}

interface sendAuthDto{
  email : string;
}

interface chechAuthDto{
  email : string;
  authCode : string;
}
interface props {
  handleState : (value : boolean) => void;
}
interface signupDto{
  email: string;
  password: string;
  name: string;
  phoneNumber: string
}