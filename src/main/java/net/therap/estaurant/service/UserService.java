package net.therap.estaurant.service;

import net.therap.estaurant.command.Credentials;
import net.therap.estaurant.command.Profile;
import net.therap.estaurant.dao.UserDao;
import net.therap.estaurant.entity.AccessStatus;
import net.therap.estaurant.entity.RestaurantTable;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.entity.UserType;
import net.therap.estaurant.exception.ResourceNotFoundException;
import net.therap.estaurant.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public List<User> findChef() {
        return userDao.findChef();
    }

    public List<User> findWaiter() {
        return userDao.findWaiter();
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(int id) {
        return Optional.ofNullable(userDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
    }

    public User findByEmail(String email) throws Exception {
        User user = userDao.findByEmail(email);

        if (Objects.isNull(user)) {
            return new User();
        }

        return user;
    }

    @Transactional
    public void delete(int id) throws Exception {
        User user = userDao.findById(id);
        user.setAccessStatus(AccessStatus.DELETED);

        if (user.getType().equals(UserType.WAITER)) {

            for (RestaurantTable restaurantTable : user.getRestaurantTableList()) {
                restaurantTable.setUser(null);
            }
        }

        userDao.saveOrUpdate(user);
    }

    @Transactional
    public User saveOrUpdate(User user) throws Exception {
        return userDao.saveOrUpdate(user);
    }

    public boolean isValidCredential(Credentials credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User user = userDao.findByEmail(credentials.getEmail());

        return Objects.nonNull(user) && user.getPassword().equals(Encryption.getPBKDF2(credentials.getPassword()));
    }

    public boolean isDuplicateEmail(User user) {
        return userDao.isDuplicateEmail(user);
    }

    public Profile getProfileObject(User user) {
        return new Profile(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getEmail());
    }

    public User updateUserByProfile(User user, Profile profile) {
        user.setId(profile.getId());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setDateOfBirth(profile.getDateOfBirth());
        user.setEmail(profile.getEmail());

        return user;
    }
}
