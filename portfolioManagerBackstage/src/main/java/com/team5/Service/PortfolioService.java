package com.team5.Service;

import com.team5.Pojo.Portfolio;

import javax.sound.sampled.Port;
import java.util.List;

/**
 * Created by ellen on 2018/8/10.
 */
public interface PortfolioService {
    List<Portfolio> queryPortfolio(String fund_manager_id) throws Exception;
    List<Portfolio> queryPortfolio() throws Exception;
    boolean deletePortfolio(int portfolio_id) throws Exception;
    boolean deletePortfolios(List<Integer> portfolios_ids);
    boolean updatePortfolio(Portfolio portfolio) throws Exception;
    boolean createPortfolio(Portfolio portfolio) throws Exception;
    Portfolio queryPortfolioById(int id);
    boolean copyPortfolio(int portfolioID, String fundManagerID) throws Exception;

}
