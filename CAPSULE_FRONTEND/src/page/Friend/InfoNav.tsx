import React, { useState } from "react";
import { motion } from "framer-motion";
import "../../styles/info/infoNav.css";

interface AutomationOverviewSelectProps {
  onData: (value: number) => void;
}

const InfoNav: React.FC<AutomationOverviewSelectProps> = (props) => {
  const [selectedTab, setSelectedTab] = useState<number>(0);

  const setParent = (v: number) => {
    props.onData(v);
    setSelectedTab(v);
  };

  return (
    <div className="automationOverviewSelects">
      <div className="aselects">
        <motion.p
          onClick={() => setParent(0)}
          style={{ fontWeight: selectedTab === 0 ? 600 : "normal" }}
          animate={{ color: selectedTab === 0 ? "#B4DBFF" : "black" }}
        >
          Liked
        </motion.p>
        <motion.p
          onClick={() => setParent(1)}
          style={{ fontWeight: selectedTab === 1 ? 600 : "normal" }}
          animate={{ color: selectedTab === 1 ? "#B4DBFF" : "black" }}
        >
          Friends
        </motion.p>
      </div>
      <div className="aselecteBar">
        <motion.div
          id="bar"
          className="automationOverviewbar"
          initial={{ width: "50%", left: `${selectedTab * 50}%` }}
          animate={{ width: "50%", left: `${selectedTab * 50}%` }}
        ></motion.div>
      </div>
    </div>
  );
};

export default InfoNav;
