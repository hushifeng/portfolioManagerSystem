package com.team5.Service.Impl;

import com.team5.Pojo.FundManager;
import com.team5.Pojo.Portfolio;
import com.team5.Pojo.Position;
import com.team5.Pojo.PricesData;
import com.team5.Pojo.Report;
import com.team5.Pojo.bestFMVo;
import com.team5.Service.getReportService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.team5.Dao.*;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/14.
 */
@Slf4j
@Service
public class getReportServiceImpl implements getReportService {

  @Autowired
  private PortfolioDao portfolioDao;

  @Autowired
  private PositionDao positionDao;

  @Autowired
  private pricesDataDao pricesDataDo;

  @Autowired
  private ratesDataDao ratesDataDao;

  @Autowired
  private fmDao fmDao;

  private static double PRIMARY_AMOUNT_OF_OneFM = 1000.0;

  public List<bestFMVo> getBestFM() throws Exception{

    Map<String, Object> fundManagers;
    List<Portfolio> portfolios;
    Map<Integer, List<Position>> positions = new HashMap<Integer, List<Position>>();
    Map<String, List<Position>> positionsFMMap = new HashMap<String, List<Position>>();
    Map<String, Double> ptfValue = new HashMap<String, Double>();
    Map<String, Double> fmValue = new HashMap<String, Double>();
    List<bestFMVo> result = new ArrayList<bestFMVo>();
    List<FundManager> fmObjects =new ArrayList<FundManager>();

    double tempValue=0.0;
    double finalValue=0.0;

    try{
      fundManagers = fmDao.queryAllFM();
      fmObjects = (List<FundManager>) fundManagers.get("FundManagerList");

      for (FundManager fm:fmObjects) {
          //all the portfolios of one fundmanager
        bestFMVo bestFMVo = new bestFMVo();
          portfolios = portfolioDao.queryPortfolio(fm.getSoeid());
          for (Portfolio p:portfolios) {
            tempValue +=p.getCash();
            //all the positions of one portfolio

            for (Position position:positionDao.queryPosition(p.getId())) {
              tempValue += position.getPrice();// * position.getQuantity();
            }

            //ptfValue.put(p.getName(),tempValue);
          }
          bestFMVo.setSoeid(fm.getSoeid());
          bestFMVo.setTotalMoney(tempValue);
          //result.put(fm.getSoeid(),tempValue);
          result.add(bestFMVo);

          tempValue=0.0;
      }

      //result = getTopNum(fmValue, num);

      log.info("Get best fundmanagers {} has been Successful", result.toString());

    }catch (Exception e){
      e.printStackTrace();
      log.error("Get best fundmanagers {} has been Successful", result.toString());
    }

    return result;
  }

/*  public Map<String, Double> getTopNum(Map<String, Double> map, int num){
    Map<String, Double> result = new HashMap<String, Double>(num);
    Map<Double, String> reverseMap = new HashMap<Double, String>(num);
    ArrayList<Double> temp = new ArrayList<Double>();

    if(map==null || map.isEmpty()) return null;


    for (String s:map.keySet()) {
      temp.add(map.get(s));
    }

    Arrays.sort(temp.toArray(new Double[map.size()]));

    for (int i=0;i<num;i++) {
      if(map.containsValue(temp.get(i))){
        //result.put(map.,temp.get(i));
      }
    }

    return result;
  }*/

  public List<Report> getBestPortfolio() throws Exception{

    Map<String, Object> fms = new HashMap<String, Object>();
    List<String> fmIDs = new ArrayList<String>();
    List<Report> result = new ArrayList<Report>();
    List<FundManager> fmObjects =new ArrayList<FundManager>();

    try{
      fms = fmDao.queryAllFM();
      fmObjects = (List<FundManager>) fms.get("FundManagerList");

      for (FundManager f:fmObjects) {
        for (Report r:getBestPortfolioOfOneFM(f.getSoeid())) {
          result.add(r);
        }
      }

      log.info("Get best fundmanagers {} has been Successful", result.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Get best fundmanagers {} has been Successful", result.toString(), e);

    }
    return result;
  }


  public List<Report> getBestPortfolioOfOneFM(String fmId) throws Exception{

    List<Report> result = new ArrayList<Report>();

    //get all the portfolios of this fundmanager
    List<Portfolio> portfolios = portfolioDao.queryPortfolio(fmId);

    double amount=0.0;
    int k=0;
    double denominator = PRIMARY_AMOUNT_OF_OneFM;
    Calendar ca = Calendar.getInstance();
    ca.setTime(new Date());
    //String date = ca.getCalendarType()+"-"+"-";
    String date = ca.get(Calendar.YEAR)+"-0"+(ca.get(Calendar.MONTH)+1)+"-"+ca.get(Calendar.DATE);


    try {

      for (Portfolio p : portfolios) {
        Report report = new Report();
        amount=0;
        //for (Position po : positionDao.queryPosition(p.getId())) {

          //if(positionDao.changePriceToTerm(po.getBase_currency(),po.getDate()).get(0)!=null && positionDao.changePriceToTerm(po.getBase_currency(),po.getDate()).get(0)!=0){
        amount += positionDao.queryTotalPrice(p.getId(), date);//po.getPrice();
        System.out.println(positionDao.queryTotalPrice(p.getId(),date));
          //}
        //}
        report.setPtfId(p.getId());
        report.setProfit(amount / p.getInitial_cash());
        report.setFmId(fmId);
        result.add(report);
      }

      log.info("Get best fundmanagers {} from the fundmanager {} has been Successful", result.toString(), fmId);

    }catch (Exception e){
      e.printStackTrace();
      log.error("Get best fundmanagers {} from the fundmanager {} has been Successful", result.toString(), fmId, e);

    }

    return result;
  }

}
