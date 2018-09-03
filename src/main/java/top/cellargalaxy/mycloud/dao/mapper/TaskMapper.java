package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.TaskDao;
import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/8/9
 */
@Mapper
public interface TaskMapper extends AbstractDao<TaskPo, TaskBo, TaskQuery> {
	@InsertProvider(type = TaskProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "taskId")
	int insert(TaskPo taskPo);

	@DeleteProvider(type = TaskProvider.class, method = "delete")
	int delete(TaskPo taskPo);

	@Results(id = "taskResult", value = {
			@Result(property = "taskId", column = "task_id", id = true),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "taskSort", column = "task_sort"),
			@Result(property = "status", column = "status"),
			@Result(property = "massage", column = "massage"),
			@Result(property = "finishTime", column = "finish_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "taskDetail", column = "task_detail"),
	})
	@SelectProvider(type = TaskProvider.class, method = "selectOne")
	TaskBo selectOne(TaskPo taskPo);

	@ResultMap(value = "taskResult")
	@SelectProvider(type = TaskProvider.class, method = "selectSome")
	List<TaskBo> selectSome(TaskQuery taskQuery);

	@SelectProvider(type = TaskProvider.class, method = "selectCount")
	int selectCount(TaskQuery taskQuery);

	@ResultMap(value = "taskResult")
	@SelectProvider(type = TaskProvider.class, method = "selectAll")
	List<TaskBo> selectAll();

	@UpdateProvider(type = TaskProvider.class, method = "update")
	int update(TaskPo taskPo);

	class TaskProvider /*implements AbstractProvider<TaskPo, TaskQuery>*/ {
		private String tableName = TaskDao.TABLE_NAME;
		private String taskId = tableName + ".task_id=#{taskId}";
		private String userId = tableName + ".user_id=#{userId}";
		private String createTime = tableName + ".create_time=#{createTime}";
		private String taskSort = tableName + ".task_sort=#{taskSort}";
		private String status = tableName + ".status=#{status}";
		private String massage = tableName + ".massage=#{massage}";
		private String finishTime = tableName + ".finish_time>=#{finishTime}";
		private String taskDetail = tableName + ".task_detail=#{taskDetail}";

		public String insert(TaskPo taskPo) {
			return "insert into " + tableName + "(user_id,create_time,task_sort,status,massage,finish_time,task_detail) " +
					"values(#{userId},#{createTime},#{taskSort},#{status},#{massage},#{finishTime},#{taskDetail})";
		}

		public String delete(TaskPo taskPo) {
			return ProviderUtil.delete(tableName, taskPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(TaskPo taskPo) {
			return ProviderUtil.selectOne(tableName, taskPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectSome(TaskQuery taskQuery) {
			return ProviderUtil.selectSome(tableName, taskQuery, this::wheresAll).append(" limit #{off},#{len}").toString();
		}

		public String selectCount(TaskQuery taskQuery) {
			return ProviderUtil.selectCount(tableName, taskQuery, this::wheresAll).toString();
		}

		public String selectAll() {
			return ProviderUtil.selectAll(tableName).toString();
		}

		public String update(TaskPo taskPo) {
			return ProviderUtil.update(tableName, taskPo, taskId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(TaskQuery taskQuery, Set<String> wheres) {
			if (taskQuery.getTaskId() > 0) {
				wheres.add(taskId);
			}
			if (taskQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (taskQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (!StringUtil.isBlank(taskQuery.getTaskSort())) {
				wheres.add(taskSort);
			}
			if (taskQuery.getStatus() > 0) {
				wheres.add(status);
			}
			if (!StringUtil.isBlank(taskQuery.getMassage())) {
				wheres.add(massage);
			}
			if (taskQuery.getFinishTime() != null) {
				wheres.add(finishTime);
			}
		}

		private void wheresKey(TaskPo taskPo, Set<String> wheres) {
			if (taskPo.getTaskId() > 0) {
				wheres.add(taskId);
			}
		}

		private void sets(TaskPo taskPo, Set<String> sets) {
			if (taskPo.getUserId() > 0) {
				sets.add(userId);
			}
			if (taskPo.getCreateTime() != null) {
				sets.add(createTime);
			}
			if (!StringUtil.isBlank(taskPo.getTaskSort())) {
				sets.add(taskSort);
			}
			if (taskPo.getStatus() > 0) {
				sets.add(status);
			}
			if (!StringUtil.isBlank(taskPo.getMassage())) {
				sets.add(massage);
			}
			if (taskPo.getFinishTime() != null) {
				sets.add(finishTime);
			}
			if (!StringUtil.isBlank(taskPo.getTaskDetail())) {
				sets.add(taskDetail);
			}
		}
	}
}
