package com.team5.Dao.Impl;

import com.team5.Dao.ratesDataDao;
import com.team5.Pojo.RatesData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * Created by ellen on 2018/8/13.
 */
@Slf4j
public class ratesDataDaoImpl extends SqlSessionDaoSupport implements ratesDataDao {

  public boolean insertRatesData(RatesData ratesData) throws Exception{
    boolean result = false;
    SqlSession sqlSession = this.getSqlSession();

    try{
      sqlSession.insert("test.insertRatesData", ratesData);
      result=true;
      //sqlSession.commit();
      log.info("Insert a new RatesData {} has been Successful", ratesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Insert a new RatesData {} has been Failed", ratesData.toString(),e);
    }

    return result;
  }

  public List<RatesData> getRatesDataByTCBCDate(String termCurrency, String baseCurrency, String date) throws Exception{
    SqlSession sqlSession = this.getSqlSession();
    List<RatesData> ratesDatas = new ArrayList<RatesData>();
    try{
      ratesDatas = sqlSession.selectList("test.getRatesDataByTCBCDate",termCurrency);

      //sqlSession.close();
      //sqlSession.selectOne()
      log.info("Find ratesData {} By term_currency {} has been Successful", ratesDatas.toString(), termCurrency, baseCurrency, date);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Find a ratesData {} By term_currency {} has been Failed", ratesDatas.toString(),termCurrency, baseCurrency, date);
    }

    return ratesDatas;
  }

  public List<RatesData> getRatesDataAll() throws Exception{
    SqlSession sqlSession = this.getSqlSession();
    List<RatesData> ratesDatas = new ArrayList<RatesData>();

    try{
      ratesDatas = sqlSession.selectList("test.getRatesDataAll");
      //sqlSession.close();
      log.info("Find all the ratesData {} has been Successful", ratesDatas.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Find all the ratesData {} has been Failed", ratesDatas.toString());
    }

    return ratesDatas;
  }

  public boolean deleteRatesDataByDateBCTC(String date, String BC, String TC) throws Exception{
    SqlSession sqlSession = this.getSqlSession();
    boolean result = false;
    RatesData ratesData = new RatesData(BC, TC, date);

    try{
      result= sqlSession.delete("test.deleteRatesDataByDateBCTC",ratesData)>0;
      //sqlSession.close();

      log.info("Delete the ratesData By Input {} has been Successful", ratesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Delete the ratesData By Input {} has been Failed", ratesData.toString(), e);
    }

    return result;
  }

  public boolean deleteRatesDataByTCList(List<RatesData> TCs) throws Exception{
    SqlSession sqlSession = this.getSqlSession();
    boolean result = true;

    try{
      for (RatesData s:TCs) {
        if(!deleteRatesDataByDateBCTC(s.getDate(),s.getBase_currency(),s.getTerm_currency())){
          result=false;
        }
      }

      //sqlSession.close();
      log.info("Delete the ratesData By TempCurrency {} has been Successful",TCs);
    }catch (Exception e){
      e.printStackTrace();
      log.error("Delete all the ratesData By TempCurrency {} has been Failed", TCs, e);
    }

    return result;
  }

  public boolean updateRatesData(RatesData ratesData) throws Exception{
    SqlSession sqlSession = this.getSqlSession();
    boolean result = false;

    try{
      result = sqlSession.update("test.updateRatesData",ratesData) > 0;
      //sqlSession.close();

      log.info("Update the ratesData {} has been Successful",ratesData.toString());
    }catch (Exception e){
      e.printStackTrace();
      log.error("Update the ratesData {} has been Failed", ratesData.toString(), e);
    }

    return result;
  }

  public double getRateToPosition(String base_currency, String term_currency, String date) {

    /*
     * 1.首先通过传入的base_currency和term_currency找出是否存在数据，如果存在，则直接返回当前rate；
     *   并且装换两种的位置，如果存在相反的就用1/rate作为新的rate返回；
     * 2.如果当前不存在这样的数据，就再次寻找新的三方转换策略；
     * */

    System.out.println("get the params base:"+base_currency+"  term:"+term_currency+" date:"+date);

    SqlSession sqlSession = this.getSqlSession();
    RatesData result = null;
    RatesData rate = new RatesData(base_currency, date ,term_currency);

//  rate中存在匹配的curr对
    result = sqlSession.selectOne("getRateDataByDateAndBaseAndTerm", rate);
    if (result != null) {
      return result.getRate();
    }

//  rate中存在相反的curr对
    rate = new RatesData(term_currency, date ,base_currency);
    result = sqlSession.selectOne("getRateDataByDateAndBaseAndTerm", rate);
    if (result != null) {
      return 1 / result.getRate();
    }


    /*
     * rate中不存在匹配和相反的curr对，所以需要通过三方进行反转；
     * 1.首先查找rate中所有base为传入的base_currency的所有数据，然后记录下所有的此时查找到的term_currency
     *   数据和相应的rate，然后在用这些term_currency作为新一轮的base去查询rate中所有base_currency为base的
     *   term_currency,直至找到这个传入进来的term_currency(所有的查询都将加入一个date)
     * */

    rate = new RatesData(base_currency, date, term_currency);

    List<RatesData> rates = sqlSession.selectList("getRateDataByDateAndBase", rate);
    for (RatesData r : rates) {
      String new_base = r.getTerm_currency();
      double first_rate = r.getRate();

//    rate中存在匹配的curr对
      base_currency = new_base;
      RatesData new_rate = new RatesData(base_currency, date, term_currency);
      result = sqlSession.selectOne("getRateDataByDateAndBaseAndTerm", new_rate);
      if (result != null) {
        return result.getRate() * first_rate;
      }

      String temp = term_currency;
      term_currency = base_currency;
      base_currency = temp;
//    rate中存在相反的curr对
      rate = new RatesData(base_currency, date, term_currency);
      result = sqlSession.selectOne("getRateDataByDateAndBaseAndTerm", rate);
      if (result != null) {
        return 1 / result.getRate() * first_rate;
      }

    }

    return 0;
  }
}

