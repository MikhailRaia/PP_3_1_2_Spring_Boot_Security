package ru.kata.spring.boot_security.demo.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Transactional
    @Override
    public void update(Role updatedRole) {
        roleDao.update(updatedRole);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        roleDao.delete(id);
    }

    @Override
    public List<Role> getDemandedRoles() {
        return roleDao.getDemandedRoles();
    }
}