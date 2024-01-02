package com.JavaTech.PointOfSales.component;

import com.JavaTech.PointOfSales.model.*;
import com.JavaTech.PointOfSales.repository.BranchRepository;
import com.JavaTech.PointOfSales.repository.BrandRepository;
import com.JavaTech.PointOfSales.repository.RoleRepository;
import com.JavaTech.PointOfSales.repository.UserRepository;
import com.JavaTech.PointOfSales.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

//@Service
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private BranchRepository branchRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BrandRepository brandRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty() ) {
//            roleRepository.save(new Role(ERole.ROLE_ADMIN));
//        }
//        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty() ) {
//            roleRepository.save(new Role(ERole.ROLE_USER));
//        }
//
//        if (branchRepository.findBranchByName("Branch 1").isEmpty()) {
//            branchRepository.save(Branch.builder().name("Branch 1").address("District 1").createdAt(new Date()).build());
//        }
//
//        if (branchRepository.findBranchByName("Branch 2").isEmpty()) {
//            branchRepository.save(Branch.builder().name("Branch 2").address("District 2").createdAt(new Date()).build());
//        }
//
//        String defaultImageFilePath = "static/user-photos/admin/admin.jpg";
//        ClassPathResource resource = new ClassPathResource(defaultImageFilePath);
//        byte[] defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//        MultipartFile avatar = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//
//        if(userRepository.getUserByUsername("admin1").isEmpty()){
//            User admin1 = User.builder()
//                    .username("admin1")
//                    .password(passwordEncoder.encode("admin1"))
//                    .activated(true)
//                    .unlocked(true)
//                    .avatar(ImageUtil.convertToBase64(avatar))
//                    .firstLogin(false)
//                    .branch(branchRepository.findBranchByName("Branch 1").orElseThrow())
//                    .build();
//            userRepository.save(admin1);
//            userRepository.addUserRole(admin1.getId(), 1L);
//        }
//
//        if(userRepository.getUserByUsername("admin2").isEmpty()){
//            User admin2 = User.builder()
//                    .username("admin2")
//                    .password(passwordEncoder.encode("admin2"))
//                    .activated(true)
//                    .unlocked(true)
//                    .avatar(ImageUtil.convertToBase64(avatar))
//                    .firstLogin(false)
//                    .branch(branchRepository.findBranchByName("Branch 2").orElseThrow())
//                    .build();
//            userRepository.save(admin2);
//            userRepository.addUserRole(admin2.getId(), 1L);
//        }
//
//        defaultImageFilePath = "static/assets/images/user/01.jpg";
//        resource = new ClassPathResource(defaultImageFilePath);
//        defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//        avatar = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//
//        if(userRepository.getUserByUsername("user1").isEmpty()){
//            User user1 = User.builder()
//                    .username("user1")
//                    .password(passwordEncoder.encode("user1"))
//                    .activated(true)
//                    .unlocked(true)
//                    .avatar(ImageUtil.convertToBase64(avatar))
//                    .firstLogin(false)
//                    .branch(branchRepository.findBranchByName("Branch 1").orElseThrow())
//                    .build();
//            userRepository.save(user1);
//            userRepository.addUserRole(user1.getId(), 2L);
//        }
//
//        if(userRepository.getUserByUsername("user2").isEmpty()){
//            User user2 = User.builder()
//                    .username("user2")
//                    .password(passwordEncoder.encode("user2"))
//                    .activated(true)
//                    .unlocked(true)
//                    .avatar(ImageUtil.convertToBase64(avatar))
//                    .firstLogin(false)
//                    .branch(branchRepository.findBranchByName("Branch 2").orElseThrow())
//                    .build();
//            userRepository.save(user2);
//            userRepository.addUserRole(user2.getId(), 2L);
//        }
//
//        defaultImageFilePath = "static/brands/Apple/Apple.jpg";
//        resource = new ClassPathResource(defaultImageFilePath);
//        defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//        avatar = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//
//        if(brandRepository.findBrandByName("Apple").isEmpty()){
//            Brand apple = Brand.builder().name("Apple")
//                    .description("Apple Inc. is a globally renowned technology company known for its innovative hardware, software, and services. Founded in 1976 by Steve Jobs, Steve Wozniak, and Ronald Wayne, Apple has been a trailblazer in the consumer electronics industry. The company's iconic products include the iPhone, iPad, Mac computers, Apple Watch, and Apple TV.")
//                    .website("apple.com")
//                    .image(ImageUtil.convertToBase64(avatar))
//                    .build();
//            brandRepository.save(apple);
//        }
//
//        defaultImageFilePath = "static/brands/Samsung/Samsung.jpg";
//        resource = new ClassPathResource(defaultImageFilePath);
//        defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//        avatar = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//
//        if(brandRepository.findBrandByName("Samsung").isEmpty()){
//            Brand samsung = Brand.builder().name("Samsung")
//                    .description("Samsung is a prominent multinational conglomerate based in South Korea, known for its diverse range of products and services across various industries. Established in 1938 as a trading company, Samsung has since expanded into electronics, mobile devices, semiconductors, home appliances.")
//                    .website("samsung.com")
//                    .image(ImageUtil.convertToBase64(avatar))
//                    .build();
//            brandRepository.save(samsung);
//        }
//
//        defaultImageFilePath = "static/brands/Vivo/Vivo.jpg";
//        resource = new ClassPathResource(defaultImageFilePath);
//        defaultImageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//        avatar = new MockMultipartFile("default-avatar.png", defaultImageBytes);
//
//        if(brandRepository.findBrandByName("Vivo").isEmpty()){
//            Brand samsung = Brand.builder().name("Vivo")
//                    .description("Vivo is a global technology company specializing in the design, development, and manufacture of smartphones, accessories, software, and online services. Established in 2009, Vivo has rapidly expanded its presence in the mobile industry and has become one of the largest smartphone brands in the world.")
//                    .website("vivo.com")
//                    .image(ImageUtil.convertToBase64(avatar))
//                    .build();
//            brandRepository.save(samsung);
//        }
//    }
//}
