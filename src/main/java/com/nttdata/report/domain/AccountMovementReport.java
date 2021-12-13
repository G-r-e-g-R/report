package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.Account;
import com.nttdata.report.domain.bean.Customer;
import lombok.Data;
import reactor.core.publisher.Flux;

/**
 * ACCOUNTMOVEMENTREPORT.
 * La clase contendrá  el reporte de los movimientos de un producto
 * (AHORRO, CUENTA CORRIENTE y PLAZO FIJO)
 */
@Data
public class AccountMovementReport {
    /**
     * Datos del cliente
     */
    private Customer customer;
    /**
     * Datos de la cuenta bancaria.
     */
    private Account account;
    /**
     * Listado de los movimientos
     */
    private Flux<AccountMovementData> movementList;
}
