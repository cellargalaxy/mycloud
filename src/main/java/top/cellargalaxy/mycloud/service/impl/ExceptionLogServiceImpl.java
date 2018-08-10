package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;
import top.cellargalaxy.mycloud.service.ExceptionLogService;

import java.util.Collection;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@Service
public class ExceptionLogServiceImpl implements ExceptionLogService {
	private Logger logger = LoggerFactory.getLogger(ExceptionLogServiceImpl.class);

	@Override
	public String clearExceptionInfo() {
		logger.info("clearExceptionInfo");
		GlobalException.clear();
		return null;
	}

	@Override
	public int getExceptionInfoCount() {
		logger.info("getExceptionInfoCount");
		return GlobalException.get().size();
	}

	@Override
	public Collection<ExceptionInfoVo> listExceptionInfo() {
		logger.info("listExceptionInfo");
		return GlobalException.get();
	}
}
