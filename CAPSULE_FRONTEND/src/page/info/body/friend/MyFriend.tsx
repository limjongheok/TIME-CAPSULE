import { IconButton, TextField } from "@mui/material";
import "../../../../styles/info/myFriend.css";
import SearchIcon from "@mui/icons-material/Search";
import axios from "axios";
import { API_BASE_URL } from "../../../../api/App-config";
import { ChangeEvent, useEffect, useState } from "react";

interface Friend {
  friendEmail: string;
  friendImgUrl: string;
  friendName: string;
}

export default function MyFriend() {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  const [searchResult, setSearchResult] = useState<Friend[]>([]);

  const handleFriendClick = (friendEmail: string) => {
    window.location.href = `/friend/profile/${friendEmail}`;
  };

  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/friends", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        setSearchResult(res.data);
        console.log(res.data);
      });
  }, []);
  const handleSearchChange = (event: ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    console.log(value);
    axios
      .post(
        API_BASE_URL + "/api/user/friend/find",
        { friendEmail: value },
        {
          headers: {
            Authorization: `${accessToken}`,
          },
        }
      )
      .then((res) => {
        setSearchResult(res.data);
      });
  };
  return (
    <div className="my_friend">
      <div className="friends">
        <div className="search_myFriend">
          <TextField
            variant="outlined"
            onChange={handleSearchChange}
            autoComplete="off"
            sx={{
              backgroundColor: "#CAE6FF",
              marginBottom: "5%",
              color: "#BFBFBF",
              border: "none",
              width: "100%",
              height: "3vw ",
              borderRadius: "50px",
              display: "flex",
              justifyContent: "center",
              "& .MuiOutlinedInput-notchedOutline": {
                border: "none",
              },
            }}
            InputProps={{
              endAdornment: (
                <IconButton
                  type="submit"
                  aria-label="search"
                  style={{
                    backgroundColor: "#ffff",
                    borderRadius: "10vw",
                    height: "2.5vw",
                    width: "3vw",
                    right: "1vw",
                    position : "absolute",
                    
                    color: "#9FCFFC",
                  }}
                >
                  <SearchIcon style={{ height: "2vw",width: "5vw" }} />
                </IconButton>
              ),
            }}
          />
        </div>
      </div>
      <div className="find_friends">
        {searchResult.map((friend, index) => (
          <div key={friend.friendEmail} className="my_Search" onClick={() => handleFriendClick(friend.friendEmail)} style={{cursor : "pointer"}}>
            <img src={friend.friendImgUrl} alt="img" />
            <p style={{fontSize : "1vw"}}>
              <b>{friend.friendName}</b>
            </p>
            <p style={{fontSize : "1vw"}}>{friend.friendEmail}</p>
          </div>
        ))}
        
      </div>
    </div>
  );
}
