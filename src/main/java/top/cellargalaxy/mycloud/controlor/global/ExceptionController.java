package top.cellargalaxy.mycloud.controlor.global;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.Vo;

/**
 * @author cellargalaxy
 * @time 2018/8/1
 */
@RestControllerAdvice
public class ExceptionController {
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public Vo handle401(AccessDeniedException e) {
		return new Vo("未授权访问", null);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Vo globalException(Exception e) {
		e.printStackTrace();
		GlobalException.add(e);
		return new Vo(e);
	}
}
