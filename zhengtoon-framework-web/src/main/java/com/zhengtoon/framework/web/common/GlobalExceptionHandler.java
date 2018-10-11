package com.zhengtoon.framework.web.common;


import com.zhengtoon.framework.entity.ResponseResult;
import com.zhengtoon.framework.exception.CoreExceptionCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 统一异常处理类，用于处理spring抛出的异常
 *
 * @author LuCheng.Qi
 * @since 2018-07-09
 * Company:北京思源政务通有限公司
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


	/**
	 * 其他异常信息处理
	 * @param exption exption
	 * @return ResponseResult
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult globalException(Exception exption) {
		log.error(exption.getMessage(),exption);
		ResponseResult<Object> error = new ResponseResult<>();
		error.setMeta(CoreExceptionCodes.REQUEST_ERROR);
		error.setData(exption.getMessage());
		return error;
	}

	/**
	 * 参数处理异常
	 * @param exption MethodArgumentNotValidException 参数校验异常类
	 * @return ResponseResult
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseResult globalParamException(MethodArgumentNotValidException exption) {
		List<ObjectError> allErrors = exption.getBindingResult().getAllErrors();
		StringBuilder sb = new StringBuilder();
		for(ObjectError error :allErrors){
			if(error instanceof FieldError){
				sb.append(String.format("property[%s],error msg[%s]",((FieldError) error).getField(), error.getDefaultMessage())).append("\n");
			}
		}
		ResponseResult<Object> error = new ResponseResult<>();
		error.setMeta(CoreExceptionCodes.PARAM_IS_ILLEGAL);
		error.setData(sb.toString());
		return error;
	}


}
