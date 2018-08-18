package com.team5.Service.Impl;

import com.team5.Pojo.Portfolio;
import com.team5.Dao.PortfolioDao;
import com.team5.Service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/10.
 */
@Service
public class PortfolioServiceImpl implements PortfolioService {


//
//    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//    PortfolioDaoImpl PortfolioDaoImpl = (PortfolioDaoImpl) applicationContext.getBean("PortfolioDao");

    @Autowired
    private PortfolioDao portfolioDao;

    public List<Portfolio> queryPortfolio(String fund_manager_id) throws Exception {
        return portfolioDao.queryPortfolio(fund_manager_id);
    }

    public List<Portfolio> queryPortfolio() throws Exception {
        return portfolioDao.queryPortfolio();
    }

    public boolean deletePortfolio(int portfolio_id) throws Exception {

        return portfolioDao.deletePortfolio(portfolio_id);
    }

    public boolean deletePortfolios(List<Integer> portfolios_ids) {
        return portfolioDao.deletePortfolios(portfolios_ids);
    }

    public boolean updatePortfolio(Portfolio portfolio) throws Exception {
        System.out.println("service:"+portfolio.toString());
        return portfolioDao.updatePortfolio(portfolio);
    }

    public boolean createPortfolio(Portfolio portfolio) throws Exception {
        return portfolioDao.createPortfolio(portfolio);
    }

    public Portfolio queryPortfolioById(int id) {
        return portfolioDao.queryPortfolioById(id);
    }

    public boolean copyPortfolio(int portfolioID, String fundManagerID) throws Exception {
        return portfolioDao.copyPortfolio(portfolioID, fundManagerID);
    }
}
