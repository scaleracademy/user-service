package dev.rakesh.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseResponseDTO<T> {

	private String error;

	private T data;

	private int status;

	public BaseResponseDTO(Exception ex, HttpStatus status) {
		this.error = ex.getMessage();
		this.status = status.value();
	}
	public BaseResponseDTO(HttpStatus status, T data) {
		this.status = status.value();
		this.data = data;
	}
}
