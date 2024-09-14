import MainHeader from "../../component/header/MainHeader";
import CreateCapsuleBody from "./body/CreateCapsuleBody";

export default function CreateCapsule() {
  return (
    <div className="page">
      <div className="main_page">
        <div className="main_content">
          <MainHeader icon={"blue"} />
          <CreateCapsuleBody />
        </div>
      </div>
    </div>
  );
}
