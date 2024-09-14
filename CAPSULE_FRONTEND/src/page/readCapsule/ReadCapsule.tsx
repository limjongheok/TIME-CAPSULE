import axios from "axios";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../api/App-config";
import { useParams } from "react-router-dom";
import "../../styles/read/readPage.css";
import MainHeader from "../../component/header/MainHeader";
import CapsuleHeader from "../../component/header/CapsuleHeader";
import ReadCapsuleBody from "./body/ReadCapsuleBody";

export default function ReadCapsule() {
  const params = useParams();
  const capsuleId: string = params.capsuleId as string;

  return (
    <div className="read_page">
      <div className="read_main_page">
        <div className="main_content">
          <MainHeader icon={"white"} />
          <CapsuleHeader id={capsuleId} check={"read"}/>
          <ReadCapsuleBody id={capsuleId}/>
        </div>
      </div>
    </div>
  );
}
