import { IconButton, TextField } from "@mui/material";
import "../../../../styles/info/myFriend.css";
import SearchIcon from "@mui/icons-material/Search";
import axios from "axios";
import { API_BASE_URL } from "../../../../api/App-config";
import { ChangeEvent, useEffect, useState } from "react";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

interface Friend {
  email: string;
  imgUrl: string;
  name: string;
}

export default function FindMembers() {
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  const [searchResult, setSearchResult] = useState<Friend[]>([]);

  const addFriend = (email : string) : void => {
    axios.post(API_BASE_URL + "/api/user/friend/request",{email: email},{
      headers: {
        Authorization: `${accessToken}`,
      },
    }).then(() => {
      const updatedSearchResult = searchResult.filter(friend => friend.email !== email);
      setSearchResult(updatedSearchResult);
    });
  }

  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/all", {
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
        API_BASE_URL + "/api/user/find",
        { email: value },
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
                    borderRadius: "30px",
                    height: "2.5vw",
                    width: "3vw",
                    right: "1vw",
                    position : "absolute",
                    color: "#9FCFFC",
                  }}
                >
                  <SearchIcon style={{height: "2vw",width: "5vw" }} />
                </IconButton>
              ),
            }}
          />
        </div>
      </div>
      <div className="find_friends">
        {searchResult.map((friend, index) => (
          <div key={friend.email} className="my_Search">
            <img src={friend.imgUrl} alt="img" />
            <p style={{fontSize : "1vw"}}>
              <b>{friend.name}</b>
            </p>
            <p style={{fontSize : "1vw"}}>{friend.email}</p>
            <AddCircleOutlineIcon style={{color: '#9FCFFC', cursor:"pointer" ,width : "1.5vw" , height:"1.5vw" }}  onClick={() => addFriend(friend.email)}/>
          </div>
        ))}
      </div>
    </div>
  );
}
