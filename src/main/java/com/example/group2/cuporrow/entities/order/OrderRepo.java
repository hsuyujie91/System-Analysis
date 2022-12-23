package com.example.group2.cuporrow.entities.order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT o FROM orders o WHERE o.orderUser.id=user_id and o.bottleId=bottleId")
    Optional<Order> findOrderByUserAndBottleId(@Param("user_id") int user_id, @Param("bottleId") int bottleId);
}
