package org.web.vikings_shop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.vikings_shop.entities.Cart;
import org.web.vikings_shop.entities.User;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser(User user);

    Cart  findByUserId(String id);


}
