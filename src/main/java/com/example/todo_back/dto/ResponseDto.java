package com.example.todo_back.dto;
import com.example.todo_back.common.ResponseCode;
import com.example.todo_back.common.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseDto {
    private String code;
    private String message;


    public ResponseDto() {
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS;
    }

    public ResponseDto(String code, String message) {
        this.code=code;
        this.message= message;
    }


    //성공
    public static ResponseEntity<ResponseDto> success() {
        ResponseDto responseBody = new ResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    //수정 실패
    public static ResponseEntity<ResponseDto> notModified(String message) {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NOT_MODIFIED, message);
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(responseBody);
    }

    //값 에러
    public static ResponseEntity<ResponseDto> validationFail(String message) {
        ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAIL, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    //데이터베이스 오류
    public static ResponseEntity<ResponseDto> databaseError(String message) {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, message);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }

    //찾을 수 없음
    public static ResponseEntity<ResponseDto> notFound(String message) {
        ResponseDto responseBody = new ResponseDto(ResponseCode.RESOURCE_NOT_FOUND, message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    //찾을 수 없음
    public static ResponseEntity<ResponseDto> userNotFound(String message) {
        ResponseDto responseBody = new ResponseDto(ResponseCode.RESOURCE_NOT_FOUND, message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
    }

    //내부 서버 오류
    public static ResponseEntity<ResponseDto> internalServerError(String message) {
        ResponseDto responseBody = new ResponseDto(ResponseCode.INTERNAL_SERVER_ERROR,message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

}