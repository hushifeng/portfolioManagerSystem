package com.team5.Service.Impl;

import com.team5.Pojo.RatesData;
import com.team5.Service.ratesDataService;
import com.team5.Dao.ratesDataDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/13.
 */
@Service
@Slf4j
public class ratesDataServiceImpl implements ratesDataService {

  @Autowired(required = false)
  private ratesDataDao ratesDataDao;

  public boolean createRatesData(RatesData ratesData) throws Exception{

    boolean result=false;

    try{
      result = ratesDataDao.insertRatesData(ratesData);
      log.info("Create a new ratesData {} has been Successful",ratesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Create a new ratesData {} has been Failed",ratesData.toString(),e);
    }

    return result;
  }

  public List<RatesData> getRatesDataByTCBCDate(String termCurrency, String baseCurrency, String date) throws Exception{

    List<RatesData> ratesDatas = new ArrayList<RatesData>();

    try{
      ratesDatas = ratesDataDao.getRatesDataByTCBCDate(termCurrency, baseCurrency, date);

      log.info("Find ratesData {} By TC {} has been Successful",ratesDatas.toString(), termCurrency);
    }catch (Exception e){

      e.printStackTrace();
      log.error("Find ratesData {} By TC {} has been Failed",ratesDatas.toString(),termCurrency,e);
    }
    return ratesDatas;
  }

  public List<RatesData> getRatesDataAll() throws Exception{

    List<RatesData> ratesDatas = new ArrayList<RatesData>();
    try{
      ratesDatas = ratesDataDao.getRatesDataAll();
      log.info("Find all the ratesData {} has been Successful",ratesDatas.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Find all the ratesData {} has been Failed",ratesDatas.toString(),e);
    }
    return ratesDatas;
  }

  public boolean deleteRatesDataByDateBCTC(String date, String BC, String TC) throws Exception{
    boolean result=false;
    try{
      result = ratesDataDao.deleteRatesDataByDateBCTC(date, BC, TC);
      log.info("Delete the ratesData By Date {} and BaseCurrency {} and TempCurrency {} has been Successful", date, BC, TC);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Delete the ratesData By Date {} and BaseCurrency {} and TempCurrency {} has been Failed", date, BC, TC, e);
    }
    return result;
  }

  public boolean deleteRatesDataByTCList(List<RatesData> TCs) throws Exception{
    boolean result=false;
    try{
      result = ratesDataDao.deleteRatesDataByTCList(TCs);
      log.info("Delete multiple ratesData {} By TempCurrencys {} has been Successful", TCs);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Delete multiple ratesData {} has been Failed", TCs, e);
    }
    return result;
  }

  public boolean updateRatesData(RatesData ratesData) throws Exception{
    boolean result=false;
    try{
      result = ratesDataDao.updateRatesData(ratesData);
      log.info("Update the ratesData {} By TempCurrency {} has been Successful", ratesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Update all the ratesData {} By TempCurrency {} has been Failed", ratesData.toString(), e);
    }
    return result;
  }

  public double getRateToPosition(String base_currency, String term_currency, String date) {
    double rate = 0;
    try{
      rate = ratesDataDao.getRateToPosition(base_currency,term_currency,date);
      log.info("Update the ratesData {} By TempCurrency {} has been Successful", rate);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Update all the ratesData {} By TempCurrency {} has been Failed", rate, e);
    }
    return rate;
  }

}
