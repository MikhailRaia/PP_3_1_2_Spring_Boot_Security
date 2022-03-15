package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.userService.RoleService;
import ru.kata.spring.boot_security.demo.userService.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        List<User> demandedUserList = userService.getDemandedUsers();
        model.addAttribute("keyValue", demandedUserList);

        return "usersView";
    }

    @GetMapping("/admin/users/new")
    public String newCar(Model model) {
        List<Role> rolesList = roleService.getDemandedRoles();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("rolesList", rolesList);
        return "new";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr", required = false) int[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.save(updatedUser);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        User user1 = userService.getUserById(id);
        List<Role> rolesList = roleService.getDemandedRoles();
        model.addAttribute("rolesList", rolesList);
        model.addAttribute("user", user1);
        return "edit";
    }

    @PatchMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesIdArr", required = false) int[] rolesIdArr) {
        User updatedUser = userService.setRolesToUser(user, rolesIdArr);
        userService.update(updatedUser);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(Model model, @PathVariable("id") Integer id) {
        User user1 = userService.getUserById(id);
        model.addAttribute("user", user1);
        return "delete";
    }

    @DeleteMapping("/admin/users/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/admin/users/";
    }

    @GetMapping("/admin")
    public String pageRedirect(Principal principal) {
        return "redirect:/admin/users/";
    }
}