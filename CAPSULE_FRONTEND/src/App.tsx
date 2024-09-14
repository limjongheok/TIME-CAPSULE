import { BrowserRouter } from "react-router-dom";
import UserRouter from "./router/UserRouter";
import firebase from "firebase/app";
import "firebase/messaging";
import "firebase/analytics";
import "./index.css";

const firebaseConfig = {
  apiKey: "AIzaSyAq7NYmtgnD4viYVvFmrhszkL_o2SIImtA",
  authDomain: "talktalk-a7e28.firebaseapp.com",
  projectId: "talktalk-a7e28",
  storageBucket: "talktalk-a7e28.appspot.com",
  messagingSenderId: "532620406105",
  appId: "1:532620406105:web:775e23e14e8aadd901f048",
  measurementId: "G-RY5BXMHN7F",
};
function App() {
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
  }
  // Retrieve an instance of Firebase Messaging so that it can handle background
  // messages.

  return (
    <BrowserRouter>
      <UserRouter />
    </BrowserRouter>
  );
}

export default App;
