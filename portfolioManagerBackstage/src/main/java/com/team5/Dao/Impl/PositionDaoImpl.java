package com.team5.Dao.Impl;

import com.team5.Dao.PositionDao;
import com.team5.Pojo.Portfolio;
import com.team5.Pojo.Position;
import com.team5.Pojo.PricesData;
import javafx.geometry.Pos;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ellen on 2018/8/10.
 */
public class PositionDaoImpl extends SqlSessionDaoSupport implements PositionDao {

    @Autowired
    private ratesDataDaoImpl ratesDataDaoImpl;

    @Autowired
    private PortfolioDaoImpl portfolioDaoImpl;

    public boolean createPosition(Position position) throws Exception {

        /*
         * 在准备买入的时候会首先考虑剩下的cash是否足够
         * 1.查找当前position的portfolioID
         * 2.找到当前的cash
         * 2.用当前的term转换成USD，然后从portfolio中减去
         * */
        String date = position.getDate();
        boolean result = false;

        try {
            SqlSession sqlSession = this.getSqlSession();
            Portfolio portfolio = sqlSession.selectOne("queryPortfolioById",position.getPortfolio_id());
            double cash = portfolio.getCash();

            double rate = ratesDataDaoImpl.getRateToPosition(position.getTerm_currency(),"USD",date);
            double value = rate * position.getPrice();
            if (cash < value){
                return false;
            }

            portfolio.setCash(cash - value);
            portfolioDaoImpl.updatePortfolio(portfolio);

            sqlSession.insert("createPosition",position);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when create a position");
        }

        return result;
    }


    public boolean deletePosition(int position_id) throws Exception {
        /*
         * 删除一个position后，将会更新属于的portfolio的cash值
         * 此时需要计算卖出的position的当前的市值步骤：
         * 1.计算当前position的市值------>>>>此时需要计算当天的symbol的price和rate
         *    ------->>>>price通过symbol和date获取，rate通过getRateToPosition函数获取
         * 2.将市值加到portfolio上面的cash中
         * */

        boolean result = false;
        String date = "";
        SqlSession sqlSession = this.getSqlSession();
        try {
            Position position = sqlSession.selectOne("queryPositionById",position_id);
            date = position.getDate();
            PricesData p = new PricesData(position.getSymbol(),position.getDate());

            PricesData p1 = sqlSession.selectOne("queryPriceDateAndSymbol",p);

            double rate = ratesDataDaoImpl.getRateToPosition(p1.getBase_currency(),position.getTerm_currency(),date);
            double delta_price = position.getPrice()  - p1.getPrice() * rate * position.getQuantity();

            //        获取以term_currency为货币的最终price，并且将之转换成USD修改cash的值
            rate = ratesDataDaoImpl.getRateToPosition(position.getTerm_currency(),"USD",date);
            double delta_cash = delta_price * rate;
            Portfolio portfolio = sqlSession.selectOne("queryPortfolioById",position.getPortfolio_id());
            portfolio.setCash(portfolio.getCash()+delta_cash);
            portfolioDaoImpl.updatePortfolio(portfolio);

            //      删除此条记录
            sqlSession.update("deletePosition",position_id);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when delete a position");
        }

        return result;
    }

