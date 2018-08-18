package com.team5.Dao.Impl;

import com.team5.Dao.fmDao;
import com.team5.Pojo.FundManager;
import com.team5.Pojo.Portfolio;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ellen on 2018/8/10.
 */
public class FMDaoImpl extends SqlSessionDaoSupport implements fmDao {
    @Autowired
    private PortfolioDaoImpl portfolioDao;

    public boolean insertFM(FundManager fundManager) throws Exception {

        boolean result = false;

        try{
            SqlSession sqlSession = this.getSqlSession();
            sqlSession.insert("test.insertFundManager",fundManager);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Insert a new fundmanager {} has been Failed",e);
        }

        return result;
    }


    public FundManager queryFM(String soeid) throws Exception {
        FundManager fundManager = null;

        try{
            SqlSession sqlSession = this.getSqlSession();
            fundManager = sqlSession.selectOne("test.queryFundManager", soeid);
        } catch (Exception e) {
            logger.error("Query fundmanager {} has been Failed", e);
        }
        return fundManager;
    }

    public Map<String, Object> queryAllFM() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SqlSession sqlSession = this.getSqlSession();
            List<FundManager> fundManagerList = sqlSession.selectList("test.queryAllFundManager");
            map.put("Total", fundManagerList.size());
            map.put("FundManagerList", fundManagerList);
        } catch (Exception e) {
            logger.error("Query fundmanagerList has been Failed", e);
        }
        return map;
    }

    public boolean updateFM(FundManager fundManager) throws Exception {
        boolean result = false;

        try{
            SqlSession sqlSession = this.getSqlSession();
            sqlSession.update("test.updateFundManager", fundManager);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("update fundmanager {} has been Failed",e);
        }

        return result;
    }

    public boolean deleteFM(FundManager fundManager) throws Exception {
        boolean result = false;

        try{
            SqlSession sqlSession = this.getSqlSession();

            //check this fundmanager has portfolio or not
            //if portfolioList not null can not delete this fundmanager!
            String soeid = fundManager.getSoeid();
            List<Portfolio> list = portfolioDao.queryPortfolio(soeid);
            if(list.size() != 0) {
                logger.error("Cannot delete a fundmanager who still have portfolio!");
                result = false;
            } else {
                sqlSession.delete("test.deleteFundManager", fundManager);
                result=true;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Delete a fundmanager {} has been Failed", e);
        }

        return result;
    }

    public boolean deleteManyFM(List<String> fundManagerIDList) throws Exception {
        boolean result = false;
        try {
            for (String id: fundManagerIDList) {
                FundManager fundManager = queryFM(id);
                result = deleteFM(fundManager);
            }
        } catch (Exception e) {
            logger.error("Delete fundmanagers has been Failed", e);
        }

        return result;
    }
}
