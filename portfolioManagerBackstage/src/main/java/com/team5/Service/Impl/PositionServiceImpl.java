package com.team5.Service.Impl;

import com.team5.Pojo.Position;
import com.team5.Dao.PositionDao;
import com.team5.Service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Created by ellen on 2018/8/10.
 */
@Service
public class PositionServiceImpl implements PositionService {



//    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//    com.team5.Dao.Impl.PositionDaoImpl PositionDaoImpl = (com.team5.Dao.Impl.PositionDaoImpl) applicationContext.getBean("PositionDao");

    @Autowired
    private PositionDao positionDao;
    public boolean createPosition(Position position) throws Exception {
        return positionDao.createPosition(position);
    }

    public boolean deletePosition(int position_id) throws Exception {
        return positionDao.deletePosition(position_id);
    }

    public boolean deletePositions(List<Integer> position_ids) {
        return positionDao.deletePositions(position_ids);
    }

    public boolean updatePosition(Position position,double deltQuantity) throws Exception {
        return positionDao.updatePosition(position, deltQuantity);
    }

    public List<Position> queryPosition(int portfolio_id) throws Exception {
        return positionDao.queryPosition(portfolio_id);
    }

    public Position queryPositionById(int id) {
        return positionDao.queryPositionById(id);
    }

    public List<Position> queryPosition() throws Exception {
        return positionDao.queryPosition();
    }

    public Integer queryQuantityToUpdate(int position_id){
        return positionDao.queryQuantityToUpdate(position_id);
    }
}
