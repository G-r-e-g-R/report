package com.nttdata.report.infraestructure.rest;

import com.nttdata.report.application.CreditMovementReportOperations;
import com.nttdata.report.domain.CreditMovementReport;
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
 * CREDITREPORTCONTROLLER.
 * Realiza las consultas (reporte) de credito de los clientes.
 */
@Slf4j
@RestController
@RequestMapping("/reports/credits")
@RequiredArgsConstructor
public class CreditMovementReportController {
    /**
     * Consulta de las operaciones de credito.
     */
    private final CreditMovementReportOperations creditReportOperations;

    /**
     * Busca por el Id del cliente y producto los saldos de credito.
     * @param idCustomer codigo cliente.
     * @param idCredit codigo de credito.
     * @return Flux<ResponseEntity<AccountBalanceReport>>
     */
    @GetMapping("/movements/{idCustomer}/{idCredit}")
    public Flux<ResponseEntity<CreditMovementReport>>
    getById(@PathVariable final String idCustomer,
            @PathVariable final String idCredit) {
        return creditReportOperations
                .getCreditMovementReport(idCustomer, idCredit)
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
