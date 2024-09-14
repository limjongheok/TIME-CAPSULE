import MainHeader from "../../component/header/MainHeader";
import SearchHeader from "../../component/header/InfoHeader";
import SendRequest from "./body/SendRequest";
import RequestBody from "./body/RequestBody";

export default function RequestPage() {
  return (
    <div className="page">
      <div className="main_page">
        <div className="main_content">
          <SearchHeader />
          <RequestBody/>
        </div>
      </div>
    </div>
  );
}
