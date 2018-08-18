package com.team5.Dao;


import com.team5.Pojo.Portfolio;
import java.util.List;
/**
 * Created by ellen on 2018/8/10.
 */
public interface PortfolioDao {
  public boolean createPortfolio(Portfolio portfolio) throws Exception;
  public boolean deletePortfolio(int portfolio_id) throws Exception;
  public boolean deletePortfolios(List<Integer> portfolio_ids);
  public boolean updatePortfolio(Portfolio portfolio) throws Exception;
  public List<Portfolio> queryPortfolio(String fund_manager_id) throws Exception;
  public List<Portfolio> queryPortfolio() throws Exception;
  public Portfolio queryPortfolioById(int portfolio_id);
  public boolean copyPortfolio(int portfolioID, String fundManagerID) throws Exception;
}
