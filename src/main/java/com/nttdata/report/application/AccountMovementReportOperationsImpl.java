package com.nttdata.report.application;

import com.nttdata.report.domain.AccountMovementData;
import com.nttdata.report.domain.AccountMovementReport;
import com.nttdata.report.domain.bean.Account;
import com.nttdata.report.domain.bean.AccountAffiliation;
import com.nttdata.report.domain.bean.AccountMovement;
import com.nttdata.report.domain.bean.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTREPORTOPERATIONSIMPL.
 * Implementa las consultas (Reportes) de los movimientos
 *                         de Cuentas Bancarias de un cliente
 */
@Service
@Slf4j
public class AccountMovementReportOperationsImpl
        implements AccountMovementReportOperations {
    /**
     * Repositorio de las consultas de las cuentas bancarias.
     */
    private final AccountMovementReportRepository repository;

    /**
     * Constructor.
     * @param accountReportRepository repositorio.
     */
    public AccountMovementReportOperationsImpl(AccountMovementReportRepository accountReportRepository){
        this.repository = accountReportRepository;
    }

    @Override
    public
    Flux<AccountMovementReport>
    getAccountMovementReport(String idCustomer, String idAccount) {
        //Obtenemos las afiliaciones
        Flux<AccountAffiliation>  affiliations = getAccountAffiliationByIdCustomerByIdAccount(idCustomer, idAccount);
        //Recorremos todas las afiliaciones
        Flux<AccountMovementData> accountMovementData =
        affiliations.flatMap(affiliation -> {
            //Obtenemos los movimientos
            Flux<AccountMovement> movements = getAccountMovementByIdAffiliation(affiliation.getId());
            return
                movements.flatMap(movement -> {
                    AccountMovementData data = new AccountMovementData();
                    data.setMovementDate(movement.getMovementDate());
                    data.setAmount(movement.getAmount());
                    data.setMovementType(movement.getMovementType());
                    return Flux.just(data);
                }).switchIfEmpty(Flux.just(new AccountMovementData()));
        }).switchIfEmpty(Flux.just(new AccountMovementData()));
        //Preparamos el reporte con los datos de los movimientos
        AccountMovementReport report = new AccountMovementReport();
        //Asignamos los datos de la cuenta bancaria.
        getProductAccountById(idAccount).map(account -> {
            report.setAccount(account);
            return account;
        });
        //Asignamos los datos del cliente
        getCustomerById(idCustomer).map(customer ->{
            report.setCustomer(customer);
            return customer;
        });
        return Flux.just(report);
    }

    /**
     * Obtenemos los datos de la afiliación por cuenta y por cliente.
     * @param idCustomer Codigo del cliente.
     * @param idAccount Codigo de la cuenta.
     * @return Flux<AccountAffiliation>
     */
    @Override
    public
    Flux<AccountAffiliation>
    getAccountAffiliationByIdCustomerByIdAccount(String idCustomer, String idAccount){
        return repository.getAccountAffiliationByIdCustomerByIdAccount(idCustomer, idAccount);
    }

    /**
     * Obtenemos los datos de los movimientos de una afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @return Flux<AccountMovement>
     */
    @Override
    public
    Flux<AccountMovement>
    getAccountMovementByIdAffiliation(String idAffiliation) {
        return repository.getAccountMovementByIdAffiliation(idAffiliation);
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    @Override
    public
    Mono<Customer>
    getCustomerById(String idCustomer) {
        log.info("[getCustomerById] idAccount:"+idCustomer);
        return repository.getCustomerById(idCustomer);
    }

    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Flux<Account>
     */
    @Override
    public
    Mono<Account>
    getProductAccountById(String idAccount) {
        log.info("[getProductAccountById] idAccount:"+idAccount);
        return repository.getProductAccountById(idAccount);
    }
}
