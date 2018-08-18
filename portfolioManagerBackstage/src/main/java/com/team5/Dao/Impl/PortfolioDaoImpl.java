package com.team5.Dao.Impl;

import com.team5.Dao.PortfolioDao;
import com.team5.Pojo.Portfolio;
import java.util.HashMap;
import java.util.Map;

import com.team5.Pojo.Position;
import com.team5.Service.PositionService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ellen on 2018/8/10.
 */
//@Slf4j
public class PortfolioDaoImpl extends SqlSessionDaoSupport implements PortfolioDao {

  @Autowired
  private PositionService positionService;

  public boolean createPortfolio(Portfolio portfolio) throws Exception{
    boolean result = false;
    SqlSession sqlSession = this.getSqlSession();

    try{
      sqlSession.insert("createPortfolio",portfolio);
      result=true;
    }catch (Exception e){
      e.printStackTrace();
      logger.error("Error when create a new portfolio");
    }
    return result;
  }

  public boolean deletePortfolio(int portfolio_id) throws Exception {

    boolean result = false;
    List<Integer> ids = null;
    try {
      SqlSession sqlSession = this.getSqlSession();
      ids = sqlSession.selectList("queryPositionToDel",portfolio_id);
      for (Integer id:ids) {
        positionService.deletePosition(id);
      }
      sqlSession.delete("deletePortfolio",portfolio_id);
      result = true;
    }catch (Exception e){
      e.printStackTrace();
      logger.error("Error when delete a portfolio");
    }
    return result;
  }

  public boolean deletePortfolios(List<Integer> portfolio_ids) {
    boolean result = false;
    try {
      for (Integer id:portfolio_ids) {
        deletePortfolio(id);
      }
      result = true;
    }catch (Exception e){
      e.printStackTrace();
      logger.error("Error when delete a portfolio");
    }

    return result;
  }

  public boolean updatePortfolio(Portfolio portfolio) throws Exception {

    /*
     * 更新portfolio时，如果减少initcash的量大于剩下的cash则返回失败，
     * 否则会相应减少cash的数量，步骤：
     * 1.根据portfolio_id找出此时剩余的cash，和initcash
     * 2.比较cash与initcash的变化量
     * 3.如果满足条件则进行更改，否则不进行修改
     * */

    System.out.println(portfolio);
    SqlSession sqlSession = this.getSqlSession();
    int id = portfolio.getId();

    Portfolio pre_portfolio = sqlSession.selectOne("queryPortfolioById",id);
    double pre_cash = pre_portfolio.getCash();
    double pre_initcash = pre_portfolio.getInitial_cash();

//    判断更改的是否大于cash,如果减少的initcash大于现在的cash，则返回false
    if ((portfolio.getInitial_cash() - pre_initcash + pre_cash) <0){
      return false;
    }else{//否则将更新portfolio
      portfolio.setCash(pre_cash+ portfolio.getInitial_cash() - pre_initcash );

      sqlSession.update("updatePortfolio",portfolio);
      return true;
    }

  }

  public List<Portfolio> queryPortfolio(String fund_Manager_id) throws Exception {
    List<Portfolio> result = null;
    try {
      SqlSession sqlSession = this.getSqlSession();
      result = sqlSession.selectList("queryPortfolio",fund_Manager_id);
      //System.out.println(result.size());
/*      for (Portfolio p:result ) {
        System.out.println(p.toString());
      }*/
    }catch (Exception e){
      e.printStackTrace();
      logger.error("Error when query  portfolions by fund_manager_id");
    }

    return result;
  }

  public List<Portfolio> queryPortfolio() throws Exception {
    List<Portfolio> result = null;
    try {
      SqlSession sqlSession = this.getSqlSession();
      result = sqlSession.selectList("queryPortfolioAll");
      //System.out.println(result.size());
/*      for (Portfolio p:result ) {
        System.out.println(p.toString());
      }*/
    }catch (Exception e){
      e.printStackTrace();
      logger.error("Error when query  portfolions by fund_manager_id");
    }

    return result;
  }

  public Portfolio queryPortfolioById(int portfolio_id) {
    Portfolio portfolio = null;
    try {
          SqlSession sqlSession = this.getSqlSession();
          portfolio = sqlSession.selectOne("queryPortfolioById",portfolio_id);
    }catch (Exception e){
      e.printStackTrace();
      logger.error("Error when query a portfolion by id");
    }
    return portfolio;
  }

  public boolean copyPortfolio(int portfolioID, String fundManagerID) throws Exception {
    boolean result = false;

    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("portfolioID", portfolioID);
      map.put("fundManagerID", fundManagerID);

      SqlSession sqlSession = this.getSqlSession();
      sqlSession.update("test.copyPortfolio", map);
    } catch (Exception e) {
      logger.error("Error while copy portfolio");
    }
    return result;
  }
}
