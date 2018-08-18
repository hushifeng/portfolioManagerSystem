package com.team5;

import com.team5.Dao.fmDao;
import com.team5.Dao.ratesDataDao;
import com.team5.Pojo.*;
import com.team5.Dao.Impl.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/*import org.junit.Before;
import org.junit.Test;*/
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ellen on 2018/8/12.
 */
@Slf4j
public class UnitTest {

  private static ApplicationContext applicationContext;
  private static FMDaoImpl fmDao;
  private static PortfolioDaoImpl portfolioDao;

  @BeforeAll
  public void setUp() throws Exception {
    applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    fmDao = (FMDaoImpl) applicationContext.getBean("fundmanagerDao");
    portfolioDao = (PortfolioDaoImpl) applicationContext.getBean("portfolioDao");
  }

  @Test
  public void testInsertAdmin() throws Exception{
    Administrator administrator = new Administrator("122","John");
    adminDaoImpl adminDaoImpl = (adminDaoImpl) applicationContext.getBean("adminDao");
    boolean result = adminDaoImpl.insertAdmin(administrator);
    System.out.println(result+":"+administrator.getSoeid()+administrator.getName());
  }

  @Test
  public void testInsertRatesData() throws Exception{
    RatesData ratesData = new RatesData(3,"USD",1.09,"2018-08-10","CHY");
    ratesDataDaoImpl ratesDataDaoImpl = (ratesDataDaoImpl) applicationContext.getBean("ratesDataDao");
    boolean result = ratesDataDaoImpl.insertRatesData(ratesData);
    System.out.println(result+":"+ratesData.getTerm_currency()+ratesData.getRate()+ratesData.getDate()+ratesData.getBase_currency());
  }

/*  @Test
  public void testGetRatesDataByTC() throws Exception{

    ratesDataDaoImpl ratesDataDaoImpl =(ratesDataDaoImpl) applicationContext.getBean("ratesDataDao");
    List<RatesData> ratesDatas = ratesDataDaoImpl.getRatesDataByTC("US");
    for (RatesData ratesData:ratesDatas) {
      System.out.println(ratesData.getTerm_currency()+ratesData.getRate()+ratesData.getDate()+ratesData.getBase_currency());
    }
  }*/

  @Test
  public void testGetRatesDataAll() throws Exception{

    ratesDataDaoImpl ratesDataDaoImpl = (ratesDataDaoImpl) applicationContext.getBean("ratesDataDao");
    List<RatesData> ratesDatas= ratesDataDaoImpl.getRatesDataAll();
    for (RatesData ratesData:ratesDatas) {
      System.out.println(ratesData.getTerm_currency()+ratesData.getRate()+ratesData.getDate()+ratesData.getBase_currency());
    }
  }

/*  @Test
  public void testDeleteRatesDataByTC() throws Exception{

    ratesDataDaoImpl ratesDataDaoImpl = (ratesDataDaoImpl) applicationContext.getBean("ratesDataDao");
    boolean result = ratesDataDaoImpl.deleteRatesDataByTC("GBK");
    List<RatesData> ratesDatas= ratesDataDaoImpl.getRatesDataByTC("GBK");
    System.out.println(result+String.valueOf(ratesDatas.size())+String.valueOf(ratesDatas.size()));
  }*/

/*  @Test
  public void testDeleteRatesDataByTCList() throws Exception{

    ratesDataDaoImpl ratesDataDaoImpl = (ratesDataDaoImpl) applicationContext.getBean("ratesDataDao");
    List<String> TCs = new ArrayList<String>();
    TCs.add("JPY");
    TCs.add("USD");
    boolean result = ratesDataDaoImpl.deleteRatesDataByTCList(TCs);
    List<RatesData> ratesDatas= ratesDataDaoImpl.getRatesDataByTC("JPY");
    List<RatesData> ratesData1s= ratesDataDaoImpl.getRatesDataByTC("USD");
    System.out.println(result+String.valueOf(ratesDatas.size())+String.valueOf(ratesData1s.size()));
  }*/

  @Test
  public void testUploadRatesData() throws Exception{

    ratesDataDaoImpl ratesDataDaoImpl =(ratesDataDaoImpl) applicationContext.getBean("ratesDataDao");
    RatesData ratesData = new RatesData(1,"CNH", 3.23, "2018-08-10", "USD");
    boolean result = ratesDataDaoImpl.updateRatesData(ratesData);
    System.out.println(result+String.valueOf(ratesData.getRate()));
  }


