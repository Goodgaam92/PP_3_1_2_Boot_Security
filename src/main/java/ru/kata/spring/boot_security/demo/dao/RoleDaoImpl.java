package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDAO {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Role> getAllRoles() {
        List<Role> roleList = entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        return new LinkedHashSet<>(roleList);
    }

    @Override
    public Role saveRole(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.roleName = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Set<Role> findByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }

        List<Role> rolesList = entityManager.createQuery("SELECT r FROM Role r WHERE r.id IN :ids", Role.class)
                .setParameter("ids", ids)
                .getResultList();
        return new HashSet<>(rolesList);
    }


    @Override
    public void deleteRoleById(Long id) {
        entityManager.createQuery("delete Role where id = :roleId")
                .setParameter("roleId", id)
                .executeUpdate();
    }


}
