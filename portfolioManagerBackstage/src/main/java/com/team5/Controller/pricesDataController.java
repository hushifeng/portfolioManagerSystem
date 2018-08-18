package com.team5.Controller;

import com.alibaba.fastjson.JSONObject;
import com.team5.Pojo.PricesData;
import com.team5.Service.pricesDataService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ellen on 2018/8/13.
 */
@Controller
@RequestMapping("/api/pricesData/")
public class pricesDataController {

  //private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired(required = false)
  private pricesDataService pricesDataService;

  @RequestMapping(value = "uploadPricesData", method = RequestMethod.POST)
  public @ResponseBody JSONObject uploadPricesData(@RequestParam(value="symbol", required = false) String symbol,
      @RequestParam(value="date", required = false) String date, @RequestParam(value="price", required = false) double price,
      @RequestParam(value="base_currency",required = false) String base_currency) throws Exception{
    //Date realDate = sdf.parse(date);
    PricesData pricesData = new PricesData(symbol, date, price, base_currency);

    boolean result = pricesDataService.createPricesData(pricesData);

    JSONObject obj = new JSONObject();
    obj.put("result",result);

    return obj;
  }


  @RequestMapping(value = "getPricesDataAll", method = RequestMethod.GET)
  public @ResponseBody JSONObject getPricesDataAll() throws Exception{

    List<PricesData> pricesDataList = pricesDataService.getPricesDataAll();

    JSONObject obj = new JSONObject();
    obj.put("pricesDataList",pricesDataList);

    return obj;
  }

  @RequestMapping(value = "getPricesDataBySymbol", method = RequestMethod.GET)
  public @ResponseBody JSONObject getPricesDataBySymbolAndDateAndBase(@RequestParam(value="symbol",required = false) String symbol) throws Exception{

    //double pricesDatas = pricesDataService.getPricesDataBySymbolAndDateAndBase(symbol, date, base_currency);
    //PricesData pricesData = pricesDataService.getPricesDataBySymbol(symbol);

    List<PricesData> pricesDatas = pricesDataService.getPricesDataBySymbol(symbol);
    JSONObject obj = new JSONObject();
    obj.put("pricesData",pricesDatas);

    return obj;
  }

  @RequestMapping(value = "deletePricesDataBySymbol", method = RequestMethod.DELETE)
  public @ResponseBody JSONObject deletePricesDataBySymbol(@RequestParam(value="symbol",required = false) String symbol) throws Exception{

    boolean result = pricesDataService.deletePricesDataBySymbol(symbol);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }

  @RequestMapping(value="deleteMultiPricesDataBySymbols", method = RequestMethod.DELETE)
  public @ResponseBody JSONObject deletePricesDataBySymbolList(@RequestParam(value="symbols",required = false) List<String> symbols) throws Exception{

    boolean result = pricesDataService.deletePricesDataBySymbolList(symbols);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }

  @RequestMapping(value="updatePricesData", method=RequestMethod.POST)
  @ResponseBody
  public JSONObject updatePricesDataBySymbol(@RequestParam(value="symbol", required = false) String symbol,
      @RequestParam(value="date", required = false) String date,
      @RequestParam(value="price", required = false) double price,
      @RequestParam(value = "base_currency",required = false) String base_currency) throws Exception{

    PricesData pricesData = new PricesData(symbol, date, price, base_currency);
    boolean result = pricesDataService.updatePricesData(pricesData);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("result", result);

    return jsonObject;
  }


}
