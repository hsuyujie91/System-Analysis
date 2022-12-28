package com.example.group2.cuporrow.entities.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.group2.cuporrow.entities.user.User;
import com.example.group2.cuporrow.entities.user.UserService;
import com.example.group2.cuporrow.utils.ErrorResponse;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private UserService userService;

    private static class DeleteOrderRequest {
        String account;
        String token;
        int orderId;

        public void setAccount(String account) {
            this.account = account;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getAccount() {
            return account;
        }

        public String getToken() {
            return token;
        }

        public int getOrderId() {
            return orderId;
        }

    }

    private static class PlaceOrderRequest {
        String account;
        String token;
        int bottleId;

        public void setAccount(String account) {
            this.account = account;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setBottleId(int bottleId) {
            this.bottleId = bottleId;
        }

        public String getAccount() {
            return account;
        }

        public String getToken() {
            return token;
        }

        public int getBottleId() {
            return bottleId;
        }
    }

    @PostMapping(value = "/rent", produces = "application/json")
    public ResponseEntity<String> placeOrder(PlaceOrderRequest body) {
        User user = userService.validByTokenAndGetUser(body.getAccount(), body.getToken());
        if (user == null)
            return ErrorResponse.notAuthorized();
        if (!service.isReturn(user, body.getBottleId())) {
            Order order = service.makeOrder(user, body.getBottleId());
            if (order != null) {
                return new ResponseEntity<String>(String.format("Borrow time: %s", order.getBorrowTime()),
                        HttpStatus.OK);
            }
        } else {
            Order order = service.returnBottle(user, body.getBottleId());
            if (order != null) {
                return new ResponseEntity<String>(
                        String.format("{\"start\": \"%s\", \"end\": \"%s\", \"bill\": \"%d\"}",
                                order.getBorrowTime(), order.getReturnTime(), order.getBill()),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("Unable to place order.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<String> deleteOrder(@RequestBody DeleteOrderRequest request) {
        User user = userService.validByTokenAndGetUser(request.getAccount(), request.getToken());
        if (user == null)
            return ErrorResponse.notAuthorized();
        if (service.cancelOrder(user, request.getOrderId()) != null) {
        }
        return new ResponseEntity<String>("Unable to cancel order!", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/return", produces = "application/json")
    public ResponseEntity<?> returnBottle(PlaceOrderRequest body) {
        User user = userService.validByTokenAndGetUser(body.getAccount(), body.getToken());
        if (user == null)
            return ErrorResponse.notAuthorized();
        Order order = service.returnBottle(user, body.bottleId);
        if (order != null) {
            return new ResponseEntity<String>(String.format("{\"start\": \"%s\", \"end\": \"%s\", \"bill\": \"%d\"}",
                    order.getBorrowTime(), order.getReturnTime(), order.getBill()), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Unable to return the bottle!", HttpStatus.NOT_FOUND);
    }

}
