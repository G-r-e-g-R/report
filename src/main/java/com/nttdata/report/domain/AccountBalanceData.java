package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.AccountType;
import lombok.Data;

/**
 * ACCOUNTBALANCEDATA.
 * La clase contendrá  información de los saldos disponibles
 * (AHORRO, CUENTA CORRIENTE y PLAZO FIJO)
 */
@Data
public class AccountBalanceData {
    /**
     * Tipo de Cuenta: Ahorro, cuenta corriente, Plazo Fijo.
     */
    private AccountType accountType;
    /**
     * Saldo disponible.
     */
    private Double balance;
}
