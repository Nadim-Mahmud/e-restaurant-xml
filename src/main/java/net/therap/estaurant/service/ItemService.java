package net.therap.estaurant.service;

import net.therap.estaurant.dao.ItemDao;
import net.therap.estaurant.entity.Item;
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
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderLineItemService orderLineItemService;

    public List<Item> findAvailable() {
        return itemDao.findAvailable();
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item findById(int id) {
        return Optional.ofNullable(itemDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void delete(int id) throws Exception {
        itemDao.delete(id);
    }

    @Transactional
    public Item saveOrUpdate(Item item) throws Exception {
        return itemDao.saveOrUpdate(item);
    }

    public boolean isExistingItem(Item item) {
        return itemDao.isExistingItem(item);
    }
}
