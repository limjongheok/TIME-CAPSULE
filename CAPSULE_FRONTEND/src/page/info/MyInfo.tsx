import { useEffect, useState } from "react";
import "../../styles/info/info.css";
import axios from "axios";
import { API_BASE_URL } from "../../api/App-config";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import AddIcon from "@mui/icons-material/Add";

interface Info {
  email: string;
  name: string;
  imgUrl: string;
  phoneNumber: string;
}

interface Props {
  getMyInfo: Info | undefined;
  updateMyInfo: (myInfo: Info) => void;
}

export default function MyInfo({ getMyInfo, updateMyInfo }: Props) {
  const [myInfo, setMyInfo] = useState<Info>();
  const [isEditing, setIsEditing] = useState(false);
  const [editedInfo, setEditedInfo] = useState<Info | undefined>(getMyInfo);
  const [previewImage, setPreviewImage] = useState<File | undefined>();
  const [updateName, setUpdateName] = useState<string>("");
  const [updateNumber, setUpdateNumber] = useState<string>("");
  const [phoneError, setPhoneError] = useState<boolean>(false);
  const key = process.env.REACT_APP_IMGBB_KEY;
  console.log(key);

  const handleFriendList = () => {
    window.location.href = "/friend/list";
  };

  const accessToken = localStorage.getItem("ACCESS_TOKEN");
  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
        setMyInfo(res.data);
        updateMyInfo(res.data);
      });
  }, []);

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleSaveClick = async () => {
    if (phoneError) {
      return; // 전화번호 형식이 올바르지 않으면 저장하지 않음
    }
    if (previewImage) {
      try {
        const formData = new FormData();
        formData.append("image", previewImage);

        const response = await fetch(
          "https://api.imgbb.com/1/upload?key=" + key,
          {
            method: "POST",
            body: formData,
          }
        );
        if (response.ok) {
          const data = await response.json();

          axios
            .put(
              API_BASE_URL + "/api/user",
              {
                name: updateName,
                phoneNumber: updateNumber,
                imgUrl: data.data.url,
              },
              {
                headers: {
                  Authorization: `${accessToken}`,
                },
              }
            )
            .then((res) => {
              setMyInfo(res.data);
              updateMyInfo(res.data);
            })
            .catch(() => {
              alert("업데이트 실패");
            });
        } else {
          console.error("이미지 업로드 실패:", response.status);
          return null;
        }
      } catch (error) {
        console.error("이미지 업로드 오류:", error);
        return null;
      }
    } else {
      axios
        .put(
          API_BASE_URL + "/api/user",
          { name: updateName, phoneNumber: updateNumber, imgUrl: "" },
          {
            headers: {
              Authorization: `${accessToken}`,
            },
          }
        )
        .then((res) => {
          setMyInfo(res.data);
          updateMyInfo(res.data);
        })
        .catch(() => {
          alert("업데이트 실패");
        });
    }

    setPreviewImage(undefined);
    setIsEditing(false);
  };

  const handleChangeName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUpdateName(value);
  };

  const handleChangePhoneNumber = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUpdateNumber(value);
    const phonePattern = /^01(?:0|1|[6-9])[.-]?(?:\d{3}|\d{4})[.-]?\d{4}$/;
    setPhoneError(!phonePattern.test(value));
  };
  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setPreviewImage(e.target.files[0]);
    }
  };

  const handleDeleteClick = () => {
    axios
      .delete(API_BASE_URL + "/api/user", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then(() => {
        localStorage.clear();
        window.location.href = "/";
      })
      .catch(() => {
        alert("삭제 실패");
      });
  };

  const cansle = () => {
    setIsEditing(false);
    setPreviewImage(undefined);
    setUpdateName("");
    setUpdateNumber("");
  };

  return (
    <div className="info">
      <div className="user_info">
        <div className="user_img">
          {isEditing ? (
            <>
              <label htmlFor="image-upload" style={{ cursor: "pointer" }}>
                {previewImage ? (
                  <Avatar
                    src={URL.createObjectURL(previewImage)}
                    alt="img"
                    sx={{ width: "5vw", height: "5vw" }}
                  />
                ) : editedInfo?.imgUrl ? (
                  <Avatar
                    src={editedInfo?.imgUrl}
                    alt="img"
                    sx={{ width: "5vw", height: "5vw" }}
                  />
                ) : (
                  <Box
                    sx={{
                      width: "5vw",
                      height: "5vw",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                      backgroundColor: "gray",
                      cursor: "pointer",
                    }}
                  >
                    <AddIcon sx={{ color: "white" }} />
                  </Box>
                )}
              </label>
              <input
                accept="image/*"
                style={{ display: "none" }}
                id="image-upload"
                type="file"
                onChange={handleImageChange}
              />
            </>
          ) : (
            <Avatar
              src={myInfo?.imgUrl}
              alt="img"
              sx={{ width: "5vw", height: "5vw" }}
            />
          )}
        </div>
        <div className="user_name">
          {isEditing ? (
            <TextField
              label="Name"
              variant="outlined"
              name="name"
              value={editedInfo?.name}
              onChange={handleChangeName}
              style={{
                marginBottom: "3%",
                fontSize: "1vw",
                width: "100%",
              }}
              InputProps={{
                style: {
                  height: "3hw",
                },
              }}
              InputLabelProps={{
                style: {
                  fontSize: "1hw",
                },
              }}
            />
          ) : (
            <h2 style={{ fontSize: "1vw", marginBottom: "3%" }}>
              {myInfo?.name}
            </h2>
          )}
          <div>
            {!isEditing && <p style={{ fontSize: "1vw" }}>{myInfo?.email}</p>}
            {isEditing ? (
              <TextField
                label="Phone Number"
                variant="outlined"
                name="phoneNumber"
                value={editedInfo?.phoneNumber}
                onChange={handleChangePhoneNumber}
                error={phoneError}
                helperText={phoneError ? "010-xxxx-xxxx로 입력해주세요" : ""}
                autoComplete="off"
                style={{
                  marginBottom: "3%",
                  fontSize: "1vw",
                  width: "100%",
                }}
                InputProps={{
                  style: {
                    height: "3hw",
                  },
                }}
                InputLabelProps={{
                  style: {
                    fontSize: "1hw",
                  },
                }}
              />
            ) : (
              <p style={{ fontSize: "1vw" }}>{myInfo?.phoneNumber}</p>
            )}
            <div className="userUpdate">
              {isEditing ? (
                <>
                  <button className="info_button" onClick={handleSaveClick}>
                    저장하기
                  </button>
                  <button className="info_button" onClick={cansle}>
                    취소하기
                  </button>
                </>
              ) : (
                <>
                  <button className="info_button" onClick={handleEditClick}>
                    수정하기
                  </button>
                  <button className="info_button" onClick={handleDeleteClick}>
                    삭제하기
                  </button>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
      <div className="friend_list" onClick={handleFriendList}>
        <p style={{ fontSize: "1vw" }}>{`친구 신청 목록 보기 >`}</p>
      </div>
    </div>
  );
}
