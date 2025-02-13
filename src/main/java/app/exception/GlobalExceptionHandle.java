package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.dto.ErrorResponse.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandle {

    // Xử lý lỗi mật khẩu không hợp lệ
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    // Xử lý lỗi khi user đã tồn tại
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO(HttpStatus.CONFLICT.value(), exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); 
    }


    // Xử lý lỗi khi resource không tìm thấy
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            exception.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRunTimeException(RuntimeException exception){
    	ErrorResponseDTO error=new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception.getMessage());
    	return new ResponseEntity<ErrorResponseDTO>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	
    }
}
