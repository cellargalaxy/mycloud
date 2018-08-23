package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.TaskDao;
import top.cellargalaxy.mycloud.dao.mapper.TaskMapper;
import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
@Repository
@CacheConfig(cacheNames = "task")
public class TaskCache implements TaskDao {
	private Logger logger = LoggerFactory.getLogger(TaskCache.class);
	@Autowired
	private TaskMapper taskMapper;

	@Cacheable(key = "'selectOne'+#p0.taskId", condition = "true")
	public TaskBo selectOne(TaskPo taskPo) {
		logger.info("selectOne:{}", taskPo);
		return taskMapper.selectOne(taskPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<TaskBo> selectSome(TaskQuery taskQuery) {
		logger.info("selectSome:{}", taskQuery);
		return taskMapper.selectSome(taskQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(TaskQuery taskQuery) {
		logger.info("selectCount:{}", taskQuery);
		return taskMapper.selectCount(taskQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<TaskBo> selectAll() {
		logger.info("selectAll");
		return taskMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(TaskPo taskPo) {
		logger.info("insert:{}", taskPo);
		return taskMapper.insert(taskPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.taskId"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(TaskPo taskPo) {
		logger.info("delete:{}", taskPo);
		return taskMapper.delete(taskPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.taskId"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(TaskPo taskPo) {
		logger.info("update:{}", taskPo);
		return taskMapper.update(taskPo);
	}
}
