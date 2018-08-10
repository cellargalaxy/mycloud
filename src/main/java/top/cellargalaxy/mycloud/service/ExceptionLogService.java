package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;

import java.util.Collection;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
public interface ExceptionLogService {
	String clearExceptionInfo();

	int getExceptionInfoCount();

	Collection<ExceptionInfoVo> listExceptionInfo();
}
