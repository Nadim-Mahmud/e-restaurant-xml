package net.therap.estaurant.service;

import net.therap.estaurant.dao.CategoryDao;
import net.therap.estaurant.entity.Category;
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
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ItemService itemService;

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category findById(int id) {
        return Optional.ofNullable(categoryDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    public void delete(int id) throws Exception {
        Category category = categoryDao.findById(id);

        for (Item item : category.getItemList()) {
            itemService.delete(item.getId());
        }

        categoryDao.delete(id);
    }

    @Transactional
    public Category saveOrUpdate(Category category) throws Exception {
        return categoryDao.saveOrUpdate(category);
    }

    public boolean isExistingCategory(Category category) {
        return categoryDao.isExistingCategory(category);
    }
}
