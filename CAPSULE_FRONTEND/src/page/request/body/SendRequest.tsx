import { useEffect, useState } from "react";
import "../../../styles/request/request.css";
import axios from "axios";
import { API_BASE_URL } from "../../../api/App-config";
interface Friend {
  friendEmail: string;
  friendImgUrl: string;
  friendName: string;
}

export default function SendRequest() {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  const [searchResult, setSearchResult] = useState<Friend[]>([]);

  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/friend/request/send", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        setSearchResult(res.data);
        console.log(res.data);
      });
  }, []);

  return (
    <div className="request">
      <h3 style={{fontSize : "1vw"}}>보낸 친구</h3>
      <div className="requests">
        {searchResult.map((friend, index) => (
          <div key={friend.friendEmail} className="my_request">
            <img src={friend.friendImgUrl} alt="img" />
            <p style={{fontSize : "1vw"}}>
              <b>{friend.friendName}</b>
            </p>
            <p style={{fontSize : "1vw"}}> {friend.friendEmail}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
