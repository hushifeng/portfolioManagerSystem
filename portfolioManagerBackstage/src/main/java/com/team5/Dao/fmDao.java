package com.team5.Dao;

import com.team5.Pojo.FundManager;

import java.util.List;
import java.util.Map;

/**
 * Created by ellen on 2018/8/10.
 */
public interface fmDao {

    public boolean insertFM(FundManager fundManager) throws Exception;

    public boolean updateFM(FundManager fundManager) throws Exception;

    public boolean deleteFM(FundManager fundManager) throws Exception;

    public boolean deleteManyFM(List<String> fundManagerIDList) throws Exception;

    public FundManager queryFM(String soeid) throws Exception;

    public Map<String, Object> queryAllFM() throws Exception;

}
