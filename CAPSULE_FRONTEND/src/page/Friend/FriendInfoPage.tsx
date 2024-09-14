import { useState } from "react";
import { useParams } from "react-router";
import SearchHeader from "../../component/header/InfoHeader";
import InfoNav from "./InfoNav";
import FriendInfo from "./FriendInfo";
import Friend from "./body/friend/Friend";
import FriendLike from "./body/friendLike/FriendLike";

interface FriendInfo {
  email: string;
  name: string;
  imgUrl: string;
  phoneNumber: string;
}

export default function FriendInfoPage() {
  const params = useParams();
  const email:string = params.email as string;

  const [active, setActive] = useState(0);
  const childData = (data: number) => {
    setActive(data);
  };

  const [friendInfo, setFriendInfo] = useState<FriendInfo | undefined>(undefined);

  const updateFriendInfo = (info: FriendInfo) => {
    setFriendInfo(info);
    console.log("인포 페이지 데이터 확인 : ", info);
  };

  return (
    <div className="page">
      <div className="main_page">
        <div className="main_content">
          <SearchHeader />
          <FriendInfo getFriendInfo={friendInfo} email={email} updateFriendInfo={updateFriendInfo} />
          <InfoNav onData={childData} />
          {active === 0 && <FriendLike friendInfo={friendInfo} />}
          {active === 1 && <Friend email={email} />}
        </div>
      </div>
    </div>
  );
}
