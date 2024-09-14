import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import "../../../styles/main/chatWindow.css"; // 스타일 파일

const ChatWindow: React.FC<{ onClose: () => void }> = ({ onClose }) => {
  const [messages, setMessages] = useState<{ role: string; content: string }[]>(
    [
      {
        role: "assistant",
        content:
          "안녕하세요!\n여행장소 추천봇, 톡톡봇입니다. \n무엇을 도와드릴까요?",
      },
    ]
  );
  const [input, setInput] = useState("");
  const [loading, setLoading] = useState(false); // 로딩 상태 추가
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const sendMessage = async () => {
    if (input.trim() === "") return;

    const userMessage = { role: "user", content: input };
    setMessages([...messages, userMessage]);
    setLoading(true); // 로딩 시작

    setInput("");
    try {
      const response = await axios.post(
        "https://api.openai.com/v1/chat/completions",
        {
          model: "gpt-3.5-turbo",
          messages: [...messages, userMessage].map((msg) => ({
            role: msg.role,
            content: msg.content,
          })),
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${process.env.REACT_APP_CHATGPT_API_KEY}`,
          },
        }
      );

      const botMessage = response.data.choices[0].message;
      setMessages([
        ...messages,
        userMessage,
        { role: "assistant", content: botMessage.content },
      ]);
    } catch (err) {
      const error = err as any;
      console.error("Error sending message:", error);
      console.error("Response data:", error.response?.data); // 에러 응답 데이터 출력
    }

    setLoading(false); // 로딩 종료
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const formatContent = (content: string) => {
    return content.split("\n").map((str, index) => (
      <React.Fragment key={index}>
        {str}
        <br />
      </React.Fragment>
    ));
  };

  return (
    <div className="chat-window">
      <div className="chat-header">
        <span>톡톡봇</span>
        <button className="chat-close-button" onClick={onClose}>
          나가기
        </button>
      </div>
      <div className="chat-messages">
        {messages.map((msg, index) => (
          <div key={index} className={`chat-message ${msg.role}`}>
            <div className="chat-bubble">{formatContent(msg.content)}</div>
          </div>
        ))}
        {loading && <div className="loading-bar">Loading...</div>}
        <div ref={messagesEndRef} />
      </div>
      <div className="chat-input">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyPress={(e) => e.key === "Enter" && sendMessage()}
          placeholder="Type a message..."
        />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
};

export default ChatWindow;
