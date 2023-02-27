package net.therap.estaurant.service;

import net.therap.estaurant.dao.RestaurantTableDao;
import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class RestaurantTableService {

    @Autowired
    public RestaurantTableDao restaurantTableDao;

    public List<RestaurantTable> findByWaiterId(int id) {
        return restaurantTableDao.findByWaiterId(id);
    }

    public List<RestaurantTable> findAll() {
        return restaurantTableDao.findAll();
    }

    public RestaurantTable findById(int id) {
        return Optional.ofNullable(restaurantTableDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void delete(int id) throws Exception {
        restaurantTableDao.delete(id);
    }

    @Transactional
    public RestaurantTable saveOrUpdate(RestaurantTable restaurantTable) throws Exception {
        return restaurantTableDao.saveOrUpdate(restaurantTable);
    }

    public boolean isDuplicateTable(RestaurantTable restaurantTable) {
        return restaurantTableDao.isDuplicateTable(restaurantTable);
    }
}
