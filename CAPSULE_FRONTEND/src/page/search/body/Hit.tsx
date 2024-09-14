import React, { useState, useEffect } from "react";
import axios from "axios";
import AttractionCard from "./AttractionHitCard";
import { API_BASE_URL } from "../../../api/App-config";

interface Attraction {
  favoriteId: number | null;
  title: string;
  firstimage: string;
  mapx: string;
  mapy: string;
  state: boolean;
}

const Located: React.FC = () => {
  const [places, setPlaces] = useState<Attraction[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  useEffect(() => {
    loadPlaces();
  }, []);

  const loadPlaces = async () => {
    setLoading(true);
    try {
      const response = await axios.get(API_BASE_URL + "/api/user/favorite", {
        headers: {
          Authorization: `${accessToken}`,
        },
        params: {
          page: 0,
          size: 10,
        },
      });
      setPlaces(response.data.data);
      setLoading(false);
    } catch (err) {
      const error = err as any;
      setError(error.response?.data?.message || error.message);
      setLoading(false);
    }
  };

  const updatePlace = (updatedPlace: Attraction) => {
    console.log("업데이트된 장소:", updatedPlace);

    setPlaces((prevPlaces) => {
      return prevPlaces.map((place) => {
        if (place.title === updatedPlace.title) {
          return updatedPlace;
        }
        return place;
      });
    });
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="card-container">
      <div className="card-body">
        {places.map((place, index) => (
          <AttractionCard
            key={place.favoriteId ?? index} // 고유한 키를 설정
            place={place}
            updatePlace={updatePlace}
          />
        ))}
      </div>
    </div>
  );
};

export default Located;
