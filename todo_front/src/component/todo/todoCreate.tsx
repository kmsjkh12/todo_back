import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
// Define the type for the Todo item
interface Todo {
    id: number;
    text: string;
    completed: boolean;
}


const TodoCreate: React.FC =() => {
    const [text, setText] = useState<string>(''); // State to hold the input value
    const [error, setError] = useState<string>(''); // State to hold error messages

    const navigate = useNavigate();
    // Handle form submission
  

    const handleSendMessage = async (event: React.FormEvent) => {
        if (text.trim()) {
            try {
                // Making POST request to add todo item
                const response = await axios.post('/todos', {
                    content: text
                },
                {
                    headers:{
                        "Content-Type":"application/json"
                    },
                    withCredentials:true
                });

                if (response.status === 200) {
                    // Handle success
                    alert("생성 완료입니다.")
                    console.log('Todo added successfully:', response.data);
                    navigate("/")
                    setText('');
        setError('');
                } else {
                    alert("생성 실패입니다.")

                    console.error('Failed to add todo:', response.statusText);
                }
            } catch (error) {
                console.error('Error adding todo:', error);
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

export default TodoCreate;
