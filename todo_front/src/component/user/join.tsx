import React, { useState, FormEvent } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
const Join: React.FC = () => {
  const [useremail, setUseremail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [nickname, setNickname] = useState<string>('');
  
  const [error, setError] = useState<string>('');

  const navigate = useNavigate();
  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        'http://localhost:8080/user/join',
        {
          useremail,
          password,
          nickname,
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      )
      .then((response)=>{
        navigate('/login')
      });
      // Handle successful signup (e.g., redirect to login page)
    } catch (err) {
      setError('회원가입 실패. 정보를 확인하세요.');
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="useremail">이메일:</label>
          <input
            type="email"
            id="useremail"
            value={useremail}
            onChange={(e) => setUseremail(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">비밀번호:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="nickname">닉네임:</label>
          <input
            type="text"
            id="nickname"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
            required
          />
        </div>
        <button type="submit">회원가입</button>
        {error && <p>{error}</p>}
      </form>
    </div>
  );
};

export default Join;
