import Icon from "../../images/capsule_blue.svg";
import WhiteIcon from "../../images/capsule.svg";
import WhiteMain from "../../images/whiteMain.svg";
import Info from "../../images/info.svg";
import Main from "../../images/onMain.svg";
import Search from "../../images/search.svg";
import TalkTalk from "../../images/talk.svg";

import "../../styles/header/header.css";
interface Props {
  icon: string;
}
const MainHeader: React.FC<Props> = ({ icon }) => {
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
        {icon == "blue" ? (
          <img src={Icon} alt="icon" />
        ) : (
          <img src={WhiteIcon} alt="icon" />
        )}
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
          {icon == "blue" ? (
            <img src={Main} alt="main" onClick={goMain} />
          ) : (
            <img src={WhiteMain} alt="main" onClick={goMain} />
          )}
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
export default MainHeader;
