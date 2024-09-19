import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import TodoCreate from './todoCreate';
import './todo.css'

export interface Todo {
    id: number;
    complete: string;
    content: string;
    createAt: string; // Assuming it's a date string (e.g., "2023-09-08T12:34:56Z")
}

const TodoList: React.FC = () => {
  const [todos, setTodos] = useState<Todo[]>([]);  // State to store the list of todos
  const [error, setError] = useState<string>('');  // State to handle any errors during fetch
  const [editingTodoId, setEditingTodoId] = useState<number | null>(null);
  const [newContent, setNewContent] = useState<string>('');
  const [add, setAdd] = useState<boolean>(false);
  const navigate = useNavigate();


  // Fetch the todos from the backend when the component mounts
  useEffect(() => {
    const fetchTodos = async () => {
      try {
        const response = await axios.get('/todos', { withCredentials: true });
        setTodos(response.data.todoResponseDto);
      } catch (error) {
        setError('Failed to fetch todos.');
      }
    };

    fetchTodos();
  }, []);

  const handleEditContent = (todoId: number, content: string) => {
    setEditingTodoId(todoId);
    setNewContent(content);
  };

  const handleUpdateContent = async (todoId: number) => {
    try {
      await axios.patch(`/todos/edit`, { 
        todoid: todoId,
        content: newContent 
      }, { withCredentials: true })
      .then((response) => {
        if(response.status === 200) {
          alert("수정 완료입니다.")

          setTodos(todos.map(todo => 
            todo.id === todoId ? { ...todo, content: newContent } : todo
          ));
          setEditingTodoId(null);
          setNewContent('');
        }
      });
    } catch (error) {
      alert("수정 실패입니다.")

      setError('Failed to update todo.');
    }
  };

  const handleToggleComplete = async (todoId: number, currentStatus: string) => {
    try {
      await axios.patch(`/todos/complete`, { todoid: todoId }, { withCredentials: true })
      .then((response) => {
        if(response.status === 200) {
          alert("완료입니다.")

          setTodos(todos.map(todo => 
            todo.id === todoId ? { ...todo, complete: currentStatus === 'COMPLETE' ? 'INCOMPLETE' : 'COMPLETE' } : todo
          ));
        }
      });
    } catch (error) {
      setError('Failed to toggle todo completion.');
    }
  };

  const handleDeleteTodo = async (todoId: number) => {
    try {
        await axios.delete(`/todos?todoid=${todoId}`, { withCredentials: true })

      .then((response) => {
        if(response.status === 200) {
          alert("삭제 완료입니다.")

          setTodos(todos.filter(todo => todo.id !== todoId));
        }
      });
    } catch (error) {
      alert("삭제 실패입니다.")

      setError('Failed to delete todo.');
    }
  };

  return (
    <div>
      {error && <p>{error}</p>}
      <button type='button' onClick={()=>{
        setAdd(!add);
      }}> todo 생성</button>
      {
            add && <TodoCreate />
      }
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
            <div className="todo-actions">
              <button onClick={() => handleUpdateContent(todo.id)}>Save</button>
            </div>
          </>
        ) : (
          <>
            {todo.content}
            <div className="todo-actions">
              <button onClick={() => handleEditContent(todo.id, todo.content)}>Edit</button>
            </div>
          </>
        )}
      </div>
      <div className="todo-complete">
        <strong>Complete:</strong> 
        {todo.complete === "COMPLETE" ? 'Yes' : 'No'}
        <button onClick={() => handleToggleComplete(todo.id, todo.complete)}>
          Toggle Complete
        </button>
      </div>
      <div>
        <strong>Created At:</strong> {todo.createAt}
      </div>
      <div className="todo-actions">
        <button className="delete-btn" onClick={() => handleDeleteTodo(todo.id)}>Delete</button>
      </div>
    </li>
  ))}
</ul>

    </div>
  );
};

export default TodoList;
