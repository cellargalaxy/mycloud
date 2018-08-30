package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.Vo;

import java.io.IOException;

/**
 * @author cellargalaxy
 * @time 2018/8/1
 */
@RestControllerAdvice
public class ExceptionController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

	@ExceptionHandler(BindException.class)
	public Vo bindException(BindException e) {
		logger.info("bindException:非法参数:{}", e.getMessage());
		return new Vo("非法参数:" + e.getMessage(), null);
	}

	@ExceptionHandler(MultipartException.class)
	public Vo multipartException(MultipartException e) {
		logger.info("multipartException:文件上传异常:{}", e.getMessage());
		return new Vo("multipartException:文件上传异常:" + e.getMessage(), null);
	}

	@ExceptionHandler(IOException.class)
	public Vo ioException(IOException e) {
		logger.info("ioException:IO异常:{}", e.getMessage());
		if (!e.toString().startsWith("org.apache.catalina.connector.ClientAbortException: java.io.IOException:")) {
			GlobalException.add(e);
		}
		return new Vo("ioException:IO异常:" + e.getMessage(), null);
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
