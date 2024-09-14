import "../../../styles/request/requestBody.css";
import ReceiveRequest from "./ReceiveRequest";
import SendRequest from "./SendRequest";
export default function RequestBody() {
  return(
    <div className="request_body">
        <ReceiveRequest/>
        <SendRequest/>
        
    </div>
  );
}
