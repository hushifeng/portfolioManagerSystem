package com.team5.Service.Impl;

import com.team5.Pojo.PricesData;
import com.team5.Service.pricesDataService;
import com.team5.Dao.pricesDataDao;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/13.
 */
@Service
@Slf4j
public class pricesDataServiceImpl implements pricesDataService {

  @Autowired(required = false)
  private pricesDataDao pricesDataDao;

  public boolean createPricesData(PricesData pricesData) throws Exception{

    boolean result = false;
    try{
      result = pricesDataDao.insertPricesData(pricesData);
      log.info("Create a pricesData {} has been Successful", pricesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Create a pricesData {} has been Failed", pricesData.toString(),e);
    }

    return result;
  }

  public List<PricesData> getPricesDataAll() throws Exception{

    List<PricesData> pricesDatas = new ArrayList<PricesData>();
    try{
      pricesDatas = pricesDataDao.getPricesDataAll();
      log.info("Find all the PricesData {} has been Successful", pricesDatas.toString());
    }catch (Exception e){
      log.error("Find all the PricesData {} has been Failed", pricesDatas.toString(),e);
    }
    return pricesDatas;
  }

  public List<PricesData> getPricesDataBySymbol(String symbol) throws Exception{

    List<PricesData> pricesDatas=new ArrayList<PricesData>();
    try{
      pricesDatas = pricesDataDao.getPricesDataBySymbol(symbol);
      log.info("Find PricesData {} By Symbol {} has been Successful", pricesDatas,symbol);
    }catch (Exception e){
      log.error("Find PricesData {} By Symbol {} has been Failed", pricesDatas,symbol,e);
    }
    return pricesDatas;

  }


  public double getPricesDataBySymbolAndDateAndBase(String symbol, String date, String base_currency) throws Exception{

    double pricesData=0.0;
    try{
       pricesData = pricesDataDao.getPricesDataBySymbolAndDateAndBase(symbol,date,base_currency);
      log.info("Find PricesData {} By Symbol {} has been Successful", pricesData,symbol);
    }catch (Exception e){
      log.error("Find PricesData {} By Symbol {} has been Failed", pricesData,symbol,e);
    }
    return pricesData;
  }

  public double getPricesDataBySymbolAndDate(String symbol, String date, String base_currency)
      throws Exception {
    return pricesDataDao.getPricesDataBySymbolAndDate(symbol,date,base_currency);
  }

  public boolean deletePricesDataBySymbol(String symbol) throws Exception{
    boolean result = false;
    try{
      result = pricesDataDao.deletePricesDataBySymbol(symbol);
      log.info("Delete PricesData {} By Symbol {} has been Successful",symbol);
    }catch (Exception e){
      log.error("Delete PricesData {} By Symbol {} has been Failed",symbol,e);
    }

    return result;
  }

  public boolean deletePricesDataBySymbolList(List<String> symbols) throws Exception{
    boolean result = false;
    try{
      result = pricesDataDao.deletePricesDataBySymbolList(symbols);
      log.info("Create a pricesData {} has been Successful", symbols);
    }catch (Exception e){
      log.error("Create a pricesData {} has been Failed", symbols, e);
    }

    return result;
  }

  public boolean updatePricesData(PricesData pricesData) throws Exception{
    boolean result = false;
    try{
      result = pricesDataDao.updatePricesData(pricesData);
      log.info("Create a pricesData {} has been Successful", pricesData.toString());
    }catch (Exception e){
      log.error("Create a pricesData {} has been Failed", pricesData.toString(), e);
    }

    return result;
  }
}
