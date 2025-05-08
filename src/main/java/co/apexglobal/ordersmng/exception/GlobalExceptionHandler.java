package co.apexglobal.ordersmng.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(OrdenNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrdenNotFoundException(OrdenNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
    	 Map<String, String> response = new HashMap<>();
         response.put("mensaje", ex.getMessage());
         return ResponseEntity.internalServerError().body(response);
    }

}
