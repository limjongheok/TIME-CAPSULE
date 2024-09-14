import React, { useEffect, useRef } from "react";
import "../../styles/motion/sendAnimation.css";
import axios from "axios";
import { API_BASE_URL } from "../../api/App-config";
import { useParams } from "react-router-dom";
const ReciveAnimation: React.FC = () => {
  const params = useParams();
  const capsuleId: string = params.capsuleId as string;
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  useEffect(() => {
    // 페이지가 로드되면 자동으로 초기화 후 1.5초 후에 애니메이션 실행
    resetAnimation();
    setTimeout(sendAnimation, 100);
  }, []);

  useEffect(() => {
    console.log(capsuleId);
    axios.get(API_BASE_URL + "/api/user/read/" +capsuleId,
    {
      headers: {
        Authorization: `${accessToken}`,
      },
    }
    ).then((res)=>{
      console.log(res.data);
      setTimeout(()=>{
        window.location.href = "/capsule/" +capsuleId;

      }, 2800)
    }).catch((error) =>{
      window.location.href = "/main";
    })
  }, []);

  const resetAnimation = () => {
    // Checkbox 요소 체크 해제
    const checkbox = document.getElementById('cb') as HTMLInputElement;
    checkbox.checked = false;
  };

  const sendAnimation = () => {
    // Checkbox 요소 체크
    const checkbox = document.getElementById('cb') as HTMLInputElement;
    checkbox.checked = true;
  };

  return (
    <div className="background">
      <input type="checkbox" id="cb" style={{ display: 'none' }} />
      <div className="circle"></div>
      <div className="circle-outer"></div>
      <svg className="icon mail" >
        <polyline points="119,1 119,69 1,69 1,1"></polyline>
        <polyline points="119,1 60,45 1,1 119,1"></polyline>
      </svg>
    </div>
  );
};

export default ReciveAnimation;