package com.team5.Dao;

import com.team5.Pojo.RatesData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ellen on 2018/8/13.
 */
public interface ratesDataDao {

  public boolean insertRatesData(RatesData RatesData) throws Exception;

  public List<RatesData> getRatesDataByTCBCDate(String termCurrency, String baseCurrency, String date) throws Exception;

  public List<RatesData> getRatesDataAll() throws Exception;

  public boolean deleteRatesDataByDateBCTC(String date, String BC, String TC) throws Exception;

  public boolean deleteRatesDataByTCList(List<RatesData> TCs) throws Exception;

  public boolean updateRatesData(RatesData ratesData) throws Exception;
  public double getRateToPosition(String base_currency, String term_currency, String date) throws Exception;
}
