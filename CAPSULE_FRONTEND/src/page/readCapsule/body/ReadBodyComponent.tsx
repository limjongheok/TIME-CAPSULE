import "../../../styles/read/readBody.css";
interface Props {
  name: string;
  memo: string;
  imgUrl: string;
}

const ReadBodyComponent: React.FC<Props> = ({ memo, imgUrl }) => {
  // memo를 부모 요소의 너비에 맞게 나누는 함수
  const splitMemo = (memo: string): string[] => {
    return memo.split("\n"); // 개행 문자를 기준으로 나눔
  };

  return (
    <div className="read_body_component">
      {splitMemo(memo).map((paragraph, index) => (
        <p key={index} style={{ fontSize: "1vw" }}>{paragraph}</p>
      ))}
      {imgUrl !== null && (
        <div className="image-container">
          <img src={imgUrl} alt="이미지" className="centered-image" />
        </div>
      )}
    </div>
  );
};

export default ReadBodyComponent;