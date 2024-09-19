package com.example.todo_back.service;

import com.example.todo_back.dto.todo.request.TodoAddRequestDto;
import com.example.todo_back.dto.todo.request.TodoCompleteRequestDto;
import com.example.todo_back.dto.todo.request.TodoEditRequestDto;
import com.example.todo_back.dto.todo.response.TodoResponseDto;
import com.example.todo_back.entity.Todo;
import com.example.todo_back.exception.ResourceNotFoundException;
import com.example.todo_back.mapper.TodoMapper;
import com.example.todo_back.repository.TodoRepository;
import com.example.todo_back.type.CompleteState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;
    private final UserService userService;


    public TodoServiceImpl(TodoRepository todoRepository, UserService userService){
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    //userid를 통해 조회
    @Override
    public List<TodoResponseDto> getTodoAll (Long userid){
        System.out.println("userid " + userid);
        userService.existsById(userid);

        List<Todo> todoList = todoRepository.findByUserid(userid);

        return TodoMapper.mapToDtoList(todoList);
    }

    @Override
    public List<TodoResponseDto> getTodoDate(Long userid, String date) {
        userService.existsById(userid);

        List<Todo> todoList = todoRepository.findByCreateAtAndUserid(LocalDate.parse(date),userid);

        return TodoMapper.mapToDtoList(todoList);
    }

    @Override
    public List<TodoResponseDto> getTodoComplete(Long userid, String complete) {
        userService.existsById(userid);

        List<Todo> todoList = todoRepository.findByCompleteAndUserid(CompleteState.findByComplete(complete),Long.valueOf(userid));

        return TodoMapper.mapToDtoList(todoList);
    }



    //Todo 생성
    @Transactional
    @Override
    public void addTodo (Long userid,TodoAddRequestDto todoAddRequestDto){
        userService.existsById(userid);

        Todo todo = Todo.builder()
                .content(todoAddRequestDto.getContent())
                .complete(CompleteState.INCOMPLETE)
                .createAt(LocalDate.now())
                .userid(userid)
                .build();

        todoRepository.save(todo);
    }


    //Todo 내용 수정
    @Transactional
    @Override
    public void editTodoContent(Long userid, TodoEditRequestDto todoEditRequestDto){
        userService.existsById(userid);

        Todo todo = todoRepository.findById(Long.valueOf(todoEditRequestDto.getTodoid())).orElseThrow(() -> new ResourceNotFoundException("존재하는 todo가 없습니다"));

        //재차 검증
        if(!todo.getUserid().equals(userid)){
            new ResourceNotFoundException("회원이 일치하지 않습니다.");
        }
        todo.updateTodoContent(todoEditRequestDto.getContent());
    }

    //Todo 완료 및 미완료
    @Transactional
    @Override
    public void completeTodo(Long userid, TodoCompleteRequestDto todoCompleteRequestDto){

        userService.existsById(userid);

        Todo todo = todoRepository.findById(Long.parseLong(todoCompleteRequestDto.getTodoid())).orElseThrow(() -> new ResourceNotFoundException("해당하는 todo가 없습ㄴ디ㅏ"));

        if(!todo.getUserid().equals(userid)){
            new ResourceNotFoundException("todo와 유저가 일치하지 않습니다.");
        }
        todo.completeTodo(todo.getComplete().toggle());

    }
    @Transactional
    @Override
    public void deleteTodo(Long userid,String todoid) {
        userService.existsById(userid);
        todoRepository.deleteById(Long.parseLong(todoid));
    }

}
