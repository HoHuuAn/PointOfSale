package com.JavaTech.PointOfSales.repository;

import com.JavaTech.PointOfSales.model.ConfirmationToken;
import com.JavaTech.PointOfSales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {

    ConfirmationToken findConfirmationTokenByUserEntity(User user);
    ConfirmationToken findByConfirmationToken(String confirmationToken);


//    ConfirmationToken findConfirmationTokenByUserEntity(User user);
}
