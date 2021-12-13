package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.Customer;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
/**
 * ACCOUNTBALANCEREPORT.
 * La clase contendrá  el reporte de los saldos disponibles
 * (AHORRO, CUENTA CORRIENTE y PLAZO FIJO)
 */
@Data
public class AccountBalanceReport {
    /**
     * Datos del cliente
     */
    private Customer customer;
    /**
     * Datos de los saldos
     */
    private Flux<AccountBalanceData> balanceList;

}
