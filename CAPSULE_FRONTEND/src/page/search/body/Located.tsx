import React, { useState, useEffect, useRef, useCallback } from "react";
import axios from "axios";
import AttractionCard from "./AttractionLocatedCard";
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
  const [location, setLocation] = useState<{
    mapx: number | null;
    mapy: number | null;
  }>({ mapx: null, mapy: null });
  const [page, setPage] = useState(1); // 페이지 번호
  const [hasMore, setHasMore] = useState(true); // 추가 데이터 여부

  const observer = useRef<IntersectionObserver | null>(null);

  const accessToken = localStorage.getItem("ACCESS_TOKEN");

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          setLocation({ mapx: longitude, mapy: latitude });
        },
        (error) => {
          setError(error.message);
          setLoading(false);
        }
      );
    } else {
      setError("Geolocation is not supported by this browser.");
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (location.mapx !== null && location.mapy !== null) {
      setPage(1);
      setHasMore(true);
      setPlaces([]);
      loadPlaces(1);
    }
  }, [location]);

  useEffect(() => {
    if (page > 0 && location.mapx !== null && location.mapy !== null) {
      loadPlaces(page);
    }
  }, [page]);

  const loadPlaces = async (page: number) => {
    setLoading(true);
    try {
      const response = await axios.get(API_BASE_URL + "/api/user/place", {
        headers: {
          Authorization: `${accessToken}`,
        },
        params: {
          mapX: location.mapx,
          mapY: location.mapy,
          page: page,
          size: 10,
        },
      });

      const newPlaces = response.data.data;

      if (Array.isArray(newPlaces) && newPlaces.length > 0) {
        setPlaces((prevPlaces) => [...prevPlaces, ...newPlaces]); // 초기 로딩과 추가 로딩을 구분
      } else {
        setHasMore(false); // 더 이상 데이터가 없음을 설정
      }

      setLoading(false);
    } catch (err) {
      const error = err as any;
      setError(error.response?.data?.message || error.message);
      setLoading(false);
    }
  };

  const lastCardElementRef = useCallback(
    (node: HTMLDivElement) => {
      if (loading) return;
      if (observer.current) observer.current.disconnect();
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setPage((prevPage) => prevPage + 1);
        }
      });
      if (node) observer.current.observe(node);
    },
    [loading, hasMore]
  );

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

  if (loading && page === 0) {
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
            key={`${place.favoriteId ?? 'no-id'}-${index}`} // 고유한 키를 설정
            place={place}
            updatePlace={updatePlace}
          />
        ))}
        <div ref={lastCardElementRef} />
      </div>
      {loading && page > 0 && <div>Loading more...</div>}
    </div>
  );
};

export default Located;
