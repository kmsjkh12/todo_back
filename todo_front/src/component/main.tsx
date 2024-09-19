import React, { useState, useEffect } from 'react';
import TodoList from './todo/todo';
import axios from 'axios';
import TeamTodoList from './todo/teamTodo';
import { useNavigate } from 'react-router-dom';
import { Cookies } from 'react-cookie';
import './main.css'

interface Team {
  teamid: string;
  teamname: string;
}

const App: React.FC = () => {
  const [selectedView, setSelectedView] = useState('');
  const [teams, setTeams] = useState<Team[]>([]);
  const [choice, setChoice] = useState<Team | null>(null);
  const [teamidR, setTeamidR] = useState("");

  const cookie = new Cookies();
  const navigate = useNavigate();

  useEffect(() => {
    const token = cookie.get('jwt');

    if (!token) {
      navigate('/login');
    }
  }, [cookie, navigate]);

  useEffect(() => {
    const fetchTeams = async () => {
      try {
        const response = await axios.get('/team/my', { withCredentials: true });
        setTeams(response.data.teamTodoResponseDtoList);
        console.log(response);
      } catch (error) {
        console.error(error);
      }
    };

    fetchTeams();
  }, []);

  const registerTeams = async () => {
    try {
      const response = await axios.post('/team/add', {
        teamid: teamidR
      },
      { withCredentials: true });
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div style={{ display: 'flex', height: '100vh' }}>
      <aside>
        <h3>Menu</h3>
        <ul>
          {teams.map((t) => (
            <li
              key={t.teamid}
              onClick={() => {
                setSelectedView(t.teamname);
                setChoice(t);
              }}
              style={styles.menuItem}
            >
              {t.teamname}
            </li>
            
          ))}
         <li
              onClick={() => {
                setSelectedView('my todo list');
              }}
              style={styles.menuItem}
            >
              my todo list
            </li>
          <li>
            <input type='text' value={teamidR} onChange={(e)=>{
              setTeamidR(e.target.value)
            }}/>
          <button type='button'
            onClick={()=>{
              registerTeams()
            }}
          >팀 가입하기</button>
              
          </li>
        </ul>
      </aside>
      <main style={styles.mainContent}>
        <h2>{selectedView}</h2>
        {selectedView !== 'my todo list' && choice !== null && (
          <TeamTodoList teamid={choice.teamid} teamname={choice.teamname} />
        )}
        {selectedView === 'my todo list' && <TodoList />}

      </main>
    </div>
  );
};

const styles = {
  sidebar: {
    width: '200px',
    background: '#f0f0f0',
    padding: '10px',
    borderRight: '1px solid #ddd',
    display: 'flex',
    flexDirection: 'column',
  },
  menuItem: {
    cursor: 'pointer',
    padding: '10px',
    borderRadius: '4px',
    margin: '5px 0',
    transition: 'background-color 0.3s',
  },
  menuItemHover: {
    backgroundColor: '#ddd',
  },
  mainContent: {
    flex: 1,
    padding: '20px',
  },
};

export default App;
