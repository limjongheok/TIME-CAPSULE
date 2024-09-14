import { useEffect, useState } from "react";
import CapsuleH from "../../images/capsule_h.svg";
import CapsuleW from "../../images/Capsule_w.svg";
import "../../styles/header/capsuleHeader.css";
import { API_BASE_URL } from "../../api/App-config";
import axios from "axios";

interface Props {
  id: string;
  check: string;
}

const CapsuleHeader: React.FC<Props> = ({ id, check }) => {
  const [title, setTitle] = useState("");
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  useEffect(() => {
    if (check == "write") {
      axios
        .get(API_BASE_URL + "/api/user/capsule/write/check/" + id, {
          headers: {
            Authorization: `${accessToken}`,
          },
        })
        .then((res) => {
          console.log(res.data.title);
          setTitle(res.data.title);
        })
        .catch((error) => {
          window.location.href = "/main";
        });
    } else {
      axios
        .get(API_BASE_URL + "/api/user/capsule/read/check/" + id, {
          headers: {
            Authorization: `${accessToken}`,
          },
        })
        .then((res) => {
          console.log(res.data);
          setTitle(res.data.title);
        })
        .catch((error) => {
          window.location.href = "/main";
        });
    }
  }, []);
  return (
    <div className="capsule_header">
      {check === "write" ? (
        <img src={CapsuleH} alt="icon" />
      ) : (
        <img src={CapsuleW} alt="icon" />
      )}
      <h2 style={{ fontSize: "2vw" }}>&nbsp; {title}</h2>
    </div>
  );
};

export default CapsuleHeader;
