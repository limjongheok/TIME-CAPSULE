import { FormEvent } from "react";
import "../../styles/login/login_mx_900/loginInput.css";
import { Button, TextField } from "@mui/material";
import { login } from "../../api/Api";

export default function LoginInput() {

  const HandleLogin = (e:FormEvent<HTMLFormElement>) =>{
    e.preventDefault();
    const data = new FormData(e.currentTarget);
    const email:string = data.get('email') as string;
    const password:string = data.get('password') as string;

    login({email:email , password:password});
    
  } 
  return (
    <div className="login_input">
      <form onSubmit={HandleLogin}>
        <TextField
          id="outlined-basic"
          label="이메일 주소"
          variant="outlined"
          name="email"
          sx={{
            backgroundColor: "#F5F5F5",
            marginBottom: "5%",
            color: "#E7E7E7",
            border: "none",
            borderRadius: "10px",
            "& .MuiOutlinedInput-notchedOutline": {
              border: "none",
            },
          }}
        />
        <TextField
          id="outlined-basic"
          label="비밀번호"
          variant="outlined"
          name="password"
          sx={{
            backgroundColor: "#F5F5F5",
            marginBottom: "10%",
            color: "#E7E7E7",
            border: "none",
            borderRadius: "10px",
            "& .MuiOutlinedInput-notchedOutline": {
              border: "none", 
            },
          }}
        />
        <Button
          variant="contained"
          type="submit"
          sx={{
            background: "linear-gradient(to right, #B4DBFF, #9FCFFC)",
            color: "#fffff",
            display: "block",
            borderRadius: "10px",
          }}
        >
          로그인하기
        </Button>
      </form>
      
    </div>
  );
}
