import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './component/user/login';
import Join from './component/user/join';
import TodoList from './component/todo/todo';
import App from './component/main';
import TodoCreate from './component/todo/todoCreate';
import { useEffect } from 'react';
import {Cookies} from 'react-cookie';

const AppRouter = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/todo" element={<TodoList />} />
        <Route path="/todo/add" element={<TodoCreate />} />
        <Route path="/" element={<App />} />
      </Routes>
    </Router>
  );
};

export default AppRouter;
