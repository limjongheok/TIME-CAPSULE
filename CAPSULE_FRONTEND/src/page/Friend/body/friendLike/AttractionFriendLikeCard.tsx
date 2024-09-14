import React, { useState } from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardActions from "@mui/material/CardActions";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import NearMeIcon from "@mui/icons-material/NearMe";
import Modal from "react-modal";
import axios from "axios";
import { API_BASE_URL } from "../../../../api/App-config";
import "../../../../styles/search/AttractionCard.css";
import NOIMAGE from "../../../../images/noimage.jpg";

interface Attraction {
  favoriteId: number | null;
  title: string;
  firstimage: string;
  mapx: string;
  mapy: string;
  state: boolean;
}

interface MapPos {
  mapX: number;
  mapY: number;
}

interface Props {
  place: Attraction;
  updatePlace: (place: Attraction) => void;
}

const AttractionCard: React.FC<Props> = ({ place, updatePlace }) => {
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [isFavorite, setIsFavorite] = useState(place.state); // 상태를 로컬 상태로 관리
  const [favoriteId, setFavorite] = useState(place.favoriteId);

  const handleMap = (mapPos: MapPos) => {
    window.open(
      `https://map.kakao.com/link/search/${mapPos.mapY},${mapPos.mapX}`
    );
  };

  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  return (
    <div>
      <Card className="card" sx={{ maxWidth: "100%" }}>
        <CardHeader
          action={
            <IconButton
              aria-label="settings"
              style={{ color: "#CAE6FF" }}
              onClick={() =>
                handleMap({
                  mapX: parseFloat(place.mapx),
                  mapY: parseFloat(place.mapy),
                })
              }
            >
              <NearMeIcon />
            </IconButton>
          }
          title={<span className="card-title">{place.title}</span>}
        />
        <CardMedia
          component="img"
          height="194"
          image={
            place.firstimage.split(":")[0] === "http"
              ? place.firstimage
              : NOIMAGE
          }
          alt={place.title}
          onClick={() => setModalIsOpen(true)}
          style={{ cursor: "pointer" }}
        />
      </Card>

      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        className="ReactModal__Content"
        overlayClassName="ReactModal__Overlay"
      >
        <div className="modal-header">
          <h2>{place.title}</h2>
          <button onClick={() => setModalIsOpen(false)}>&times;</button>
        </div>
        <div className="modal-body">
          <img
            src={
              place.firstimage.split(":")[0] === "http"
                ? place.firstimage
                : NOIMAGE
            }
            alt={place.title}
          />
        </div>
      </Modal>
    </div>
  );
};

export default AttractionCard;
