package com.nttdata.report.domain;

import com.nttdata.report.domain.bean.Credit;
import com.nttdata.report.domain.bean.Customer;
import lombok.Data;
import reactor.core.publisher.Flux;

/**
 * CREDITMOVEMENTREPORT.
 * La clase contendrá  el reporte de los movimientos de un producto.
 * (PERSONAL, EMPRESARIAL y TARJETAS DE CREDITO)
 */
@Data
public class CreditMovementReport {
    /**
     * Datos del cliente
     */
    private Customer customer;
    /**
     * Datos del credito.
     */
    private Credit credit;
    /**
     * Listado de movimientos
     */
    private Flux<CreditMovementData> movementList;
}
