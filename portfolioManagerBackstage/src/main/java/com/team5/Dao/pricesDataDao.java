package com.team5.Dao;

import com.team5.Pojo.PricesData;
import java.util.List;

/**
 * Created by ellen on 2018/8/13.
 */
public interface pricesDataDao {

  public boolean insertPricesData(PricesData pricesData) throws Exception;
  public List<PricesData> getPricesDataAll() throws Exception;
  public double getPricesDataBySymbolAndDateAndBase(String symbol, String date, String base_currency)throws Exception;
  public double getPricesDataBySymbolAndDate(String symbol, String date, String base_currency)throws Exception;
  public List<PricesData>  getPricesDataBySymbol(String symbol) throws Exception;
  public boolean deletePricesDataBySymbol(String symbol) throws Exception;
  public boolean deletePricesDataBySymbolList(List<String> symbols) throws Exception;
  public boolean updatePricesData(PricesData pricesData) throws Exception;
}
