package com.team5.Pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by ellen on 2018/8/10.
 */
@ToString
@Data
@AllArgsConstructor
/*@NoArgsConstructor*/
public class Portfolio implements Serializable{

  private int id;
  private String name;
  private String fund_manager_id;
  private double cash;
  private double initial_cash;

  public double getInitial_cash() {
    return initial_cash;
  }

  public void setInitial_cash(double initial_cash) {
    this.initial_cash = initial_cash;
  }

  public Portfolio(String name, String fund_manager_id, double initial_cash) {
    this.name = name;
    this.fund_manager_id = fund_manager_id;
    this.initial_cash=initial_cash;
  }

  public Portfolio(String name, String fund_manager_id, double cash, double initial_cash) {
    this.name = name;
    this.fund_manager_id = fund_manager_id;
    this.cash = cash;
    this.initial_cash=initial_cash;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFund_manager_id() {
    return fund_manager_id;
  }

  public void setFund_manager_id(String fund_manager_id) {
    this.fund_manager_id = fund_manager_id;
  }

  public double getCash() {
    return cash;
  }

  public void setCash(double cash) {
    this.cash = cash;
  }


  public Portfolio(int id, String name, String fund_manager_id, double initcash) {
    this.id = id;
    this.name = name;
    this.fund_manager_id = fund_manager_id;
    this.initial_cash = initcash;
  }

  public Portfolio() { }
}
