package com.nttdata.report.infraestructure.rest;

import com.nttdata.report.application.BalanceReportOperations;
import com.nttdata.report.domain.BalanceReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * CREDITREPORTCONTROLLER.
 * Realiza las consultas (reporte) de los productos de los clientes.
 */
@Slf4j
@RestController
@RequestMapping("/reports/balances")
@RequiredArgsConstructor
public class BalanceReportController {
    /**
     * Consulta de las operaciones de balance.
     */
    private final BalanceReportOperations reportOperations;

    /**
     * Busca los saldos de todos los productos.
     * @return Flux<ResponseEntity<AccountBalanceReport>>
     */
    @GetMapping
    public Flux<ResponseEntity<BalanceReport>>
    getAll() {
        return reportOperations
                .getBalanceReport()
                .map(a -> ResponseEntity
                        .ok()
                        .body(a))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
