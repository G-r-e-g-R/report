package com.nttdata.report.application;

import com.nttdata.report.domain.*;
import com.nttdata.report.domain.bean.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BalanceReportRepository {

    /**
     * Listado de Afiliaciones de cuentas por Cliente.
     * @param idCustomer Codigo del cliente.
     * @return Mono<AccountAffiliation>
     */
    Flux<AccountAffiliation>
    getAccountAffiliationByIdCustomer(String idCustomer);

    /**
     * Obtenemos todos los cliente.
     * @return Flux<Customer>
     */
    Flux<Customer>
    getAllCustomer();

    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria.
     * @return Mono<Account>
     */
    Mono<Account>
    getProductAccountById(final String idAccount);
    /**
     * Obtenemos los datos del producto: Credito.
     * @param idCredit codigo del credito.
     * @return Mono<Credit>
     */
    Mono<Credit>
    getProductCreditById(final String idCredit);

    /**
     * Listado de Afiliaciones de credito por Cliente.
     * @param idCustomer Codigo del cliente.
     * @return Mono<CreditAffiliation>
     */
    Flux<CreditAffiliation>
    getCreditAffiliationByIdCustomer(String idCustomer);

    /**
     * Lista todos los balances de los productos de todos los clientes.
     * @return Flux<BalanceReport>
     */
    Flux<BalanceReport>
    getBalanceReport();
}
