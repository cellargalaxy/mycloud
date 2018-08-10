package top.cellargalaxy.mycloud.dao.cache;

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
	@Autowired
	private TaskMapper taskMapper;

	@Caching(evict = {
			@CacheEvict(key = "#p0.taskId"),
	})
	public int insert(TaskPo taskPo) {
		return taskMapper.insert(taskPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.taskId"),
	})
	public int delete(TaskPo taskPo) {
		return taskMapper.delete(taskPo);
	}

	@Cacheable(key = "#p0.taskId", condition = "#p0.taskId>0")
	public TaskBo selectOne(TaskPo taskPo) {
		return taskMapper.selectOne(taskPo);
	}

	public List<TaskBo> selectSome(TaskQuery taskQuery) {
		return taskMapper.selectSome(taskQuery);
	}

	public int selectCount(TaskQuery taskQuery) {
		return taskMapper.selectCount(taskQuery);
	}

	public List<TaskBo> selectAll() {
		return taskMapper.selectAll();
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.taskId"),
	})
	public int update(TaskPo taskPo) {
		return taskMapper.update(taskPo);
	}
}
