package com.team5.Controller;

import com.team5.Pojo.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.team5.Service.PortfolioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2018/8/11.
 */

@Controller
@RequestMapping(value = "api/portfolio/")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

//test true
    @RequestMapping(value = "/queryPortfolioById",method = RequestMethod.GET)
    public @ResponseBody
    Portfolio queryPortfolioById(@RequestParam int id){
        return portfolioService.queryPortfolioById(id);
    }

//test true
    @RequestMapping(value = "/queryPortfolio",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryPortfolios(@RequestParam String fund_manager_id) throws Exception {
        List<Portfolio> results = portfolioService.queryPortfolio(fund_manager_id);
        Map<String,Object> map = new HashMap<String, Object>(2);
        map.put("total",results.size());
        map.put("portfolios",results);
        return map;

    }

    //test true
    @RequestMapping(value = "/queryPortfolioAll",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryPortfolios() throws Exception {
        List<Portfolio> results = portfolioService.queryPortfolio();
        Map<String,Object> map = new HashMap<String, Object>(2);
        map.put("total",results.size());
        map.put("portfolios",results);
        return map;

    }

//test true
    @RequestMapping(value = "/deletePortfolio",method = RequestMethod.GET)
    @ResponseBody
    public boolean deletePortfolios(@RequestParam int portfolio_id) throws Exception {
        return portfolioService.deletePortfolio(portfolio_id);
    }

//test true
    @RequestMapping(value = "/updatePortfolio",method = RequestMethod.GET)
    @ResponseBody
    public boolean updatePortfolio(@RequestParam int id, @RequestParam String name,
        @RequestParam String fund_manager_id,
        @RequestParam double initial_cash) throws Exception {

        return portfolioService.updatePortfolio(new Portfolio(id, name, fund_manager_id, initial_cash));
    }

//    test false
    @RequestMapping(value="/deletePortfolios",method = RequestMethod.GET)
    @ResponseBody
    public boolean deletePortfolios(@RequestParam List<Integer> ids){
        System.out.println(ids.toString());
        return portfolioService.deletePortfolios(ids);
    }
//    test true
    @RequestMapping(value = "/createPortfolio",method = RequestMethod.GET)
    @ResponseBody
    public boolean createPortfolios(
                                    @RequestParam String name,
                                    @RequestParam String fund_manager_id,
                                    @RequestParam double initcash) throws Exception {

        Portfolio pf = new Portfolio(name,fund_manager_id,initcash,initcash);
        return portfolioService.createPortfolio(pf);
    }

//    ___________Portfolio(id=1, name=BRIC99, fund_manager_id=yj98727, cash=200.0)

}
