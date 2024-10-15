package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleDAO roleRepository;

    @Autowired
    public RoleServiceImpl(RoleDAO roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Операция только для чтения
    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    // Операция изменения данных
    @Override
    @Transactional
    public Role saveRole(Role role) {
        return roleRepository.saveRole(role);
    }

    // Операция только для чтения
    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long id) {
        return roleRepository.getRoleById(id);
    }

    // Операция изменения данных
    @Override
    @Transactional
    public void deleteRoleById(Long id) {
        roleRepository.deleteRoleById(id);
    }

    // Операция изменения данных
    @Override
    @Transactional
    public Set<Role> findDyIds(Set<Long> ids) {
        return roleRepository.findByIds(ids);
    }
}
