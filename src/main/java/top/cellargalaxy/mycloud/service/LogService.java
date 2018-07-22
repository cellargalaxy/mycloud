package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
public interface LogService {
	String clearExceptionInfo();

	List<ExceptionInfoVo> listExceptionInfo();
}
