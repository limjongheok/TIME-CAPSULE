import axios from "axios";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../../api/App-config";
import ReadBodyComponent from "./ReadBodyComponent";
import Modal from "react-modal";
import { Button, CircularProgress } from "@mui/material";

interface Result {
  name: string;
  memo: string;
  imgUrl: string;
}

interface Props {
  id: string;
}

const ReadCapsuleBody: React.FC<Props> = ({ id }) => {
  const [result, setResult] = useState<Result[]>([]);
  const [combinedResult, setCombinedResult] = useState<string>("");
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [imgUrl, setImgUrl] = useState<string | null>(null);

  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/read/" + id, {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        console.log(res.data);
        setResult(res.data);

        // Combine all values of the result array into a single string
        const combinedString = res.data
          .map((item: Result) => `${item.name}_,_${item.memo}`)
          .join("_,_");
        setCombinedResult(combinedString);
      });
  }, [id, accessToken]);

  const sendCombinedResult = async () => {
    try {
      console.log("combinedResult : ", combinedResult);
      const response = await axios.post(
        API_BASE_URL + "/api/v1/ai/split-noun",
        {
          text: combinedResult,
        },
        {
          headers: {
            Authorization: `${accessToken}`,
          },
        }
      );
      console.log("명사 분리 결과값 :", response.data);

      await generateImage(response.data);
    } catch (error) {
      console.error("Error sending combined result to AI:", error);
    }
  };

  const generateImage = async (nounData: string) => {
    setIsLoading(true);
    try {
      const response = await axios.post(
        API_BASE_URL + "/api/v1/img/generate",
        {
          capsuleId: id,
          text: nounData,
        },
        {
          headers: {
            Authorization: `${accessToken}`,
          },
        }
      );
      console.log("이미지 생성 결과값 :", response.data);
      await fetchImage();
      setIsLoading(false);
    } catch (error) {
      console.error("Error generating image:", error);
      setIsLoading(false);
    }
  };

  const fetchImage = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get(
        API_BASE_URL + "/api/v1/img/generate/"+id
      );
      console.log("img url : ", response.data);
      setImgUrl(response.data);
      setIsLoading(false);
    } catch (error) {
      console.error("Error fetching image:", error);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (combinedResult) {
      sendCombinedResult();
    }
  }, [combinedResult]);

  const handleModalOpen = () => {
    setModalIsOpen(true);
    fetchImage();
  };

  return (
    <div className="read-page-body">
      {result.map((item, index) => (
        <ReadBodyComponent
          key={index}
          name={item.name}
          memo={item.memo}
          imgUrl={item.imgUrl}
        />
      ))}
      <Button
        variant="contained"
        color="primary"
        onClick={handleModalOpen}
        sx={{ marginTop: "20px" }}
      >
        추억 돌아보기
      </Button>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        className="ReactModal__Content"
        overlayClassName="ReactModal__Overlay"
      >
        <div className="modal-header">
          <h2>추억 이미지</h2>
          <button onClick={() => setModalIsOpen(false)}>&times;</button>
        </div>
        <div className="modal-body">
          {isLoading ? (
            <CircularProgress />
          ) : imgUrl ? (
            <img src={imgUrl} alt="Generated" style={{ width: "100%" }} />
          ) : (
            <p>로딩 중..</p>
          )}
        </div>
      </Modal>
    </div>
  );
};

export default ReadCapsuleBody;
