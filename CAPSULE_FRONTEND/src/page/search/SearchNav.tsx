import React, { useState } from "react";
import { motion } from "framer-motion";
import "../../styles/search/searchNav.css";

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
          style={{ fontWeight: selectedTab === 0 ? 600 : "normal" , fontSize: "1vw" }}
          animate={{ color: selectedTab === 0 ? "#B4DBFF" : "black" }}
        >
          Located
        </motion.p>
        <motion.p
          onClick={() => setParent(1)}
          style={{ fontWeight: selectedTab === 1 ? 600 : "normal"  , fontSize: "1vw"}}
          animate={{ color: selectedTab === 1 ? "#B4DBFF" : "black" }}
        >
          Hit now
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
