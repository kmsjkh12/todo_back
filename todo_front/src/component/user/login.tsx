import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "./login.css"

const Login: React.FC = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string>('');

  const navigate = useNavigate();
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  
    const formData = new FormData();
    formData.append('username', email);
    formData.append('password', password);
    try {
      const response = await axios.post(
        '/user/login',
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
          withCredentials: true, // Include credentials (cookies) in the request
        }
      )
      .then((response)=>{
        if(response.status === 200){
          navigate("/")
        }
      })
    } catch (error) {
      console.error(error); // Handle the error
      setError('Login failed. Please check your email and password.');
    }
  };
  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

       
        <button type="submit">Login</button>
        <button type='button' onClick={()=>{
              navigate("/join")
            }}> join</button>
        <div>
          test id <br />
          id : test1@test.com < br/>
        password: 1234</div>
        {error && <p>{error}</p>}
      </form>
    </div>
  );
};

export default Login;