  @Test
  public void testInsertPricesData() throws Exception{
    PricesData pricesData = new PricesData("AAPL","2018-08-10",100,"USD");
    pricesDataDaoImpl pricesDataDaoImpl = (pricesDataDaoImpl) applicationContext.getBean("pricesDataDao");
    boolean result = pricesDataDaoImpl.insertPricesData(pricesData);
    System.out.println(result+":"+pricesData.getSymbol()+pricesData.getPrice()+pricesData.getDate());
  }

  @Test
  public void testGetPricesDataBySymbolAndDate() throws Exception{

    pricesDataDaoImpl pricesDataDaoImpl =(pricesDataDaoImpl) applicationContext.getBean("pricesDataDao");
    double price = pricesDataDaoImpl.getPricesDataBySymbolAndDateAndBase("apple","2018-08-11","USD");
    System.out.println(price);
  }

  @Test
  public void testGetPricesDataAll() throws Exception{

    pricesDataDaoImpl pricesDataDaoImpl =(pricesDataDaoImpl) applicationContext.getBean("pricesDataDao");
    List<PricesData> pricesDatas = pricesDataDaoImpl.getPricesDataAll();
    for (PricesData pricesData:pricesDatas) {
      System.out.println(pricesData.getSymbol()+pricesData.getDate().toString()+pricesData.getPrice());
    }
  }
//
//  @Test
//  public void testDeletePricesDataBySymbol() throws Exception{
//
//    pricesDataDaoImpl pricesDataDaoImpl =(pricesDataDaoImpl) applicationContext.getBean("pricesDataDao");
//    boolean result = pricesDataDaoImpl.deletePricesDataBySymbol("AAPL");
//    PricesData pricesData= pricesDataDaoImpl.getPricesDataBySymbol("AAPL");
//    System.out.println(result+pricesData.toString());
//  }
//
//  @Test
//  public void testDeletePricesDataBySymbolList() throws Exception{
//
//    pricesDataDaoImpl pricesDataDaoImpl =(pricesDataDaoImpl) applicationContext.getBean("pricesDataDao");
//    List<String> symbols = new ArrayList<String>();
//    List<PricesData> pricesDatas = new ArrayList<PricesData>();
//    symbols.add("AAPL");
//    symbols.add("C");
//    boolean result = pricesDataDaoImpl.deletePricesDataBySymbolList(symbols);
//    PricesData pricesData0= pricesDataDaoImpl.getPricesDataBySymbol("AAPL");
//    PricesData pricesData1= pricesDataDaoImpl.getPricesDataBySymbol("C");
//    pricesDatas.add(pricesData0);
//    pricesDatas.add(pricesData1);
//    System.out.println(result+String.valueOf(pricesDatas.size())+String.valueOf(pricesDatas.size()));
//  }

  @Test
  public void testUploadPricesData() throws Exception{

    pricesDataDaoImpl pricesDataDaoImpl =(pricesDataDaoImpl) applicationContext.getBean("pricesDataDao");
    PricesData pricesData = new PricesData("GOOG", "2018-08-10",33.23,"USD");
    boolean result = pricesDataDaoImpl.updatePricesData(pricesData);
    System.out.println(result+String.valueOf(pricesData.getPrice()));
  }


  @Test
  public void testInsertFM() throws Exception {
    FundManager fundManager = new FundManager("jq98312", "jing");
    FMDaoImpl fmDao = (FMDaoImpl) applicationContext.getBean("fundmanagerDao");
    boolean result = fmDao.insertFM(fundManager);
    //assertTrue(result);
  }

  @Test
  public void testQueryFM() throws Exception {
    FundManager fundManager = fmDao.queryFM("jq98312");
    //assertEquals("jing", fundManager.getName());
  }

  @Test
  public void testDeletFM() throws Exception {
    FundManager fundManager = fmDao.queryFM("jq98312");
    boolean result = fmDao.deleteFM(fundManager);
    //assertTrue(result);
  }

  @Test
  public void testDeleteManyFM() throws Exception {
    List<String> IDList = new ArrayList<String>();
    String fmID = "jj98312";
    String fmID2 = "yj98727";
    IDList.add(fmID);
    IDList.add(fmID2);

    boolean result = fmDao.deleteManyFM(IDList);
    //assertFalse(result);
  }

  @Test
  public void testUpdateFM() throws Exception {
    FundManager fundManager = fmDao.queryFM("jj98312");
    fundManager.setName("vicky");
    boolean result = fmDao.updateFM(fundManager);
    //assertTrue(result);
    //assertEquals("vicky", fundManager.getName());
  }

  @Test
  public void testCopyPortfolio() throws Exception {
    String fundManagerID = "yj98727";
    int id = 2;

    portfolioDao.copyPortfolio(id, fundManagerID);
    //assertEquals(fundManagerID, portfolioDao.queryPortfolioById(id).getFund_manager_id());
  }


}
