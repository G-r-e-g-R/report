package com.nttdata.report.application;

import com.nttdata.report.domain.AccountMovementReport;
import com.nttdata.report.domain.bean.Account;
import com.nttdata.report.domain.bean.AccountAffiliation;
import com.nttdata.report.domain.bean.AccountMovement;
import com.nttdata.report.domain.bean.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTREPORTREPOSITORY.
 * Define las operaciones en la BD para
 * las consultas (Reportes) de una cuenta bancaria y un cliente
 */
public interface AccountMovementReportRepository {
    /**
     * Lista los movimientos de un producto de un cliente.
     * @param idCustomer codigo del cliente.
     * @param idAccount codigo del producto.
     * @return Flux<AccountMovementReport>
     */
    Flux<AccountMovementReport>
    getAccountMovementReport(String idCustomer, String idAccount);


    /**
     * Listado de Afiliaciones por cuentas y por Cliente.
     * @param idCustomer Codigo del cliente.
     * @param idAccount Codigo de la cuenta.
     * @return  Flux<AccountAffiliation>
     */
    Flux<AccountAffiliation>
    getAccountAffiliationByIdCustomerByIdAccount(String idCustomer, String idAccount);

    /**
     * Listado de movimientos de una afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @return Flux<AccountMovement>
     */
    Flux<AccountMovement>
    getAccountMovementByIdAffiliation(String idAffiliation);

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    Mono<Customer>
    getCustomerById(final String idCustomer);

    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Flux<Account>
     */
    Mono<Account>
    getProductAccountById(final String idAccount);
}
