import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

interface Todo {
    id: string;
    teamid: string;
    complete: string;
    content: string;
    createAt: string;
    author: string;
}

interface TeamTodo {
    teamid: string;
    setTodos: React.Dispatch<React.SetStateAction<Todo[]>>;
}

const TeamTodoCreate: React.FC<TeamTodo> = ({ teamid, setTodos }) => {
    const [text, setText] = useState<string>(''); // State to hold the input value
    const [error, setError] = useState<string>(''); // State to hold error messages

    const handleSendMessage = async (event: React.FormEvent) => {

        if (text.trim()) {
            try {
                // Making POST request to add todo item
                const response = await axios.post('/team/todo', {
                    teamid: teamid,
                    content: text
                },
                {
                    headers: {
                        "Content-Type": "application/json"
                    },
                    withCredentials: true
                });

                if (response.status === 200) {
                    // Handle success
                    const newTodo: Todo = response.data; // Assuming the server responds with the newly created todo
                    console.log('Todo added successfully:', newTodo);
                    alert("삭제 완료입니다.")
                    // Update todos in the parent component

                    // Clear input and error
                    setText('');
                    setError('');
                } else {
                    alert("삭제 실패입니다.")

                    console.error('Failed to add todo:', response.statusText);
                }
            } catch (error) {
                console.error('Error adding todo:', error);
                setError('Failed to add todo.');
            }
        }
    };

    return (
        <div style={styles.container}>
            <h3>Create Todo</h3>
            <form onSubmit={handleSendMessage}>
                <input
                    type="text"
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    placeholder="Enter todo text"
                    style={styles.input}
                />
                <button type="submit" style={styles.button}>Add Todo</button>

            </form>
            {error && <p style={styles.error}>{error}</p>}
        </div>
    );
};

// Define basic styles for the component
const styles: { [key: string]: React.CSSProperties } = {
    container: {
        padding: '20px',
        border: '1px solid #ddd',
        borderRadius: '4px',
        backgroundColor: '#f9f9f9',
    },
    input: {
        padding: '8px',
        marginRight: '10px',
        border: '1px solid #ddd',
        borderRadius: '4px',
    },
    button: {
        padding: '8px 16px',
        border: 'none',
        borderRadius: '4px',
        backgroundColor: '#007bff',
        color: '#fff',
        cursor: 'pointer',
    },
    error: {
        color: 'red',
        marginTop: '10px',
    },
};

export default TeamTodoCreate;
