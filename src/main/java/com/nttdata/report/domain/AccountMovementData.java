package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.AccountMovementType;
import lombok.Data;

import java.util.Date;
/**
 * ACCOUNTMOVEMENTDATA.
 * La clase contendrá  información de los movimientos.
 * (AHORRO, CUENTA CORRIENTE y PLAZO FIJO)
 */
@Data
public class AccountMovementData {
    /**
     * Monto de la transacción.
     */
    private Double amount;
    /**
     * Tipo de movimiento - transacción.
     */
    private AccountMovementType movementType;
    /**
     * Fecha del movimiento - transacción.
     */
    private Date movementDate;
}