    public boolean deletePositions(List<Integer> position_ids) {
        boolean result = false;
        try {
            SqlSession sqlSession = this.getSqlSession();
            for (Integer id:position_ids ) {
                sqlSession.update("deletePosition",id);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when delete a position");
        }
        return result;
    }


    public boolean updatePosition(Position position, double pre_quantity) throws Exception {
        /*
         * 在update的时候与delete相似，但是由于不是删除所有的quantity，
         * 删除一个position后，将会更新属于的portfolio的cash值
         * 此时需要计算卖出的position的当前的市值步骤：
         * 1.计算当前position的市值———>>>>此时需要计算当天的symbol的price和rate
         *    ———>>>>price通过symbol和date获取，rate通过getRateToPosition函数获取
         * 2.将市值加到portfolio上面的cash中
         * */

        System.out.println("deltQuantity:"+pre_quantity);
        String date = "";
        SqlSession sqlSession = this.getSqlSession();
        PricesData p = new PricesData(position.getSymbol(),position.getDate());

        PricesData p1 = sqlSession.selectOne("queryPriceDateAndSymbol",p);

        double rate = ratesDataDaoImpl.getRateToPosition(p1.getBase_currency(),position.getTerm_currency(),date);
        double delta_price = position.getPrice()  - p1.getPrice() * rate * (pre_quantity - position.getQuantity());

        //        获取以term_currency为货币的最终price，并且将之转换成USD修改cash的值
        rate = ratesDataDaoImpl.getRateToPosition(position.getTerm_currency(),"USD",date);
        double delta_cash = delta_price * rate;
        Portfolio portfolio = sqlSession.selectOne("queryPortfolioById",position.getPortfolio_id());
        portfolio.setCash(portfolio.getCash()+delta_cash);
        portfolioDaoImpl.updatePortfolio(portfolio);


        boolean result = false;
        try {
            if (pre_quantity == position.getQuantity()){
                sqlSession.update("deletePosition",position.getId());
            }else {
                sqlSession.update("updatePosition", position);
            }
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when update a position");
        }
        return result;
    }

    public List<Position> queryPosition(int portfolio_id) throws Exception {
        List<Position> result = null;
        try {
            SqlSession sqlSession = this.getSqlSession();
            result = sqlSession.selectList("queryPosition",portfolio_id);
            /**System.out.println(result.size());
            for (Position p:result ) {
                System.out.println(p.toString());
            }*/
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when query  positions by portfolio id");
        }

        return result;
    }

    public List<Position> queryPosition() throws Exception {
        List<Position> result = null;
        try {
            SqlSession sqlSession = this.getSqlSession();
            result = sqlSession.selectList("queryPositionAll");
            /**System.out.println(result.size());
             for (Position p:result ) {
             System.out.println(p.toString());
             }*/
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when query  positions by portfolio id");
        }

        return result;
    }

    public Position queryPositionById(int position_id) {
        Position position = null;
        try{
            SqlSession sqlSession = this.getSqlSession();
            position = sqlSession.selectOne("queryPositionById",position_id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when query a position by id");
        }
        return position;
    }

    public Integer queryQuantityToUpdate(int position_id) {
        int  quantity = 0;
        try{
            SqlSession sqlSession = this.getSqlSession();
            quantity = sqlSession.selectOne("queryQuantityToUpdate",position_id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error when query the quantity with a position id");
        }
        return quantity;
    }

    public List<Double> changePriceToTerm(String base_currency, String date) {

        List<Double> rates = new ArrayList<Double>();

        SqlSession sqlSession = this.getSqlSession();
        Position p_today = new Position(base_currency, date);
        List<Position> positions_today = sqlSession.selectList("queryPositionsToPrice", p_today);

        String yestoday = dateHelper(date);

        Position p_yestoday = new Position(base_currency,yestoday);
        List<Position> positions_yestoday = sqlSession.selectList("queryPositionsToPrice",p_yestoday);
        if (positions_yestoday.size() ==0 ){
            return null;
        }

        double rate_today = 0;
        double rate_yestoday = 0;
        for (int i = 0; i < positions_today.size(); i++) {
            rate_today = ratesDataDaoImpl.getRateToPosition(positions_today.get(i).getBase_currency(),"USD",date);
            rate_yestoday = ratesDataDaoImpl.getRateToPosition(positions_yestoday.get(i).getBase_currency(), "USD",yestoday);
            rates.add(rate_today - rate_yestoday);
        }
        return rates;
    }

    public double queryTotalPrice(int prtfolio_id, String date) {
        double sum  = 0;
        Position p = new Position(prtfolio_id, date);
        List<Position> list = new ArrayList<Position>();
        try{
            SqlSession sqlSession = this.getSqlSession();
            list = sqlSession.selectList("test.queryTotalPrice",p);
            for (Position psition :list) {
                sum += psition.getPrice();
            }
        }catch (Exception e){
            e.printStackTrace();
//            logger.error("Error when query the quantity with a position id");
        }
        return sum;
    }

    public static String dateHelper(String date){

        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = myFmt.parse(date);
        }catch (Exception e){
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, -1);// 日期减1年
        return myFmt.format(cal.getTime());
    }

}
