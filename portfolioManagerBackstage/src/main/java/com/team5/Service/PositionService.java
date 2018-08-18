package com.team5.Service;

import com.team5.Pojo.Position;

import java.util.List;

/**
 * Created by ellen on 2018/8/10.
 */
public interface PositionService {
    boolean createPosition(Position position) throws Exception;
    boolean deletePosition(int position_id) throws Exception;
    boolean deletePositions(List<Integer> position_ids);
    boolean updatePosition(Position position, double deltQuantity) throws Exception;
    List<Position> queryPosition(int portfolio_id) throws Exception;
    Position queryPositionById(int id) throws Exception;
    List<Position> queryPosition() throws Exception;
    Integer queryQuantityToUpdate(int position_id) throws Exception;
    //Position queryPositionByIdAndSymbolAndDate(int id, String Symbol, String date);
}
