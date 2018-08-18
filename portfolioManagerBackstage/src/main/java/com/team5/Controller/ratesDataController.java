package com.team5.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.team5.Pojo.PricesData;
import com.team5.Pojo.RatesData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.ws.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.team5.Service.ratesDataService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ellen on 2018/8/13.
 */
@Controller
@RequestMapping("api/ratesData/")
public class ratesDataController {

/*  private static String SUCCESS = "success";
  private static String SUCCESS_CODE = "1";
  private static String ERROR_CODE = "0";*/

  @Autowired
  private ratesDataService ratesDataService;

  @RequestMapping(value = "uploadRatesData", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject uploadRatesData(@RequestParam(value = "term_currency", required = false) String term_currency, @RequestParam(value = "rate", required = false) double rate,
  @RequestParam(value = "date", required = false) String date, @RequestParam(value = "base_currency", required = false) String base_currency) throws Exception{

    RatesData ratesData = new RatesData(term_currency, rate, date, base_currency);
    boolean result = ratesDataService.createRatesData(ratesData);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }

  @RequestMapping(value = "getRatesDataAll", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getRatesDataAll() throws Exception{

    List<RatesData> ratesDataList = ratesDataService.getRatesDataAll();

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("ratesDataList", ratesDataList);

    return jsonObject;
  }

  @RequestMapping(value = "getRatesDataByTCBCDate", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getRatesDataByTCBCDate(@RequestParam(value="term_currency",required = false) String term_currency,
      @RequestParam(value="base_currency", required = false)String base_currency,
      @RequestParam(value="date",required = false) String date) throws Exception{
    List<RatesData> ratesDataList = ratesDataService.getRatesDataByTCBCDate(term_currency, base_currency, date);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("ratesDataList", ratesDataList);

    return jsonObject;
  }

  @RequestMapping(value="deleteRatesDataByDateAndBaseAndTerm", method = RequestMethod.DELETE)
  @ResponseBody
  public JSONObject deleteRatesDataByDateBCTC(@RequestParam(value="date",required = false) String date,
  @RequestParam(value="base_currency",required = false) String base_currency,
  @RequestParam(value="term_currency",required = false) String term_currency) throws Exception{

    boolean result = ratesDataService.deleteRatesDataByDateBCTC(date, base_currency, term_currency);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }

  @RequestMapping(value="deleteMultiRatesDatas", method=RequestMethod.POST)
  @ResponseBody
  public JSONObject deleteRatesDataByList(@RequestBody List<RatesData> list) throws Exception{

    boolean result = ratesDataService.deleteRatesDataByTCList(list);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }

  @RequestMapping(value="updateRatesDataByDateAndBaseAndTerm", method=RequestMethod.POST)
  @ResponseBody
  public JSONObject updateRatesData(@RequestParam(value = "term_currency", required = false) String term_currency, @RequestParam(value = "rate", required = false) double rate,
      @RequestParam(value = "date", required = false) String date, @RequestParam(value = "base_currency", required = false) String base_currency) throws Exception{

    RatesData ratesData = new RatesData(term_currency, rate, date, base_currency);
    boolean result = ratesDataService.updateRatesData(ratesData);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }
}
