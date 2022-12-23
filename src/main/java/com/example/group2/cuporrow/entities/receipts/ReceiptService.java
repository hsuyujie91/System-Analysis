package com.example.group2.cuporrow.entities.receipts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.group2.cuporrow.entities.user.User;

@Service
public class ReceiptService {
   
    @Autowired
    private ReceiptRepo receiptRepo;

    public void saveReceipt(Receipt receipt) {
        receiptRepo.save(receipt);
    }

    public void deleteReceipt(Receipt receipt) {
        receiptRepo.delete(receipt);
    }

    public List<Receipt> getAllReceipts(User user) {
        return receiptRepo.findAllByOwner(user.getId());
    }

}
