package com.example.group2.cuporrow.entities.receipts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepo extends JpaRepository<Receipt, Integer> {
    @Query("SELECT new com.example.group2.cuporrow.entities.receipts.Receipt(r.startTime, r.endTime, r.bill) FROM receipt r WHERE r.owner.id=user_id")
    List<Receipt> findAllByOwner(@Param("user_id") int user_id);
}
