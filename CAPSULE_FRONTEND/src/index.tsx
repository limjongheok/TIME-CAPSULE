import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import Modal from "react-modal";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

// Modal의 앱 요소를 설정합니다.
Modal.setAppElement("#root");

root.render(
  //<React.StrictMode>
  <App />
  //</React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
