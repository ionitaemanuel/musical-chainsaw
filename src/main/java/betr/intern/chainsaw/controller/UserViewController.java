package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.mapper.UserMapper;
import betr.intern.chainsaw.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserViewController(final UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public String listUsers(final Model model) {
        model.addAttribute(
                "users",
                userService.findAll().stream().map(userMapper::toResponse).toList());
        return "index";
    }
}
