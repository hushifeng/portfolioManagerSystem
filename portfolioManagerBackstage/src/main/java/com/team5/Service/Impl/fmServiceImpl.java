package com.team5.Service.Impl;

import com.team5.Dao.Impl.FMDaoImpl;
import com.team5.Pojo.FundManager;
import com.team5.Service.fmService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/10.
 */
@Service
public class fmServiceImpl implements fmService {
    @Autowired
    private FMDaoImpl fundmanagerDao;

    public boolean insertFM(FundManager fundManager) throws Exception {
        return fundmanagerDao.insertFM(fundManager);
    }

    public boolean updateFM(FundManager fundManager) throws Exception {
        return fundmanagerDao.updateFM(fundManager);
    }

    public boolean deleteFM(FundManager fundManager) throws Exception {
        return fundmanagerDao.deleteFM(fundManager);
    }

    public boolean deleteManyFM(List<String> fundManagerIDList) throws Exception {
        return fundmanagerDao.deleteManyFM(fundManagerIDList);
    }

    public FundManager queryFM(String soeid) throws Exception {
        return fundmanagerDao.queryFM(soeid);
    }

    public Map<String, Object> queryAllFM() throws Exception {
        return fundmanagerDao.queryAllFM();
    }
}
