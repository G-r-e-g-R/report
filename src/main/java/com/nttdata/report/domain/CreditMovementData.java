package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.CreditMovementType;
import lombok.Data;

import java.util.Date;
/**
 * CREDITMOVEMENTDATA.
 * La clase contendrá  los datos de los movimientos de un producto.
 * (PERSONAL, EMPRESARIAL y TARJETAS DE CREDITO)
 */
@Data
public class CreditMovementData {
    /**
     * Tipo de movimiento - transacción.
     */
    private CreditMovementType movementType;
    /**
     * Monto de la transacción.
     */
    private Double amount;
    /**
     * Fecha del movimiento - transacción.
     */
    private Date movementDate;
}
