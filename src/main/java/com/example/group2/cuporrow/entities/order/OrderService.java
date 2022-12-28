package com.example.group2.cuporrow.entities.order;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.group2.cuporrow.entities.receipts.Receipt;
import com.example.group2.cuporrow.entities.receipts.ReceiptService;
import com.example.group2.cuporrow.entities.user.User;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ReceiptService receiptService;

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    public void deleteOrder(Order order) {
        orderRepo.delete(order);
    }

    public Order cancelOrder(User user, int orderId) {
        Optional<Order> optOrderInDB = orderRepo.findById(orderId);
        if (optOrderInDB.isPresent()) {
            Order order = optOrderInDB.get();
            deleteOrder(order);
            return order;
        }
        return null;
    }

    public Order makeOrder(User user, int bottleId) {
        Order order = new Order(bottleId, user);
        orderRepo.save(order);
        return order;
    }

    public boolean isReturn(User user, int bottleId) {
        Optional<Order> optOrderInDB = orderRepo.findOrderByUserAndBottleId(user.getId(), bottleId);
        return optOrderInDB.isPresent();
    }

    public Order returnBottle(User user, int bottleId) {
        Optional<Order> optOrderInDB = orderRepo.findOrderByUserAndBottleId(user.getId(), bottleId);
        if (optOrderInDB.isPresent()) {
            Order order = optOrderInDB.get();
            order.setReturnTime(LocalDateTime.now());
            order.calculateBill();
            saveOrder(order);
            // remove the order and save the receipt
            Receipt receipt = new Receipt(order.getBorrowTime(), order.getReturnTime(), user, order.getBill());
            receiptService.saveReceipt(receipt);
            //
            deleteOrder(order);
            return order;
        }
        return null;
    }
}
