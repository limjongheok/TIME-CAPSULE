import React, { useState } from "react";
import { TextField, Button, IconButton } from "@mui/material";
import "../../../styles/write/writePageBody.css";
import { API_BASE_URL } from "../../../api/App-config";
import axios from "axios";
import AddPhotoAlternateIcon from "@mui/icons-material/AddPhotoAlternate";

interface Props {
  write: string;
}
const WritePageBody: React.FC<Props> = ({ write }) => {
  const [content, setContent] = useState("");
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [imageUrl, setImageUrl] = useState<string | null>(null);
  const [slang, setSlang] = useState<boolean>(false);

  const key = process.env.REACT_APP_IMGBB_KEY;
  console.log(key);
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  const uploadImageToImgBB = async (file: File): Promise<string | null> => {
    try {
      const formData = new FormData();
      formData.append("image", file);

      const response = await axios.post(
        "https://api.imgbb.com/1/upload?key=" + key,
        formData
      );

      if (response.status === 200) {
        return response.data.data.url; // 이미지 링크 반환
      } else {
        console.error("이미지 업로드 실패:", response.status);
        return null;
      }
    } catch (error) {
      console.error("이미지 업로드 오류:", error);
      return null;
    }
  };

  const handleFileIconClick = () => {
    const fileInput = document.createElement("input");
    fileInput.type = "file";
    fileInput.accept = "image/*";
    fileInput.onchange = async (event) => {
      const files = (event.target as HTMLInputElement).files;
      if (files && files.length > 0) {
        const file = files[0];
        setSelectedFile(file);
      }
    };
    fileInput.click();
  };

  const handleSendClick = async () => {
    // GPT 욕설 확인
    try {
      const res = await axios.post(
        API_BASE_URL + "/api/v1/ai/check-profanity",
        {
          prompt: content,
        }
      );
      console.log("욕 확인 : ", res);

      if (res.data) {
        if (selectedFile) {
          const imageUrl = await uploadImageToImgBB(selectedFile);
          if (imageUrl) {
            console.log("이미지 링크:", imageUrl);
            const res = await axios.post(
              API_BASE_URL + "/api/user/write/" + write,
              {
                imgUrl: imageUrl,
                memo: content,
              },
              {
                headers: {
                  Authorization: `${accessToken}`,
                },
              }
            );
            console.log("등록");
            window.location.href = "/send/" + write;
          } else {
            console.log("이미지 업로드 실패");
          }
        } else {
          const res = await axios.post(
            API_BASE_URL + "/api/user/write/" + write,
            {
              imgUrl: imageUrl,
              memo: content,
            },
            {
              headers: {
                Authorization: `${accessToken}`,
              },
            }
          );
          console.log("등록");
          window.location.href = "/send/" + write;
        }
      } else {
        alert("욕설은 안됩니다!");
        return;
      }
    } catch (err) {
      console.log("욕 확인 실패 : ", err);
      alert("캡슐이 열렸어요 새로고침을 해주세요");
      setSlang(true);
    }
  };

  return (
    <div className="write-page-body">
      <div className="write-content">
        <TextField
          id="outlined-multiline-flexible"
          label="memo"
          multiline
          rows={13}
          fullWidth
          variant="outlined"
          value={content}
          onChange={(event) => setContent(event.target.value)}
          sx={{
            height: "auto", // TextField의 높이를 100%로 설정
            alignItems: "flex-start",

            "& .MuiOutlinedInput-notchedOutline": {
              border: "none",
            },
            backgroundColor: "#ffffff",
            opacity: "50%",
          }}
        />
        <div
          className="file_Add_button"
          style={{ position: "absolute", bottom: "10px", right: "10px" }}
        >
          {selectedFile && (
            <div className="selected-file">
              <p>선택한 파일: {selectedFile.name}</p>
            </div>
          )}
          <IconButton
            onClick={handleFileIconClick}
            style={{ width: "1.5vw", height: "1.5vw", marginTop: "1%" }}
          >
            <AddPhotoAlternateIcon style={{ width: "2vw", height: "2vw" }} />
          </IconButton>
        </div>
      </div>
      <div className="write-footer">
        <Button
          variant="contained"
          color="primary"
          onClick={handleSendClick}
          sx={{
            background: "linear-gradient(to right, #B4DBFF, #9FCFFC)",
            color: "#fffff",
            display: "block",
            fontSize: "1.3vw",
            fontWeight: "bolder",
            width: "20vw",
            height: "4vw",
            borderRadius: "30px",
            marginTop: "3%",
          }}
        >
          저장
        </Button>
      </div>
    </div>
  );
};

export default WritePageBody;
