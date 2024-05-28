package org.calderon.users.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
	private Integer status;
	private String errorType;
	private String message;
	private String path;
}