package com.JavaTech.PointOfSales.controller;

import com.JavaTech.PointOfSales.utils.FileUploadUtil;
import com.JavaTech.PointOfSales.model.*;
import com.JavaTech.PointOfSales.repository.ConfirmationTokenRepository;
import com.JavaTech.PointOfSales.repository.RoleRepository;
import com.JavaTech.PointOfSales.repository.UserRepository;
import com.JavaTech.PointOfSales.service.impl.EmailServiceImpl;
import com.JavaTech.PointOfSales.service.UserService;
import com.JavaTech.PointOfSales.utils.ImageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailServiceImpl emailServiceImpl;



    @GetMapping(value = "/list")
    public String listUser(Model model){
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);
        return "/users/page-list-users";
    }

    @GetMapping(value = "/add")
    public String addUser(){
        return "/users/page-add-users";
    }

    @PostMapping(value = "/add")
    public String addPostUser(  @RequestParam(name = "avatar", required = false) MultipartFile avatar,
                                @Valid @RequestParam("fullName") String fullName,
                                @RequestParam("phone") String phone,
                                @RequestParam("email") String email,
                                @RequestParam("gender") String gender,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("active") String active,
                                @RequestParam("role") String role,
                                @RequestParam("sendEmail") boolean sendEmail) throws IOException {

        //avatar
//        String fileName;
//        if (avatar != null && !avatar.isEmpty()) {
//            fileName = fullName.trim()+".jpg";
//            FileUploadUtil.saveFile(fileName, avatar);
//        } else {
//            String defaultImageFilePath = "static/assets/images/user/01.jpg";
//            ClassPathResource resource = new ClassPathResource(defaultImageFilePath);
//            byte[] defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//
//            MultipartFile defaultImageFile = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//            fileName = fullName.trim()+".jpg";
//
//            FileUploadUtil.saveFile(fileName, defaultImageFile);
//        }

        if(avatar == null || !avatar.isEmpty()){
            String defaultImageFilePath = "static/assets/images/user/01.jpg";
            ClassPathResource resource = new ClassPathResource(defaultImageFilePath);
            byte[] defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            avatar = new MockMultipartFile("default-avatar.png", defaultImageBytes);
        }

        //role
        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow();
        roles.add((role.equals("Admin"))?adminRole:userRole);

        //branch
        Optional<User> info = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User admin = null;
        if(info.isPresent()){
            admin = info.get();
        }
        assert admin != null;
        Branch branch = admin.getBranch();

        //add
        User user = User.builder()
                .fullName(fullName)
                .avatar(ImageUtil.convertToBase64(avatar))
                .phone(phone)
                .email(email)
                .gender(gender)
                .username(username)
                .firstLogin(true)
                .unlocked(true)
                .branch(branch)
                .password(passwordEncoder.encode(password))
                .activated(Objects.equals(active, "Active"))
                .roles(roles)
                .build();
        userService.saveOrUpdate(user);

        //send
        sendMail(user);

        return "redirect:/user/list";
    }

    @GetMapping(value="/confirm-account")
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token == null) {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("/users/error-verified");
        } else {
            Instant now = Instant.now();
            Instant tokenExpiration = token.getCreatedDate().toInstant().plusSeconds(60); // Add 60 seconds (1 minute)

            if (now.isAfter(tokenExpiration)) {
                modelAndView.addObject("message", "The link has expired!");
                confirmationTokenRepository.delete(token);
                modelAndView.setViewName("/users/error-verified");
            } else {
                User user = userRepository.findByEmailIgnoreCase(token.getUserEntity().getEmail());
                user.setActivated(true);
                userRepository.save(user);
                modelAndView.setViewName("/authentication/auth-sign-in");
            }
        }
        return modelAndView;
    }

    @PostMapping(value="/confirm-account")
    public String confirmUserAccount(@RequestParam("password") String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username).orElseThrow();

        user.setPassword(passwordEncoder.encode(password));
        user.setFirstLogin(false);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping(value = "/resend/{id}")
    public String resend(@PathVariable("id") Long id){
        User user = userRepository.findUserById(id);
        if (user != null) {
            ConfirmationToken existingToken = confirmationTokenRepository.findConfirmationTokenByUserEntity(user);
            if (existingToken != null) {
                confirmationTokenRepository.delete(existingToken);
            }
            sendMail(user);
        }
        return "redirect:/user/list";
    }

    public void sendMail(User user){
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                +"http://localhost:8080/user/confirm-account?token=" + confirmationToken.getConfirmationToken());
        emailServiceImpl.sendEmail(mailMessage);
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model){
        User user = userRepository.findUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("role_user", ERole.ROLE_USER);
        model.addAttribute("role_admin", ERole.ROLE_ADMIN);
        return "/users/page-view-users";
    }

    @GetMapping("/profile")
    public String profileUser( Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username).orElseThrow();

        model.addAttribute("user", user);
        model.addAttribute("role_user", ERole.ROLE_USER);
        model.addAttribute("role_admin", ERole.ROLE_ADMIN);
        return "/users/page-edit-users";
    }

    @PostMapping("/update-avatar")
    public String updateAvatar(@RequestParam(name = "avatar") MultipartFile avatar,
                               @RequestParam(name = "username") String username) throws IOException {
        User user = userRepository.getUserByUsername(username).orElseThrow();
        System.out.println(user.getUsername());
//        String fileName;
//        if (avatar != null && !avatar.isEmpty()) {
//            fileName = (user.getFullName().equals("null")?username:user.getFullName()).trim()+".jpg";
//            FileUploadUtil.saveFile(fileName, avatar);
//        } else {
//            String defaultImageFilePath = "static/assets/images/user/01.jpg";
//            ClassPathResource resource = new ClassPathResource(defaultImageFilePath);
//            byte[] defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//
//            MultipartFile defaultImageFile = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//            fileName = (user.getFullName().equals("null")?username:user.getFullName()).trim()+".jpg";
//
//            FileUploadUtil.saveFile(fileName, defaultImageFile);
//        }

        user.setAvatar(ImageUtil.convertToBase64(avatar));
        userService.saveOrUpdate(user);
        return "redirect:/user/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(name = "password") String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveOrUpdate(user);
        return "redirect:/user/profile/" + user.getUsername();
    }

    @PostMapping("/changelock/{username}")
    public String changLock(@PathVariable(name = "username") String username){
        User user = userRepository.getUserByUsername(username).orElseThrow();
        user.setUnlocked(!user.isUnlocked());
        userService.saveOrUpdate(user);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/user/list";
    }
}