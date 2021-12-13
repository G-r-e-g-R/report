package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.Customer;
import lombok.Data;
import reactor.core.publisher.Flux;

/**
 * BALANCEREPORT.
 * La clase contendrá  el balance de los productos por cliente
 * (AHORRO, CUENTA CORRIENTE y PLAZO FIJO)
 * (PERSONAL, EMPRESARIAL y TARJETAS DE CREDITO)
 */
@Data
public class BalanceReport {
    /**
     * Datos del cliente
     */
    private Customer customer;
    /**
     * Datos de los saldos
     */
    private Flux<AccountBalanceData> balanceAccounts;
    /**
     * Datos de los saldos
     */
    private Flux<CreditBalanceData> balanceCredits;
}
