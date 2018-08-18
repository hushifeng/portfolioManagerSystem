package com.team5.Dao;

import com.team5.Pojo.Position;
import java.util.List;

/**
 * Created by ellen on 2018/8/10.
 */
public interface PositionDao {

    public boolean createPosition(Position position) throws Exception;
    public boolean deletePosition(int position_id) throws Exception;
    public boolean deletePositions(List<Integer> position_ids);
    public boolean updatePosition(Position position, double deltQuantity) throws Exception;
    public List<Position> queryPosition(int portfolio_id) throws Exception;
    public List<Position> queryPosition() throws Exception;
    public Position queryPositionById(int position_id);
    public Integer queryQuantityToUpdate(int position_id);
    public double queryTotalPrice(int prtfolio_id, String date);
    public List<Double> changePriceToTerm(String base_currency, String date);
}
