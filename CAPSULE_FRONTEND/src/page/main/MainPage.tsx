import React, { useEffect, useState } from 'react';
import MainBody from './body/MainBody';
import MainHeader from '../../component/header/MainHeader';
import '../../styles/main/mainPage.css';
import { getToken } from '../../util/firebase';
import axios from 'axios';
import { API_BASE_URL } from '../../api/App-config';
import ChatWindow from './chatbot/ChatWindow'; // ChatWindow 컴포넌트 추가
import ChatbotImage from '../../images/chatbot.png'


export default function MainPage() {
  const [showChat, setShowChat] = useState(false);

  sessionStorage.setItem('ABLE_SIZE', '0');
  const accessToken = localStorage.getItem('ACCESS_TOKEN');

  useEffect(() => {
    const requestNotificationPermission = async () => {
      try {
        const permission = await Notification.requestPermission();
        if (permission === 'granted') {
          console.log('알림 권한이 허용됨');
          await fetchAndSendToken();
        } else {
          console.log('알림 권한 허용 안됨');
        }
      } catch (error) {
        console.error('알림 권한 요청 중 에러 발생:', error);
      }
    };

    const fetchAndSendToken = async () => {
      try {
        const token = await getToken();
        console.log(token);

        const response = await axios.post(`${API_BASE_URL}/api/user/fcm`, { token }, {
          headers: {
            Authorization: `${accessToken}`,
          },
        });

        console.log('Server response:', response.data);
      } catch (error) {
        console.error('Error fetching FCM token or sending to server:', error);
      }
    };

    requestNotificationPermission();
  }, [accessToken]);

  return (
    <div className="page">
      <div className="main_page">
        <div className="main_content">
          <MainHeader icon="blue" />
          <MainBody />
        </div>
      </div>
      <button 
        className="chat-button" 
        onClick={() => setShowChat(!showChat)}
      >
        <img src={ChatbotImage} alt="Chat" />
      </button>
      {showChat && <ChatWindow onClose={() => setShowChat(false)} />}
    </div>
  );
}
