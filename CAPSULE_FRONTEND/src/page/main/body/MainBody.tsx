import "../../../styles/main/mainBody.css";
import { EventSourcePolyfill, NativeEventSource } from "event-source-polyfill";
import { useEffect, useState } from "react";
import { API_BASE_URL } from "../../../api/App-config";
import AddbuttonOpen from "../../../images/capsule_open.svg";
import AddbuttonClose from "../../../images/capsule_close.svg";
import axios from "axios";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";
import MainImg from "./MainImg";

declare global {
  interface Window {
    kakao: any;
  }
}
interface obj {
  capsuleId: string;
  latitude: string;
  longitude: string;
}
interface objAble {
  capsule_id: string;
  title: string;
  date: string;
  latitude: string;
  longitude: string;
}

export default function MainBody() {
  const [openModal, setOpenModal] = useState(false);
  const [objAble, setObjAble] = useState<objAble>();
  const [useMap, setUseMap] = useState<any>();
  const [buttonImage, setButtonImage] = useState(AddbuttonClose);

  var map: any;
  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  const handleOpenModal = () => {
    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const handleMouseEnter = () => {
    setButtonImage(AddbuttonOpen);
  };

  const handleMouseLeave = () => {
    setButtonImage(AddbuttonClose);
  };

  const handleClickGoToMapCenter = () => {
    const centerPosition = new window.kakao.maps.LatLng(
      objAble?.latitude,
      objAble?.longitude
    );
    useMap.setCenter(centerPosition);
    useMap.setLevel(7);
    setOpenModal(false);
  };

  const createCapsule = (): void => {
    window.location.href = "/create/capsule";
  };

  // 맵 생성
  useEffect(() => {
    var container = document.getElementById("map");
    var options = {
      center: new window.kakao.maps.LatLng(
        35.51180380005361,
        127.85919217477574
      ),
      level: 13,
    };

    map = new window.kakao.maps.Map(container, options);
    setUseMap(map);
    map.setMaxLevel(12);
    map.relayout();
  }, []);

  // 읽은 항목 불러오기
  useEffect(() => {
    axios
      .get(API_BASE_URL + "/api/user/read/complete/capsule", {
        headers: {
          Authorization: `${accessToken}`,
        },
      })
      .then((res) => {
        var jsonData = res.data;

        jsonData.forEach(function (obj: obj) {
          // 마커가 표시될 위치입니다

          var imageSrc = require("../../../images/complete.png");
          var imageSize = new window.kakao.maps.Size(30, 30);
          var imageOption = { offset: new window.kakao.maps.Point(2, 25) };
          var markerImage = new window.kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imageOption
          );
          var markerPosition = new window.kakao.maps.LatLng(
            obj.latitude,
            obj.longitude
          );

          // 마커를 생성합니다
          var marker = new window.kakao.maps.Marker({
            position: markerPosition,
            image: markerImage,
            xAnchor: 0.3,
            yAnchor: 0.91,
          });

          // 마커가 지도 위에 표시되도록 설정합니다
          marker.setMap(map);
          window.kakao.maps.event.addListener(marker, "click", function () {
            window.location.href = "/capsule/" + obj.capsuleId;
          });
        });
      })
      .catch(() => {
        axios
          .delete(API_BASE_URL + "/api/user/fcm", {
            headers: {
              Authorization: `${accessToken}`,
            },
          })
          .then(() => {
            window.location.href = "/login";
          })
          .catch(() => {
            window.location.href = "/login";
          });
      });
  });

  //sse 읽지 않은 항목 불러오기
  useEffect(() => {
    const EventSource = EventSourcePolyfill || NativeEventSource;

    const eventSource = new EventSource(API_BASE_URL + "/api/user/subscribe", {
      headers: {
        Authorization: `${accessToken}`,
      },
    });

    eventSource.onmessage = (event) => {
      // 이벤트 데이터 처리
      console.log(event.data === "dummy");
      if (event.data.length != 0 && event.data !== "dummy") {
        var jsonData = JSON.parse(event.data);
        console.log(jsonData);
        if (jsonData.length != 0) {
          jsonData.forEach(function (obj: objAble) {
            var imageSrc = require("../../../images/able.png");
            var imageSize = new window.kakao.maps.Size(30, 30);
            var imageOption = { offset: new window.kakao.maps.Point(2, 25) };
            var markerImage = new window.kakao.maps.MarkerImage(
              imageSrc,
              imageSize,
              imageOption
            );
            var markerPosition = new window.kakao.maps.LatLng(
              obj.latitude,
              obj.longitude
            );

            // 마커를 생성합니다
            var marker = new window.kakao.maps.Marker({
              position: markerPosition,
              image: markerImage,
              xAnchor: 0.3,
              yAnchor: 0.91,
              zIndex: 9999,
            });
            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(map);
            window.kakao.maps.event.addListener(marker, "click", function () {
              window.location.href = "/receive/" + obj.capsule_id;
            });
            setObjAble(obj);
            map.relayout();
          });
          // 모달 띄우기
          const sessionValue: string | null =
            sessionStorage.getItem("ABLE_SIZE");
          console.log(sessionValue);
          if (sessionValue !== null) {
            const numericValue: number = parseInt(sessionValue, 10); // 문자열을 숫자로 변환
            console.log(numericValue);
            if (jsonData.length > numericValue) {
              handleOpenModal();
              sessionStorage.setItem("ABLE_SIZE", jsonData.length.toString());
              console.log(sessionStorage.getItem("ABLE_SIZE"));
            } else {
              sessionStorage.setItem("ABLE_SIZE", jsonData.length.toString());
            }
          }
        }
      }
    };

    return () => {
      // 컴포넌트가 언마운트될 때 SSE 연결 해제
      eventSource.close();
    };
  }, []);

  return (
    <div className="main_body">
      <div className="main_body_content">
        <div id="map" style={{ width: "100%", height: "100%" }}></div>
        <MainImg/>
      </div>
      <Dialog open={openModal} onClose={handleCloseModal} className="modal">
        <DialogTitle>
          <div className="modal_header">
            <h2>📢📢📢알림!!</h2>
          </div>
        </DialogTitle>
        <DialogContent>
          <div className="modal_main">
            <p>{objAble?.date}에 생성한 타임캡슐</p>
            <p>
              <b>{objAble?.title}</b>
            </p>
            <p>이 공개되었습니다.</p>
            <br />
            <p>지금 당장 친구들과의 추억을 확인해보세요!</p>
          </div>
        </DialogContent>
        <DialogActions>
          <div className="modal_button">
            <div>
              <Button
                onClick={handleClickGoToMapCenter}
                style={{
                  width: "100%",
                  borderTop: "0.1px solid #9FCFFC",
                  borderRight: "0.1px solid #9FCFFC",
                  color: "black",
                }}
              >
                보러 가기
              </Button>
            </div>
            <div>
              <Button
                onClick={handleCloseModal}
                color="primary"
                style={{
                  width: "100%",
                  borderTop: "0.1px solid #9FCFFC",
                  borderLeft: "0.1px solid #9FCFFC",
                  color: "black",
                }}
              >
                나중에
              </Button>
            </div>
          </div>
        </DialogActions>
      </Dialog>
    </div>
  );
}
