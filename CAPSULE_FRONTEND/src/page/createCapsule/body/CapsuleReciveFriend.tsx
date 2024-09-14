import { IconButton } from "@mui/material";
import RemoveIcon from "@mui/icons-material/Remove";
import React from "react";

interface Friend {
  friendEmail: string;
  friendImgUrl: string;
  friendName: string;
}

interface Props {
  friends: Friend[];
  onRemoveFriend: (friend: Friend) => void;
}

const CapsuleReciveFriend: React.FC<Props> = ({ friends, onRemoveFriend }) => {
  const handleMinusFriend = (friend: Friend) => {
    onRemoveFriend(friend);
  };
  return (
    <div className="capsule_seacrh_friend">
      <div className="capsule_search_main">
        <div className="capsule_search_body">
          {friends.map((friend) => (
            <div key={friend.friendEmail} className="search">
              <img src={friend.friendImgUrl} alt="img" />
              <p style={{ fontSize: "0.8vw" }}>
                <b>{friend.friendName}</b>
              </p>
              <p style={{ fontSize: "0.8vw" }}>{friend.friendEmail}</p>
              <div className="search_button">
                <IconButton
                  onClick={() => handleMinusFriend(friend)}
                  style={{ position: "absolute", right: "0", color: "#9FCFFC" , width :"1vw" , height :"1vw" }}
                >
                  <RemoveIcon style={{ width :"1.5vw" , height :"1.5vw"}} />
                </IconButton>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default CapsuleReciveFriend;
