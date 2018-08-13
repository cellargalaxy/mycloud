package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.Vo;

/**
 * @author cellargalaxy
 * @time 2018/8/1
 */
@RestControllerAdvice
public class ExceptionController {
	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public Vo deniedException(AccessDeniedException e) {
		logger.info("deniedException:未授权访问");
		return new Vo("未授权访问", null);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Vo httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.info("httpRequestMethodNotSupportedException:http方法不支持:{}", e.getMessage());
		return new Vo("http方法不支持:" + e.getMessage(), null);
	}

	@ExceptionHandler(MultipartException.class)
	public Vo multipartException(MultipartException e) {
		logger.info("multipartException:文件上传异常:{}", e.getMessage());
		return new Vo("multipartException:文件上传异常:" + e.getMessage(), null);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Vo exception(Exception e) {
		logger.info("exception:{}", e);
		e.printStackTrace();
		GlobalException.add(e);
		return new Vo(e);
	}
}