package com.team5;

import com.team5.Pojo.Portfolio;
import com.team5.Dao.Impl.PortfolioDaoImpl;
/*import org.junit.Before;
import org.junit.Test;*/
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/8/13.
 */
public class portfolioDaoImplTest {


    private ApplicationContext applicationContext;

    @BeforeAll
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Test
    public void testCreatePortfolio() throws Exception{
        Portfolio portfolio = new Portfolio("EDK33","yj98727",100);
        PortfolioDaoImpl PortfolioDaoImpl = (PortfolioDaoImpl) applicationContext.getBean("portfolioDao");
        boolean result = PortfolioDaoImpl.createPortfolio(portfolio);
        System.out.println(result+":"+portfolio.getId()+portfolio.getCash());
    }

    @Test
    public void testDeletePortfolio() throws Exception {
        PortfolioDaoImpl portfolioDaoImpl = (PortfolioDaoImpl) applicationContext.getBean("portfolioDao");
        boolean result = portfolioDaoImpl.deletePortfolio(2);
        System.out.println(result);
    }

    @Test
    public void testUpdatePortfolio() throws Exception {
        Portfolio portfolio = new Portfolio("BRIC99","yj98727",200);
        portfolio.setId(3);
        PortfolioDaoImpl portfolioDaoImpl = (PortfolioDaoImpl) applicationContext.getBean("portfolioDao");
        boolean result = portfolioDaoImpl.updatePortfolio(portfolio);
        System.out.println(result);
    }

    @Test
    public void testQueryPortfolio() throws Exception {
        PortfolioDaoImpl portfolioDaoImpl = (PortfolioDaoImpl) applicationContext.getBean("portfolioDao");
        List<Portfolio> result = portfolioDaoImpl.queryPortfolio("yj98727");
    }

    @Test
    public void testDeletePortfolios(){
        PortfolioDaoImpl portfolioDaoImpl = (PortfolioDaoImpl) applicationContext.getBean("portfolioDao");
        List<Integer> list = new ArrayList<Integer>(){{add(3);add(4);}};
        boolean result = portfolioDaoImpl.deletePortfolios(list);
        System.out.println(result);
    }


//  @Test
//  public void testInsertAdmin() throws Exception{
//    Administrator administrator = new Administrator(2,"John");
//    adminDaoImpl adminDaoImpl = (Dao.Impl.adminDaoImpl) applicationContext.getBean("AdminDao");
//    boolean result = adminDaoImpl.insertAdmin(administrator);
//    System.out.println(result+":"+administrator.getSoeid()+administrator.getName());
//  }

}