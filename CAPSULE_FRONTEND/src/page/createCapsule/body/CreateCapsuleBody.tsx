import React, { useEffect, useState } from "react";
import CapsuleHeader from "../../../component/header/CapsuleHeader";
import "../../../styles/createCapsule/capsuleBody.css";
import CapsuleReciveFriend from "./CapsuleReciveFriend";
import CapsuleSearchFriend from "./CapsuleSearchFreind";
import { TextField } from "@mui/material";
import { API_BASE_URL } from "../../../api/App-config";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";
import axios from "axios";

interface Friend {
  friendEmail: string;
  friendImgUrl: string;
  friendName: string;
}

const CreateCapsuleBody: React.FC = () => {
  const [title, setTitle] = useState("");
  const [reciveFriends, setReciveFriends] = useState<Friend[]>([]);
  const [dateTime, setDateTime] = useState("");
  const [latitude, setLatitude] = useState("");
  const [longitude, setLongitude] = useState("");
  const [openModal, setOpenModal] = useState(false);
  const [capsuleId, setCapsuleId] = useState("");

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    const textToCopy = `https://capsuletalk.site/write/${capsuleId}`;
    navigator.clipboard
      .writeText(textToCopy)
      .then(() => {})
      .catch((error) => {
        console.error("복사 실패:", error);
      });

    setOpenModal(false);
    window.location.href = `/write/${capsuleId}`;
  };

  const handleAddFriend = (friend: Friend) => {
    // 이미 추가된 친구인지 확인
    const isDuplicate = reciveFriends.some(
      (f) => f.friendEmail === friend.friendEmail
    );

    if (!isDuplicate) {
      setReciveFriends([...reciveFriends, friend]);
    } else {
      console.log("이미 추가된 친구입니다.");
    }
  };

  const handleRemoveFriend = (friend: Friend) => {
    const updatedFriends = reciveFriends.filter(
      (f) => f.friendEmail !== friend.friendEmail
    );
    setReciveFriends(updatedFriends);
  };
  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setTitle(value);
  };

  const handleDateTimeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDateTime(event.target.value);
  };

  const createCapsule = () => {
    console.log(dateTime.toString());
    const accessToken = localStorage.getItem("ACCESS_TOKEN");
    const friendEmails: string[] = reciveFriends.map(
      (friend) => friend.friendEmail
    );
    axios
      .post(
        API_BASE_URL + "/api/user/capsule",
        {
          title: title,
          endTime: dateTime.toString() + ":00",
          latitude: latitude,
          longitude: longitude,
          membersEmail: friendEmails,
        },
        {
          headers: {
            Authorization: `${accessToken}`,
          },
        }
      )
      .then((res) => {
        setCapsuleId(res.data.capsuleId);
        handleOpenModal();
      })
      .catch((error) => {
        alert("캡슐 생성 실패");
      });
  };
  useEffect(() => {
    navigator.geolocation.getCurrentPosition(function (position) {
      var lat: string = position.coords.latitude.toString(), // 위도
        lon: string = position.coords.longitude.toString(); // 경도
      setLatitude(lat);
      setLongitude(lon);
    });
  }, []);
  return (
    <div className="main_body">
      <div className="capsule_header">
        <TextField
          variant="outlined"
          onChange={handleTitleChange}
          autoComplete="off"
          sx={{
            marginBottom: "2%",
            color: "#BFBFBF",
            width: "40%",
            height: "3vw",
            textAlign: "center",
            borderRadius: "10vw",
            "& .MuiOutlinedInput-notchedOutline": {
              border: "none",
            },
            "& input::placeholder": {
              textAlign: "center",
            },
            "& input": {
              textAlign: "center",
              fontSize: "1.4vw",
              fontWeight: "bold",
            },
          }}
          InputProps={{
            placeholder: "타이틀을 입력해주세요",
          }}
        />
      </div>

      <div className="capsule_body">
        <CapsuleSearchFriend
          friends={reciveFriends}
          onAddFriend={handleAddFriend}
        />
        <CapsuleReciveFriend
          friends={reciveFriends}
          onRemoveFriend={handleRemoveFriend}
        />
      </div>
      <div className="capsule_button">
        <input
          type="datetime-local"
          value={dateTime}
          onChange={handleDateTimeChange}
          style={{ fontSize: "1vw" }}
          placeholder="날짜를 입력하세요"
        />
        <button onClick={createCapsule}>타임 캡슐 생성</button>
      </div>
      <Dialog open={openModal} onClose={handleCloseModal} className="modal">
        <DialogTitle>
          <div className="modal_header">
            <h2>!!캡슐 생성!!</h2>
          </div>
        </DialogTitle>
        <DialogContent>
          <div className="modal_main">
            <p style={{ color: "blue", fontWeight: "bolder", fontSize: "1vw" }}>
              <b>https://capsuletalk.site/write/copy/{capsuleId}</b>
            </p>
            <br />
            <p>추억을 작성할 공간입니다.</p>
            <p>공유해 주세요</p>
          </div>
        </DialogContent>
        <DialogActions>
          <div className="modal_button">
            <div>
              <Button
                onClick={handleCloseModal}
                style={{
                  width: "100%",
                  color: "white",
                  backgroundColor: "#B4DBFF",
                  borderRadius: "10vw",
                  fontSize: "1vw",
                }}
              >
                복사하기
              </Button>
            </div>
          </div>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default CreateCapsuleBody;
