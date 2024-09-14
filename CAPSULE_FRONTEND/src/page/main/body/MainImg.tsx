import "../../../styles/main/mainBody.css";
import AddbuttonOpen from "../../../images/capsule_open.svg";
import AddbuttonClose from "../../../images/capsule_close.svg";
import { useState } from "react";

export default function MainImg() {


    const [buttonImage, setButtonImage] = useState(AddbuttonClose);
    
    const createCapsule = (): void => {
        window.location.href = "/create/capsule";
      };
    
      const handleMouseEnter = () => {
        setButtonImage(AddbuttonOpen);
      };
    
      const handleMouseLeave = () => {
        setButtonImage(AddbuttonClose);
      };
    return(
        <div className="main_img">
            <img
          src={buttonImage}
          alt="button"
          onClick={createCapsule}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
          className="animated-button"
        />
        </div>
    )

}