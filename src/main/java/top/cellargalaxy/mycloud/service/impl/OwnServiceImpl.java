package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.OwnService;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class OwnServiceImpl implements OwnService {
	private Logger logger = LoggerFactory.getLogger(OwnServiceImpl.class);
	@Autowired
	private OwnDao ownDao;

	@Override
	public String addOwn(OwnPo ownPo) {
		logger.info("addOwn:{}", ownPo);
		String string = checkAddOwn(ownPo);
		if (string != null) {
			return string;
		}
		int i = ownDao.insert(ownPo);
		if (i == 0) {
			return "权限空新增";
		}
		return null;
	}

	@Override
	public String removeOwn(OwnQuery ownQuery) {
		logger.info("removeOwn:{}", ownQuery);
		int i = ownDao.delete(ownQuery);
		if (i == 0) {
			return "所属空删除";
		}
		return null;
	}

	@Override
	public OwnBo getOwn(OwnQuery ownQuery) {
		logger.info("getOwn:{}", ownQuery);
		return ownDao.selectOne(ownQuery);
	}

	@Override
	public List<OwnBo> listOwn(OwnQuery ownQuery) {
		logger.info("listOwn:{}", ownQuery);
		return ownDao.selectSome(ownQuery);
	}

	@Override
	public List<String> listSort(OwnQuery ownQuery) {
		logger.info("listSort:{}" + ownQuery);
		return ownDao.selectSort(ownQuery);
	}

	@Override
	public String changeOwn(OwnPo ownPo) {
		logger.info("changeOwn:{}", ownPo);
		String string = checkChangeOwn(ownPo);
		if (string != null) {
			return string;
		}
		int i = ownDao.update(ownPo);
		if (i == 0) {
			return "所属空更新";
		}
		return null;
	}

	@Override
	public String checkAddOwn(OwnPo ownPo) {
		logger.info("checkAddOwn:{}", ownPo);
		String string = OwnDao.checkInsert(ownPo);
		if (string != null) {
			return string;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setOwnId(ownPo.getOwnId());
		ownQuery.setUserId(ownPo.getUserId());
		ownQuery.setFileId(ownPo.getFileId());
		OwnPo own = ownDao.selectOne(ownQuery);
		if (own != null) {
			return "所属已存在";
		}
		return null;
	}

	@Override
	public String checkChangeOwn(OwnPo ownPo) {
		logger.info("checkChangeOwn:{}", ownPo);
		String string = OwnDao.checkUpdate(ownPo);
		if (string != null) {
			return string;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setOwnId(ownPo.getOwnId());
		ownQuery.setUserId(ownPo.getUserId());
		ownQuery.setFileId(ownPo.getFileId());
		OwnPo own = ownDao.selectOne(ownQuery);
		if (own == null) {
			return "所属不存在";
		}
		return null;
	}
}
