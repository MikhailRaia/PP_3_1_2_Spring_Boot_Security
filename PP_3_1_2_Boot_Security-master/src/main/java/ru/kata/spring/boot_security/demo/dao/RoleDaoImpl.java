package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Role getRoleById(Integer id) {
        return entityManager.find(Role.class, id);
    }

    public void save(Role role) {
        entityManager.persist(role);
    }

    public void update(Role updatedRole) {
        entityManager.merge(updatedRole);
    }

    public void delete(Integer id) {
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Override
    public List<Role> getDemandedRoles() {
        List<Role> list1 = entityManager.createQuery("select role from Role role", Role.class).getResultList();
        return list1;
    }
}