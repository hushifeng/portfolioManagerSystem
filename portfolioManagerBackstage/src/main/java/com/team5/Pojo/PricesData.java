package com.team5.Pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by ellen on 2018/8/10.
 */
@Data
//@AllArgsConstructor
@ToString
@NoArgsConstructor
public class PricesData implements Serializable {

  private String symbol;
  private String date;
  private double price;
  private String base_currency;

  public PricesData(String symbol, String date, double price, String base_currency) {
    this.symbol = symbol;
    this.date = date;
    this.price = price;
    this.base_currency = base_currency;
  }

  public PricesData(String symbol, String date) {
    this.symbol = symbol;
    this.date = date;
  }

  public PricesData(String symbol, double price, String date) {
    this.symbol = symbol;
    this.price = price;
    this.date = date;
  }

  public PricesData(String symbol, String date, String base_currency) {
    this.symbol = symbol;
    this.date = date;
    this.base_currency = base_currency;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
