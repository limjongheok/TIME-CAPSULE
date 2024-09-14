import { IconButton, TextField } from "@mui/material";
import "../../../../styles/info/myFriend.css";
import SearchIcon from "@mui/icons-material/Search";
import axios from "axios";
import { API_BASE_URL } from "../../../../api/App-config";
import { ChangeEvent, useEffect, useState } from "react";

interface Friend {
  email: string;
  imgUrl: string;
  name: string;
}

interface Props {
  email:string;
}

export default function Friend({email}:Props) {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  const [searchResult, setSearchResult] = useState<Friend[]>([]);

  const handleFriendClick = (email: string) => {
    window.location.href = `/friend/profile/${email}`;
  };

  useEffect(() => {
    axios
      .post(API_BASE_URL + "/api/user/find/friend", {email:email},{
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        setSearchResult(res.data);
        console.log("이메일로 친구 목록 불러오기 : ", res.data);
      });
  }, []);
  return (
    <div className="my_friend">
      <div className="find_friends">
        {searchResult.map((friend, index) => (
          <div key={friend.email} className="my_Search" onClick={() => handleFriendClick(friend.email)} style={{cursor : "pointer"}}>
            <img src={friend.imgUrl} alt="img" />
            <p>
              <b>{friend.name}</b>
            </p>
            <p>{friend.email}</p>
          </div>
        ))}
        
      </div>
    </div>
  );
}
