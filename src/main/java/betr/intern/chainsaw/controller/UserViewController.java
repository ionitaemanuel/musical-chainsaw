package betr.intern.chainsaw.controller;

import betr.intern.chainsaw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    private final UserService userService;

    @Autowired
    public UserViewController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String listUsers(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "index";
    }
}
