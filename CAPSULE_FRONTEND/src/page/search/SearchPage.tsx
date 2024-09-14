import { useState } from "react";
import SearchHeader from "../../component/header/SearchHeader";
import InfoNav from "./SearchNav";
import "../../styles/search/AttractionCard.css";
import "../../styles/search/AttractionComponent.css";
import Located from "./body/Located";
import Hit from "./body/Hit";

export default function InfoPage() {
  const [active, setActive] = useState(0);
  const childData = (data: number) => {
    setActive(data);
  };

  return (
    <div className="page">
      <div className="main_page">
        <div className="main_content">
          <div className="stickyHeader"></div>
          <SearchHeader />
          <InfoNav onData={childData} />
          <div className="stickyContent"></div>
          {active === 0 && <Located />}
          {active === 1 && <Hit />}
        </div>
      </div>
    </div>
  );
}
