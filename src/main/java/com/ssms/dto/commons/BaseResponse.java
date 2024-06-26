package com.ssms.dto.commons;

import com.ssms.constants.ApplicationConstants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
	private String responseStatus = ApplicationConstants.ResponseConstants.RESPONSE_FAILURE;
	private String message;
	private String code;
}
