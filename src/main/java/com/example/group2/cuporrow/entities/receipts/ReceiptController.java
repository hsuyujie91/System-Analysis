package com.example.group2.cuporrow.entities.receipts;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.group2.cuporrow.entities.user.User;
import com.example.group2.cuporrow.entities.user.UserService;
import com.example.group2.cuporrow.utils.ErrorResponse;

@RestController
@RequestMapping(value = "receipt")
public class ReceiptController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReceiptService receiptService;

    @PostMapping(value = "/get-all", produces = "application/json")
    public ResponseEntity<?> getAllReceipts(@Valid @RequestBody User body) {
        User user = userService.validByTokenAndGetUser(body.getAccount(), body.getToken());
        if (user == null)
            return ErrorResponse.notAuthorized();
        List<Receipt> receipts = receiptService.getAllReceipts(user);
        if (!receipts.isEmpty()) {
            return ResponseEntity.ok(receipts);
        }
        return new ResponseEntity<String>("Unable to retrieve receipts!", HttpStatus.NOT_FOUND);
    }

}
