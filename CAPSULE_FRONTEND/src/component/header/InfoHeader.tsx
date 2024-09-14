import Icon from "../../images/capsule_blue.svg";
import Info from "../../images/onInfo.svg";
import Main from "../../images/main.svg";
import Search from "../../images/search.svg";
import "../../styles/header/header.css";
import TalkTalk from "../../images/talk.svg";

const InfoHeader: React.FC = () => {
  const goMain = (): void => {
    window.location.href = "/main";
  };
  const goInfo = (): void => {
    window.location.href = "/info";
  };
  const goSearch = (): void => {
    window.location.href = "/search";
  };
  return (
    <div className="main_header">
      <div className="main_header_icon" onClick={goMain}>
        <img src={Icon} alt="icon" />
      </div>
      <div className="talktalk">
        <img src={TalkTalk} alt="talk" />
      </div>
      <div className="main_header_nav">
        <div className="icon-container" onClick={goInfo}>
          <img src={Info} alt="info" />
          <span className="icon-text">Info</span>
        </div>
        <div className="icon-container" onClick={goMain}>
          <img src={Main} alt="main" />
          <span className="icon-text">Main</span>
        </div>
        <div className="icon-container" onClick={goSearch}>
          <img src={Search} alt="search" />
          <span className="icon-text">Search</span>
        </div>
      </div>
    </div>
  );
};
export default InfoHeader;
