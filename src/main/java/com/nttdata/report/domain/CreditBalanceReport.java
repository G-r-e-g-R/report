package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.Customer;
import lombok.Data;

import java.util.ArrayList;

/**
 * CREDITBALANCEREPORT.
 * La clase contendrá  el reporte de los saldos
 * (PERSONAL, EMPRESARIAL y TARJETAS DE CREDITO)
 */
@Data
public class CreditBalanceReport {
    /**
     * Datos del cliente
     */
    private Customer customer;
    /**
     * Datos de los saldos
     */
    private ArrayList<CreditBalanceData> balanceList;
}
