import { useState, ChangeEvent, useEffect } from "react";
import { IconButton, TextField } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import AddIcon from "@mui/icons-material/Add";
import "../../../styles/createCapsule/capsuleSearchFriend.css";
import axios from "axios";
import { API_BASE_URL } from "../../../api/App-config";
interface Friend {
  friendEmail: string;
  friendImgUrl: string;
  friendName: string;
}

interface Props {
  friends: Friend[];
  onAddFriend: (friend: Friend) => void;
}

const CapsuleSearchFriend: React.FC<Props> = ({ friends, onAddFriend }) => {
  const [searchResult, setSearchResult] = useState<Friend[]>([]);

  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  // 첫 랜더링시
  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/friends", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        setSearchResult(res.data);
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

  const handleAddFriend = (friend: Friend) => {
    onAddFriend(friend);
  };

  return (
    <div
      className="capsule_seacrh_friend"
      style={{ borderRight: "0.1vw solid #9FCFFC" }}
    >
      <div className="capsule_search_main">
        <div className="capsule_seach_header">
          <TextField
            variant="outlined"
            onChange={handleSearchChange}
            autoComplete="off"
            sx={{
              backgroundColor: "#CAE6FF",
              marginBottom: "5%",
              color: "#BFBFBF",
              border: "none",
              width: "80%",
              height: "3vw ",
              fontSize : "1vw",
              borderRadius: "10vw",
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
                    height: "3vw",
                    width: "3vw",
                    color: "#9FCFFC",
                    position : "absolute",
                    right : "1vw"
                  }}
                >
                  <SearchIcon style={{ height: "2vw" , width : "2vw" }} />
                </IconButton>
              ),
            }}
          />
        </div>
        <div className="capsule_search_body">
          {searchResult
            .filter(
              (friend) =>
                !friends.some((f) => f.friendEmail === friend.friendEmail)
            )
            .map((friend, index) => (
              <div key={friend.friendEmail} className="search">
                <img src={friend.friendImgUrl} alt="img" />
                <p style={{fontSize : "0.8vw"}}>
                  <b>{friend.friendName}</b>
                </p>
                <p style={{fontSize : "0.8vw"}}>{friend.friendEmail}</p>
                <div className="search_button">
                <IconButton
                  onClick={() => handleAddFriend(friend)}
                  style={{ position: "absolute", right: "0", color: "#9FCFFC" , width :"1vw" , height :"1vw" }}
                >
                  <AddIcon style={{ width :"1.5vw" , height :"1.5vw"}} />
                </IconButton>
                </div>
                
              </div>
            ))}
        </div>
      </div>
    </div>
  );
};

export default CapsuleSearchFriend;
