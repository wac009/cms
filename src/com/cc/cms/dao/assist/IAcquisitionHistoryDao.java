
package com.cc.cms.dao.assist;

/** @author wangcheng */
import java.util.List;
import com.cc.cms.dao.ICmsDao;
import com.cc.cms.entity.assist.AcquisitionHistory;
import com.cc.common.page.Pagination;

public interface IAcquisitionHistoryDao extends ICmsDao<AcquisitionHistory> {

	public List<AcquisitionHistory> getList(Integer siteId, Integer acquId);

	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo, Integer pageSize);

	public AcquisitionHistory findById(Integer id);

	@Override
	public AcquisitionHistory save(AcquisitionHistory bean);

	public AcquisitionHistory deleteById(Integer id);

	public Boolean checkExistByProperties(Boolean title, String value);
}