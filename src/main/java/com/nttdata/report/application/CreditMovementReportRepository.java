package com.nttdata.report.application;

import com.nttdata.report.domain.CreditMovementReport;
import com.nttdata.report.domain.bean.Credit;
import com.nttdata.report.domain.bean.CreditAffiliation;
import com.nttdata.report.domain.bean.CreditMovement;
import com.nttdata.report.domain.bean.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITREPORTREPOSITORY.
 * Define las operaciones en la BD para
 * las consultas (reportes) de un credito y un cliente
 */
public interface CreditMovementReportRepository {
    /**
     * Lista los movimientos de un producto de un cliente.
     * @param idCustomer codigo del cliente.
     * @param idCredit codigo del producto.
     * @return Flux<CreditMovementReport>
     */
    Flux<CreditMovementReport>
    getCreditMovementReport(String idCustomer, String idCredit);

    /**
     * Listado de Afiliaciones por Credito y por Cliente.
     * @param idCustomer Codigo del cliente.
     * @param idCredit Codigo del credito.
     * @return  Flux<CreditAffiliation>
     */
    Flux<CreditAffiliation>
    getCreditAffiliationByIdCustomerByIdCredit(String idCustomer, String idCredit);

    /**
     * Listado de movimientos de una afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @return Flux<CreditMovement>
     */
    Flux<CreditMovement>
    getCreditMovementByIdAffiliation(String idAffiliation);

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    Mono<Customer>
    getCustomerById(final String idCustomer);

    /**
     * Obtenemos los datos del producto: Credito.
     * @param idCredit codigo del credito.
     * @return Flux<Credit>
     */
    Mono<Credit>
    getProductCreditById(final String idCredit);
}
