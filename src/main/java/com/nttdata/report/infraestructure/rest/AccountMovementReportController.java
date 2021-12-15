package com.nttdata.report.infraestructure.rest;

import com.nttdata.report.application.AccountMovementReportOperations;
import com.nttdata.report.domain.AccountMovementReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ACCOUNTREPORTCONTROLLER.
 * Realiza las consultas (reporte) de cuentas bancarias de los clientes.
 */
@Slf4j
@RestController
@RequestMapping("/reports/accounts")
@RequiredArgsConstructor
public class AccountMovementReportController {
    /**
     * Consulta de las operaciones de cuenta bancaria.
     */
    private final AccountMovementReportOperations accountReportOperations;

    /**
     * Busca por el Id del cliente y producto los saldos de la cuenta bancaria.
     * @param idCustomer codigo cliente.
     * @param idAccount codigo Cuenta bancaria.
     * @return Flux<ResponseEntity<AccountBalanceReport>>
     */
    @GetMapping("/movements/{idCustomer}/{idAccount}")
    public Flux<ResponseEntity<AccountMovementReport>>
    getById(@PathVariable final String idCustomer,
            @PathVariable final String idAccount) {
        return accountReportOperations
                .getAccountMovementReport(idCustomer, idAccount)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
