package com.team5.Controller;

import com.team5.Dao.Impl.pricesDataDaoImpl;
import com.team5.Dao.Impl.ratesDataDaoImpl;
import com.team5.Pojo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.team5.Service.PositionService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

/**
 * Created by mac on 2018/8/11.
 */
@Controller
@RequestMapping(value = "api/position/")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private ratesDataDaoImpl ratesDataDao;

    @Autowired
    private pricesDataDaoImpl pricesDataDao;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }


//    test true
    @RequestMapping(value = "/queryPositionById",method = RequestMethod.GET)
    public @ResponseBody
    Position queryPositionById(@RequestParam int id) throws Exception{
        return positionService.queryPositionById(id);
    }


//    test true
    @RequestMapping(value = "/queryPosition",method = RequestMethod.GET)
    @ResponseBody
    public  Map<String,Object> queryPosition(@RequestParam int portfolio_id) throws Exception {
        List<Position> results = positionService.queryPosition(portfolio_id);
        Map<String,Object> map = new HashMap<String, Object>(2);
        map.put("total",results.size());
        map.put("positions",results);
        return map;
    }

    //    test true
    @RequestMapping(value = "/queryPositionAll",method = RequestMethod.GET)
    @ResponseBody
    public  Map<String,Object> queryPosition() throws Exception {
        List<Position> results = positionService.queryPosition();
        Map<String,Object> map = new HashMap<String, Object>(2);
        map.put("total",results.size());
        map.put("positions",results);
        return map;
    }


// test true
    @RequestMapping(value = "/deletePosition",method = RequestMethod.GET)
    @ResponseBody
    public boolean deletePosition(@RequestParam int position_id) throws Exception {
        return positionService.deletePosition(position_id);
    }



    @RequestMapping(value="/deletePositions",method = RequestMethod.GET)
    @ResponseBody
    public boolean deletePositions(@RequestParam List<Integer> ids){
        return positionService.deletePositions(ids);
    }


//  test true
    @RequestMapping(value = "/updatePosition",method = RequestMethod.GET)
    @ResponseBody
    public boolean updatePosition(@RequestParam int  id,
                                  @RequestParam String symbol,
                                  @RequestParam int quantity ,
                                  @RequestParam double price,
                                  @RequestParam int portfolio_id,
                                  @RequestParam String base_currency,
                                  @RequestParam String term_currency,
                                  @RequestParam String date
                                  ) throws Exception {

//      get the pre quantity and use (quantity / pre_quantity) * price
//      to get the new price


        int pre_quantity = positionService.queryQuantityToUpdate(id);
        System.out.println("pre quantity:"+pre_quantity);
        double new_price = ((float)quantity / pre_quantity) * price;
        Position p =  new Position(id,symbol, quantity, new_price, portfolio_id, base_currency, term_currency, date);
        return positionService.updatePosition(p,pre_quantity);
    }


//    test false
    @RequestMapping(value = "/createPosition",method = RequestMethod.GET)
    @ResponseBody
    public boolean createPosition(@RequestParam String symbol,
                                  @RequestParam int quantity ,
                                  @RequestParam int portfolio_id,
                                  @RequestParam String term_currency,
                                  @RequestParam String base_currency,
                                  @RequestParam String date
                                  ) throws Exception {
//      get the rate from rates_data table
//      double rate = ratesDataDao.getRateData();
//        double rate = 1;
//      get the price data from prices_data table
        double symbol_price =pricesDataDao.getPricesDataBySymbolAndDateAndBase(symbol,date,base_currency);

        System.out.println("get the symbol_price:"+symbol_price);

        double rate = ratesDataDao.getRateToPosition(base_currency,term_currency,date);

        System.out.println("the rate base:EUR to term USD is:"+rate);
        double price = rate * symbol_price * quantity;

        System.out.println("the price is :"+price);

        return positionService.createPosition(new Position(symbol,quantity,price,portfolio_id,base_currency, term_currency,date));
    }
}
