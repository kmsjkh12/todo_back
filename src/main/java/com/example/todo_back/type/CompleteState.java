package com.example.todo_back.type;

import lombok.AllArgsConstructor;
import lombok.Getter;


public enum CompleteState {
    COMPLETE("할일 완료", true),
    INCOMPLETE("할일 미완료" , false);

    private String complete1Value;
    private Boolean complete2Value;

    CompleteState(String complete1Value, Boolean complete2Value){
        this.complete1Value=complete1Value;
        this.complete2Value=complete2Value;
    }

    public static CompleteState findByComplete(String complete) {
        for (CompleteState state : CompleteState.values()) {
            if (state.getComplete1Value().equals(complete)) {
                return state;
            }
        }
        return null;
    }


    public String getComplete1Value(){
        return this.complete1Value;
    }
    public Boolean getComplete2Value(){
        return this.complete2Value;
    }


    public CompleteState toggle() {
        return this == COMPLETE ? INCOMPLETE : COMPLETE;
    }



}
