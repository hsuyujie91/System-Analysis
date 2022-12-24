package com.example.group2.cuporrow.entities.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "user")
public class UserController {
   @Autowired
   private UserService service;

   // 網頁的route
   @GetMapping(value = "/index")
   public String getIndexPage() {
      return "index";
   }

   @GetMapping(value = "/login")
   public String getLoginPage() {
      return "loginpage";
   }

   @GetMapping(value = "/register")
   public String getRegisterPage() {
      return "registerpage";
   }

   @PostMapping(value = "/register")
   public String createUser(@Valid User user, RedirectAttributes redirectAttributes) {
      User newUser = service.register(user.getName(), user.getAccount(), user.getPassword());
      if (newUser != null) {
         return "redirect:login";
      }
      redirectAttributes.addFlashAttribute("message", "User already exist.");
      redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
      return "redirect:register";
   }


   @PostMapping(value = "/login")
   public String login(@Valid User user, HttpSession session) {
      String token = service.loginAndGetToken(user.getAccount(), user.getPassword());
      if (!(token == null)) {
         session.setAttribute("token", token);
         return "rent_page";
      }
      return "Unable to login";
   }

}
