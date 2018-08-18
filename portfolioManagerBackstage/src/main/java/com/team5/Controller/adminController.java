package com.team5.Controller;

import com.alibaba.fastjson.JSONObject;
import com.team5.Pojo.Administrator;
import com.team5.Pojo.PricesData;
import com.team5.Service.Impl.PortfolioServiceImpl;
import com.team5.Service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ellen on 2018/8/13.
 */
@Controller
@RequestMapping("api/admin/")
public class adminController {

  @Autowired(required = false)
  private adminService adminService;

  @RequestMapping(value = "createAdmin", method = RequestMethod.POST)
  public @ResponseBody
  JSONObject uploadPricesData(@RequestParam(value = "soeid", required = false) String soeid,
      @RequestParam(value = "name", required = false) String name) throws Exception{

    Administrator administrator = new Administrator(soeid, name);
    boolean result = adminService.createAdmin(administrator);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }

  @Autowired
  private PortfolioServiceImpl portfolioServiceImpl;

  @RequestMapping(value = "copyPortfolio", method = {RequestMethod.GET})
  @ResponseBody
  public boolean copyPortfolio(@RequestParam int portfolioID, @RequestParam String fundManagerID ) throws Exception {
    return  portfolioServiceImpl.copyPortfolio(portfolioID, fundManagerID);
  }


}
