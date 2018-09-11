package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class OwnServiceImpl implements OwnService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OwnDao ownDao;

	private final String domain;

	@Autowired
	public OwnServiceImpl(MycloudConfiguration mycloudConfiguration) {
		domain = mycloudConfiguration.getDomain();
		logger.info("FileUserController, domain:{}", domain);
	}

	@Override
	public String addOwn(OwnPo ownPo) {
		logger.info("addOwn:{}", ownPo);
		String string = checkAddOwn(ownPo);
		if (string != null) {
			return string;
		}
		int i = ownDao.insert(ownPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "权限空新增";
		}
		return null;
	}

	@Override
	public String removeOwn(OwnPo ownPo) {
		logger.info("removeOwn:{}", ownPo);
		int i = ownDao.delete(ownPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "所属空删除";
		}
		return null;
	}

	@Override
	public OwnBo getOwn(OwnPo ownPo) {
		logger.info("getOwn:{}", ownPo);
		return setUrl(ownDao.selectOne(ownPo));
	}

	@Override
	public int getOwnCount(OwnQuery ownQuery) {
		logger.info("getOwnCount:{}", ownQuery);
		return ownDao.selectCount(ownQuery);
	}

	@Override
	public List<OwnBo> listOwn(OwnQuery ownQuery) {
		logger.info("listOwn:{}", ownQuery);
		return setUrl(ownDao.selectSome(ownQuery));
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
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "所属空更新";
		}
		return null;
	}

	@Override
	public String addOwn(UserPo userPo, OwnPo ownPo) {
		ownPo.setOwnId(0);
		ownPo.setUserId(userPo.getUserId());
		return addOwn(ownPo);
	}

	@Override
	public String removeOwn(UserPo userPo, OwnQuery ownQuery) {
		ownQuery.setOwnId(0);
		ownQuery.setUserId(userPo.getUserId());
		return removeOwn(ownQuery);
	}

	@Override
	public OwnBo getOwn(UserPo userPo, OwnQuery ownQuery) {
		ownQuery.setOwnId(0);
		ownQuery.setUserId(userPo.getUserId());
		return getOwn(ownQuery);
	}

	@Override
	public int getOwnCount(UserPo userPo, OwnQuery ownQuery) {
		ownQuery.setOwnId(0);
		ownQuery.setUserId(userPo.getUserId());
		return getOwnCount(ownQuery);
	}

	@Override
	public List<OwnBo> listOwn(UserPo userPo, OwnQuery ownQuery) {
		ownQuery.setOwnId(0);
		ownQuery.setUserId(userPo.getUserId());
		return listOwn(ownQuery);
	}

	@Override
	public List<String> listSort(UserPo userPo, OwnQuery ownQuery) {
		ownQuery.setOwnId(0);
		ownQuery.setUserId(userPo.getUserId());
		return listSort(ownQuery);
	}

	@Override
	public String changeOwn(UserPo userPo, OwnPo ownPo) {
		ownPo.setOwnId(0);
		ownPo.setUserId(userPo.getUserId());
		return changeOwn(ownPo);
	}

	@Override
	public String checkAddOwn(OwnPo ownPo) {
		logger.info("checkAddOwn:{}", ownPo);
		String string = OwnDao.checkInsert(ownPo);
		if (string != null) {
			return string;
		}
		OwnBo ownBo = ownDao.selectOne(ownPo);
		if (ownBo != null) {
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
		OwnBo ownBo = ownDao.selectOne(ownPo);
		if (ownBo == null) {
			return "所属不存在";
		}
		return null;
	}

	private List<OwnBo> setUrl(List<OwnBo> ownBos) {
		if (ownBos == null) {
			return null;
		}
		for (OwnBo ownBo : ownBos) {
			setUrl(ownBo);
		}
		return ownBos;
	}

	private OwnBo setUrl(OwnBo ownBo) {
		if (ownBo == null) {
			return null;
		}
		ownBo.setUrl(StringUtil.createUrl(domain, ownBo.getMd5()));
		return ownBo;
	}
}
