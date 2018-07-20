package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;
import top.cellargalaxy.mycloud.service.LogService;

import java.util.concurrent.BlockingQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@Service
public class LogServiceImpl implements LogService {
	private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	@Override
	public String clearExceptionInfo() {
		logger.info("clearExceptionInfo");
		GlobalException.clear();
		return null;
	}

	@Override
	public BlockingQueue<ExceptionInfoVo> listExceptionInfo() {
		logger.info("listExceptionInfo");
		return GlobalException.get();
	}
}
