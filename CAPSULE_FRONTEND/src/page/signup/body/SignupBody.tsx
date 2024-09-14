import { ChangeEvent, FormEvent, useState } from "react";
import { Button, TextField } from "@mui/material";
import { signup, sendAuth, checkAuth } from "../../../api/Api";
import "../../../styles/signup/signUpBody.css";
import Check from "../../../images/check.svg";

export default function SignupBody() {
  const [email, setEmail] = useState<string>("");
  const [authCode, setAuthCode] = useState<string>("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [emailError, setEmailError] = useState<boolean>(false);
  const [phoneError, setPhoneError] = useState<boolean>(false);
  const [mailState, setMailState] = useState<boolean>(true);
  const [authState, setAuthState] = useState<boolean>(false);

  const isDisabled = emailError || phoneError || !authState;

  const handleEmailChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setEmail(value);

    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    setEmailError(!emailPattern.test(value));
  };
  const handleAuthCodeChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setAuthCode(value);
  };

  const handlePhoneChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setPhoneNumber(value);

    const phonePattern = /^01(?:0|1|[6-9])[.-]?(?:\d{3}|\d{4})[.-]?\d{4}$/;
    setPhoneError(!phonePattern.test(value));
  };
  const checkMailState =(value: boolean) :void =>{
    setMailState(value);
  }

  const sendEmailByAuth = (): void => {
    sendAuth({ email: email }, {handleState: checkMailState});
  };


  const checkAuthState = (value: boolean): void => {
    setAuthState(value);
  };

  const checkAuthCode = (): void => {
    checkAuth(
      { email: email, authCode: authCode },
      { handleState: checkAuthState }
    );
  };

  const HandleSignup = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const data = new FormData(e.currentTarget);
    const password: string = data.get("password") as string;
    const name: string = data.get("name") as string;
    const phoneNumber: string = data.get("phoneNumber") as string;

    signup({ email: email, password: password , name : name, phoneNumber : phoneNumber});
  };

  return (
    <div className="sign_up_body">
      <form onSubmit={HandleSignup}>
        <div>
          <TextField
            id="outlined-basic"
            label="이메일 주소"
            variant="outlined"
            name="email"
            type="email"
            value={email}
            onChange={handleEmailChange}
            error={emailError}
            helperText={emailError ? "유효한 이메일 주소를 입력하세요." : ""}
            autoComplete="off"
            disabled = {authState}
            sx={{
              backgroundColor: "#F5F5F5",
              marginBottom: "5%",
              color: "#BFBFBF",
              border: "none",
              width: authState ? "346px" : "320px",
              borderRadius: "10px",
              "& .MuiOutlinedInput-notchedOutline": {
                border: "none",
              },
            }}
          />
          {authState ? (
            <></>
          ) : (
            <Button
            variant="contained"
            onClick={sendEmailByAuth}
            disabled={emailError}
            sx={{
              background: "linear-gradient(to right, #B4DBFF, #9FCFFC)",
              color: "#fffff",
              marginLeft: "10px",
              width: "70px",
              height: "50px",
              borderRadius: "10px",
              textAlign: "center",
            }}
          >
            메일 전송
          </Button>

          )
          }
         
        </div>
        <div>
          <TextField
            id="outlined-basic"
            label="인증번호"
            variant="outlined"
            name="authCode"
            value={authCode}
            onChange={handleAuthCodeChange}
            autoComplete="off"
            disabled={authState}
            sx={{
              backgroundColor: "#F5F5F5",
              marginBottom: "10%",
              color: "#E7E7E7",
              border: "none",
              width: authState ? "346px" : "320px",
              borderRadius: "10px",
              "& .MuiOutlinedInput-notchedOutline": {
                border: "none",
              },
            }}
          />
          {authState ? (
            <img src={Check} alt="check" className="check_img"/>
          ) : (
            <Button
              variant="contained"
              onClick={checkAuthCode}
              disabled = {mailState}
              sx={{
                background: "linear-gradient(to right, #B4DBFF, #9FCFFC)",
                color: "#fffff",
                marginLeft: "10px",
                width: "70px",
                height: "50px",
                borderRadius: "10px",
                textAlign: "center",
              }}
            >
              인증
            </Button>
          )}
        </div>

        <TextField
          id="outlined-basic"
          label="비밀번호"
          variant="outlined"
          name="password"
          autoComplete="off"
          sx={{
            backgroundColor: "#F5F5F5",
            marginBottom: "10%",
            color: "#BFBFBF",
            border: "none",
            display: "block",
            width: "400px",
            borderRadius: "10px",
            "& .MuiOutlinedInput-notchedOutline": {
              border: "none",
            },
          }}
        />
        <TextField
          id="outlined-basic"
          label="이름"
          variant="outlined"
          name="name"
          autoComplete="off"
          sx={{
            backgroundColor: "#F5F5F5",
            marginBottom: "10%",
            color: "#BFBFBF",
            border: "none",
            display: "block",
            width: "400px",
            borderRadius: "10px",
            "& .MuiOutlinedInput-notchedOutline": {
              border: "none",
            },
          }}
        />
        <TextField
          id="phone-number"
          label="전화번호"
          variant="outlined"
          name="phoneNumber"
          value={phoneNumber}
          onChange={handlePhoneChange}
          error={phoneError}
          helperText={phoneError ? "010-xxxx-xxxx로 입력해주세요" : ""}
          autoComplete="off"
          sx={{
            backgroundColor: "#F5F5F5",
            marginBottom: "10%",
            color: "#BFBFBF",
            border: "none",
            display: "block",
            width: "400px",
            borderRadius: "10px",
            "& .MuiOutlinedInput-notchedOutline": {
              border: "none",
            },
          }}
          inputProps={{
            pattern: "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
          }}
        />
        <Button
          variant="contained"
          type="submit"
          disabled={isDisabled}
          sx={{
            background: "linear-gradient(to right, #B4DBFF, #9FCFFC)",
            color: "#fffff",
            display: "block",
            width: "400px",
            height: "50px",
            borderRadius: "10px",
            marginBottom : "3%"
          }}
        >
          회원가입
        </Button>
      </form>
      <p>이미 회원이라면?  <a href="/login"> 로그인하기</a></p>
    </div>
    
  );
}
