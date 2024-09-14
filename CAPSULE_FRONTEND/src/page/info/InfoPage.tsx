import { useState } from "react";
import SearchHeader from "../../component/header/InfoHeader";
import InfoNav from "./InfoNav";
import MyInfo from "./MyInfo";
import MyFriend from "./body/friend/MyFriend";
import FindMembers from "./body/find/FindMembers";
import MyLike from "./body/myLike/MyLike";

interface Info {
  email: string;
  name: string;
  imgUrl: string;
  phoneNumber: string;
}

export default function InfoPage() {
  const [active, setActive] = useState(0);
  const childData = (data: number) => {
    setActive(data);
  };

  const [myInfo, setMyInfo] = useState<Info | undefined>(undefined);

  const updateMyInfo = (info: Info) => {
    setMyInfo(info);
    console.log("인포 페이지 데이터 확인 : ", info);
  };

  return (
    <div className="page">
      <div className="main_page">
        <div className="main_content">
          <SearchHeader />
          <MyInfo getMyInfo={myInfo} updateMyInfo={updateMyInfo} />
          <InfoNav onData={childData} />
          {active === 0 && <MyLike myInfo={myInfo} />}
          {active === 1 && <MyFriend />}
          {active === 2 && <FindMembers />}
        </div>
      </div>
    </div>
  );
}
