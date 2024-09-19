import React, { useEffect, useState } from 'react';
import axios from 'axios';
import WebSocketChat from '../socket/socket'; // Adjust the import path as needed
import TeamTodoCreate from './teamTodoCreate';

export interface Todo {
    id: string;
    teamid: string;
    complete: string;
    content: string;
    createAt: string;
    author: string;
}

interface TeamTodoListProps {
    teamid: string; // Define `teamid` as a prop
    teamname: string;
}

const TeamTodoList: React.FC<TeamTodoListProps> = (props: TeamTodoListProps) => {
    const { teamid, teamname } = props;
    const [todos, setTodos] = useState<Todo[]>([]);
    const [error, setError] = useState<string>('');
    const [chat, setChat] = useState<boolean>(false);
    const [add, setAdd] = useState<boolean>(false);
    const [editingTodoId, setEditingTodoId] = useState<string | null>(null);
    const [newContent, setNewContent] = useState<string>('');

    useEffect(() => {
        // Fetch the list of todos
        const fetchTodos = async () => {
            try {
                const response = await axios.get(`/team/todo?teamid=${teamid}`, { withCredentials: true });
                setTodos(response.data.teamTodoResponseDtoList);
            } catch (error) {
                setError('Failed to fetch todos.');
            }
        };

        fetchTodos();
    }, [teamid]);

    const handleEditContent = (todoId: string, content: string) => {
        setEditingTodoId(todoId);
        setNewContent(content);
    };

    const handleUpdateContent = async (todoId: string) => {
        try {
            await axios.patch('/team/todo/edit', {
                todoid: todoId,
                teamid: teamid,
                content: newContent
            }, { withCredentials: true })
            .then((response)=>{
                if(response.status === 200){
                    alert("수정 완료입니다.")

                    setTodos(todos.map(todo =>
                        todo.id === todoId ? { ...todo, content: newContent } : todo
                    ));
                    setEditingTodoId(null);
                    setNewContent('');
                }
            });

            // Update todos in the state
           
        } catch (error) {
            alert("수정 실패입니다.")

            setError('Failed to update todo.');
        }
    };

    const handleToggleComplete = async (todoId: string, currentStatus: string) => {
        try {
            await axios.patch('/team/todo/complete', {
                todoid: todoId,
                teamid: teamid
            }, { withCredentials: true })
            .then((response)=>{
                console.log(response);
                if(response.status=== 200){
                    alert("완료입니다.")

                    setTodos(todos.map(todo =>
                        todo.id === todoId ? { ...todo, complete: currentStatus === 'COMPLETE' ? 'INCOMPLETE' : 'COMPLETE' } : todo
                    ));
                }
            });

            // Update todos in the state
           
        } catch (error) {
            setError('Failed to toggle todo completion.');
        }
    };

    const handleDeleteTodo = async (todoId: string) => {
        try {
            await axios.delete(`/team/todos?teamid=${teamid}&todoid=${todoId}`, { withCredentials: true })
            .then((response)=>{
                if(response.status === 200){
                    alert("삭제 완료입니다.")

                    setTodos(todos.filter(todo => todo.id !== todoId));

                }
            });

            // Remove deleted todo from the state
        } catch (error) {
            setError('Failed to delete todo.');
        }
    };

  

    return (
        <div>
            <button type='button' onClick={() => setAdd(!add)}>
                Team Todo Create
            </button>
            {add && <TeamTodoCreate teamid={teamid} setTodos={setTodos} />}

            {
                todos.length === 0 && !error ? "" :  <>{error && <p>{error}</p>}
                {todos.length > 0 ? (
                    <ul>
                        {todos.map((todo) => (
                            <li key={todo.id}>
                                <div>
                                    <strong>ID:</strong> {todo.id}
                                </div>
                                <div>
                                    <strong>Content:</strong> 
                                    {editingTodoId === todo.id ? (
                                        <>
                                            <input
                                                type="text"
                                                value={newContent}
                                                onChange={(e) => setNewContent(e.target.value)}
                                            />
                                            <button onClick={() => handleUpdateContent(todo.id)}>Save</button>
                                        </>
                                    ) : (
                                        <>
                                            {todo.content}
                                            <button onClick={() => handleEditContent(todo.id, todo.content)}>Edit</button>
                                        </>
                                    )}
                                </div>
                                <div>
                                    <strong>Complete:</strong> 
                                    {todo.complete === 'COMPLETE' ? 'Yes' : 'No'}
                                    <button onClick={() => handleToggleComplete(todo.id, todo.complete)}>
                                        Toggle Complete
                                    </button>
                                </div>
                                <div>
                                    <strong>Created At:</strong> {todo.createAt}
                                </div>
                                <button onClick={() => handleDeleteTodo(todo.id)}>Delete</button>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No todos available.</p>
                )
                }</>
            }
           
            <button type='button' onClick={() => setChat(!chat)}>
                Chat Open
            </button>
            {chat && <WebSocketChat teamId={teamid} chat={chat} setChat={setChat} />}
        </div>
    );
};

export default TeamTodoList;
