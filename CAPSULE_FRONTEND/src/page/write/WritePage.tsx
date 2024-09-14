import { useParams } from "react-router-dom";
import CapsuleHeader from "../../component/header/CapsuleHeader";
import MainHeader from "../../component/header/MainHeader";
import "../../styles/write/writePage.css";
import WritePageBody from "./body/WritePageBody";

export default function WritePage() {
  const params = useParams();
  const capsuleId: string = params.capsuleId as string;
  return (
    <div className="write_page">
      <div className="main_page">
        <div className="main_content">
          <MainHeader icon={"blue"}/>
          <CapsuleHeader id={capsuleId} check={"write"} />
          <WritePageBody write={capsuleId}/>
        </div>
      </div>
    </div>
  );
}
