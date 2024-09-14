import React, { useEffect } from "react";
import "../../styles/motion/sendAnimation.css";

const ReciveAnimation: React.FC = () => {
  useEffect(() => {
    // 페이지가 로드되면 자동으로 초기화 후 1.5초 후에 애니메이션 실행
    resetAnimation();
    setTimeout(sendAnimation, 100);
  }, []);

  useEffect(() => {
    setTimeout(()=>{
      window.location.href = "/main";

    }, 2800)
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

  const handleAnimationEnd = () => {
    // 애니메이션이 끝나면 페이지를 이동합니다.
    window.location.href = "/main";
  };

  return (
    <div className="background">
      <input type="checkbox" id="cb" style={{ display: 'none' }} />
      <svg className="icon plane" onAnimationEnd={handleAnimationEnd}>
        <polyline points="119,1 1,59 106,80 119,1"></polyline>
        <polyline points="119,1 40,67 43,105 69,73"></polyline>
      </svg>
      <div className="send_message" >
        to send...
      </div>
    </div>
  );
};

export default ReciveAnimation;