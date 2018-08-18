package com.team5.Controller;

import com.alibaba.fastjson.JSONObject;
import com.team5.Pojo.PricesData;
import com.team5.Pojo.Report;
import com.team5.Pojo.bestFMVo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.team5.Service.getReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ellen on 2018/8/14.
 */
@Controller
@RequestMapping(value = "api/report/")
public class getReportController {

  @Autowired
  private getReportService getReportService;

  @RequestMapping(value = "getBestFM", method = RequestMethod.GET)
  public @ResponseBody JSONObject getBestFundManager() throws Exception{

    List<bestFMVo> bestFMs = getReportService.getBestFM();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("bestFMs", bestFMs);

    return jsonObject;
  }


  @RequestMapping(value = "getBestPortfolio", method = RequestMethod.GET)
  public @ResponseBody JSONObject getBestPortfolio() throws Exception{

    List<Report> result = getReportService.getBestPortfolio();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("bestPortfolios", result);

    return jsonObject;
  }


}
