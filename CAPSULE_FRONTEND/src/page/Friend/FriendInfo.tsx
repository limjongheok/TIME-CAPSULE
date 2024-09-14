import { useEffect, useState } from "react";
import "../../styles/info/info.css";
import axios from "axios";
import { API_BASE_URL } from "../../api/App-config";
import Arrow from "../../images/arrow.svg";

interface Info {
  email: string;
  name: string;
  imgUrl: string;
  phoneNumber: string;
}

interface Props {
  email:string;
  getFriendInfo: Info | undefined;
  updateFriendInfo: (friendInfo: Info) => void;
}

export default function FriendInfo({ email, getFriendInfo, updateFriendInfo }: Props) {
  const [friendInfo, setFriendInfo] = useState<Info>();

  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  useEffect(() => {
    axios
      .post(API_BASE_URL + "/api/user", {email: email},{
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        console.log(res.data[0]);
        setFriendInfo(res.data[0]);
        updateFriendInfo(res.data[0]);
      });
  }, []);
  return (
    <div className="info">
      <div className="user_info">
        <div className="user_img">
          <img src={friendInfo?.imgUrl} alt="img" />
        </div>
        <div className="user_name">
          <h2 style={{ fontSize: "1.5rem", marginBottom: "3%" }}>
            {friendInfo?.name}
          </h2>
          <div>
            <p style={{ fontSize: "0.9rem" }}>{friendInfo?.email}</p>
            <p style={{ fontSize: "0.9rem" }}>{friendInfo?.phoneNumber}</p>
          </div>
        </div>
      </div>
    </div>
  );
}
