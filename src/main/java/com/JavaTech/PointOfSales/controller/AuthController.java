package com.JavaTech.PointOfSales.controller;
import com.JavaTech.PointOfSales.repository.RoleRepository;
import com.JavaTech.PointOfSales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/authentication/auth-sign-in";
        }

        return "redirect:/";
    }

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                        loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
//                .body(new UserInfoResponse(userDetails.getId(),
//                        userDetails.getUsername(),
//                        userDetails.getEmail(),
//                        roles));
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                    signUpRequest.getEmail(),
//                    encoder.encode(signUpRequest.getPassword()));
//
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                if (role.equals("ADMIN")) {
//                    Role adminRole = roleRepository.findByName(ERole.ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(adminRole);
//                } else {
//                    Role userRole = roleRepository.findByName(ERole.USER)
//                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                    roles.add(userRole);
//                }
//            });
//        }
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser() {
//        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .body(new MessageResponse("You've been signed out!"));
//    }
}

