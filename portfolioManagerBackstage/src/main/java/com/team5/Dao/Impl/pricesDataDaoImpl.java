package com.team5.Dao.Impl;

import com.team5.Dao.pricesDataDao;
import com.team5.Pojo.PricesData;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/13.
 */
@Slf4j
@Service
public class pricesDataDaoImpl extends SqlSessionDaoSupport implements pricesDataDao {

  @Autowired
  private ratesDataDaoImpl ratesDataDaoImpl;
  public boolean insertPricesData(PricesData pricesData) throws Exception{
    boolean result=false;

    SqlSession sqlSession = this.getSqlSession();

    try{
      sqlSession.insert("test.insertPricesData", pricesData);
      result=true;
      //sqlSession.close();
      //sqlSession.commit();
      log.info("Insert a new PricesData {} has been Successful", pricesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Insert a new PricesData {} has been Failed", pricesData.toString(),e);
    }
    return result;
  }

  public List<PricesData> getPricesDataAll() throws Exception{
    List<PricesData> pricesDatas = new ArrayList<PricesData>();

    SqlSession sqlSession = this.getSqlSession();

    try{
      pricesDatas = sqlSession.selectList("test.getPricesDataAll");
      //sqlSession.close();
      //sqlSession.commit();
      log.info("Find all the PricesData {} has been Successful", pricesDatas.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Find all the PricesData {} has been Failed", pricesDatas.toString(),e);
    }

    return pricesDatas;
  }

  public double getPricesDataBySymbolAndDateAndBase(String symbol, String date, String base_currency) throws Exception{
    PricesData result = new PricesData();
    PricesData p = new PricesData(symbol, date, base_currency);

    SqlSession sqlSession = this.getSqlSession();

    try{
      result = sqlSession.selectOne("test.getPricesDataBySymbolAndDateAndBase",p);
      //sqlSession.close();
      //sqlSession.commit();
      log.info("Find the PricesData {} By symbol {} has been Successful", result.toString(),symbol);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Find the PricesData {} By {} has been Failed", result.toString(),symbol,e);
    }
    return result.getPrice();
  }
  public double getPricesDataBySymbolAndDate(String symbol, String date, String base_currency) throws Exception{
    PricesData result = new PricesData();
    PricesData p = new PricesData(symbol, date, base_currency);

    double rate = 0.0;
    double price = 0.0;
    String new_base_currency;
    SqlSession sqlSession = this.getSqlSession();

    try{
      result = sqlSession.selectOne("test.getPricesDataBySymbolAndDate",p);
      new_base_currency = result.getBase_currency();
      price = result.getPrice();
      rate = ratesDataDaoImpl.getRateToPosition(new_base_currency,base_currency,date);
      //sqlSession.close();
      //sqlSession.commit();
      log.info("Find the PricesData {} By symbol {} has been Successful", result.toString(),symbol);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Find the PricesData {} By {} has been Failed", result.toString(),symbol,e);
    }
    return price*rate;
  }

  public List<PricesData> getPricesDataBySymbol(String symbol) throws Exception{
    List<PricesData> result = new ArrayList<PricesData>();
    SqlSession sqlSession = this.getSqlSession();

    try{
      result = sqlSession.selectOne("test.getPricesDataBySymbolAndDateAndBase",symbol);
      log.info("Find the PricesData {} By symbol {} has been Successful", result.toString(),symbol);

    }catch (Exception e){
      e.printStackTrace();
      log.error("Find the PricesData {} By {} has been Failed", result.toString(),symbol,e);
    }

    return result;
  }

  public boolean deletePricesDataBySymbol(String symbol) throws Exception{
    boolean result=false;

    SqlSession sqlSession = this.getSqlSession();

    try{
      sqlSession.delete("test.deletePricesDataBySymbol", symbol);
      result=true;
      //sqlSession.close();
      //sqlSession.commit();
      log.info("Delete the PricesData By Symbol {} has been Successful", symbol);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Delete the PricesData By Symbol {} has been Failed", symbol, e);
    }
    return result;
  }

  public boolean deletePricesDataBySymbolList(List<String> symbols) throws Exception{
    boolean result=false;
    int num=0;

    SqlSession sqlSession = this.getSqlSession();

    try{
      for (String s: symbols) {
        num += sqlSession.delete("test.deletePricesDataBySymbol", s);
      }

      if(num==symbols.size()){result=true;}

      //sqlSession.close();
      //sqlSession.commit();
      log.info("Delete some PricesData By Symbols {} have been Successful", symbols);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Delete some PricesData By Symbols {} have been Failed", symbols, e);
    }
    return result;
  }

  public boolean updatePricesData(PricesData pricesData) throws Exception{
    boolean result=false;

    SqlSession sqlSession = this.getSqlSession();

    try{
      result = sqlSession.update("test.updatePricesData", pricesData)>0;
      //sqlSession.close();

      log.info("Update the PricesData {} has been Successful", pricesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Update the PricesData {} has been Failed", pricesData.toString(), e);
    }
    return result;
  }
}
