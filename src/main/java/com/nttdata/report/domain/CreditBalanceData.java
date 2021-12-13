package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.CreditType;
import lombok.Data;

/**
 * CREDITBALANCEDATA.
 * La clase contendrá  información de los saldos
 * (PERSONAL, EMPRESARIAL y TARJETAS DE CREDITO)
 */
@Data
public class CreditBalanceData {
    /**
     * Tipo de Credito: Personal, Empresarial, etc.
     */
    private CreditType creditType;
    /**
     * Saldo disponible.
     */
    private Double balance;
}
