import { useEffect, useState } from "react";
import "../../../styles/request/request.css";
import { API_BASE_URL } from "../../../api/App-config";
import CheckIcon from "@mui/icons-material/Check";
import CloseIcon from "@mui/icons-material/Close";
import axios from "axios";
import { color } from "framer-motion";

interface Friend {
  friendEmail: string;
  friendImgUrl: string;
  friendName: string;
}

export default function ReceiveRequest() {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  const [searchResult, setSearchResult] = useState<Friend[]>([]);

  const handleClick = (email: string) => {
    axios
      .post(
        API_BASE_URL + "/api/user/friend",
        { friendEmail: email },
        {
          headers: {
            Authorization: `${accessToken}`,
          },
        }
      )
      .then((res) => {
        const updatedSearchResult = searchResult.filter(
          (friend) => friend.friendEmail !== email
        );
        setSearchResult(updatedSearchResult);
      });
  };
  const handleCancleClick = (email: string) =>{

    axios
    .post(
      API_BASE_URL + "/api/user/friend/request/delete",
      { email: email },
      {
        headers: {
          Authorization: `${accessToken}`,
        },
      }
    )
    .then((res) => {
      const updatedSearchResult = searchResult.filter(
        (friend) => friend.friendEmail !== email
      );
      setSearchResult(updatedSearchResult);
    });

  }
  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/friend/request/received", {
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
      <h3 style={{ fontSize: "1vw" }}>받은 친구</h3>
      <div className="requests">
        {searchResult.map((friend, index) => (
          <div key={friend.friendEmail} className="my_request">
            <img src={friend.friendImgUrl} alt="img" />
            <p style={{ fontSize: "1vw" }}>
              <b>{friend.friendName}</b>
            </p>
            <p style={{ fontSize: "1vw" }}>{friend.friendEmail}</p>
            <div className="accept">
              <CheckIcon
                onClick={() => handleClick(friend.friendEmail)}
                style={{ color: "#4ECB71", marginLeft: "10%" , cursor : "pointer", marginRight : "4%"}}
              />
              <CloseIcon onClick={()=> handleCancleClick(friend.friendEmail)}style={{ color: "F24E1E", marginLeft: "3%", cursor : "pointer" }} />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
