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
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RatesData implements Serializable {

  private int id;
  private String term_currency;
  private double rate;
  private String date;
  private String base_currency;

  public RatesData(String base_currency, String term_currency, String date) {
    this.term_currency = term_currency;
    this.date = date;
    this.base_currency = base_currency;
  }

  public RatesData(String term_currency, double rate, String date, String base_currency) {
    this.term_currency = term_currency;
    this.rate = rate;
    this.date = date;
    this.base_currency = base_currency;
  }

  public String getTerm_currency() {
    return term_currency;
  }

  public void setTerm_currency(String term_currency) {
    this.term_currency = term_currency;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }


  public String getBase_currency() {
    return base_currency;
  }

  public void setBase_currency(String base_currency) {
    this.base_currency = base_currency;
  }

}
