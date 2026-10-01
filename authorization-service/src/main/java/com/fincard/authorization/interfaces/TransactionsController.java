package com.fincard.authorization.interfaces;

import com.fincard.authorization.application.TransactionsService;
import com.fincard.authorization.interfaces.dto.TransactionRequest;
import com.fincard.authorization.interfaces.dto.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Authorization")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/authorize")
    @Operation(summary = "Authenticate user and issue JWT")
    public TransactionResponse transaction(
            @RequestHeader("transaction-Id") String transactionId,
            @Valid @RequestBody TransactionRequest request
    ) {
        return transactionsService.transaction(request, transactionId);
    }

}
