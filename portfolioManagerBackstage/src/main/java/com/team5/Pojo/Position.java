package com.team5.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@ToString
@Data
@AllArgsConstructor
public class Position {
    private int id;
    private String symbol;
    private int quantity;
    private double price;
    private int portfolio_id;
    private String base_currency;
    private String term_currency;
    private String date;

    public Position(int portfolio_id, String symbol, String date) {
        this.symbol = symbol;
        this.portfolio_id = portfolio_id;
        this.date = date;
    }

  public Position(String base_currency, String date) {
    this.base_currency = base_currency;
    this.date = date;
  }

  public Position(int portfolio_id, String date) {
    this.portfolio_id = portfolio_id;
    this.date = date;
  }

  public Position(String symbol, int quantity, double price, int portfolio_id, String base_currency, String term_currency, String date) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.portfolio_id = portfolio_id;
        this.base_currency = base_currency;
        this.term_currency = term_currency;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Position() {
    }
}
