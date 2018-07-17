package top.cellargalaxy.mycloud.service.impl;

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
	@Autowired
	private OwnDao ownDao;

	@Override
	public void addOwn(OwnPo ownPo) {
		ownDao.insert(ownPo);
	}

	@Override
	public void removeOwn(OwnQuery ownQuery) {
		ownDao.delete(ownQuery);
	}

	@Override
	public OwnBo getOwn(OwnQuery ownQuery) {
		return ownDao.selectOne(ownQuery);
	}

	@Override
	public List<OwnBo> listOwn(OwnQuery ownQuery) {
		return ownDao.selectSome(ownQuery);
	}

	@Override
	public List<String> listSort(int userId) {
		return ownDao.selectSort(userId);
	}

	@Override
	public void changeOwn(OwnPo ownPo) {
		ownDao.update(ownPo);
	}

	@Override
	public String checkAddOwn(OwnPo ownPo) {
		return OwnDao.checkInsert(ownPo);
	}

	@Override
	public String checkChangeOwn(OwnPo ownPo) {
		return OwnDao.checkUpdate(ownPo);
	}
}
