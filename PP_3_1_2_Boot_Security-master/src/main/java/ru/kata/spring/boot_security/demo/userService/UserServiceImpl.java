package ru.kata.spring.boot_security.demo.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleService roleService;
    private ApplicationContext context;

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Transactional
    @Override
    public void save(User user) {
        setEncryptedPassword(user);
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(User updatedUser) {
        setEncryptedPassword(updatedUser);
        userDao.update(updatedUser);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    @Override
    public List<User> getDemandedUsers() {
        return userDao.getDemandedUsers();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User setRolesToUser(User user, int[] rolesIdArr) {
        List<Role> userRoles = new ArrayList<>();

        if (rolesIdArr != null) {
            for (int i : rolesIdArr) {
                userRoles.add(roleService.getRoleById(i));
            }
        }
        user.setRoles(userRoles);
        return user;
    }

    @Override
    public void setEncryptedPassword(User user) {
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
