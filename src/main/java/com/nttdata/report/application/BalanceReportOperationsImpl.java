package com.nttdata.report.application;

import com.nttdata.report.domain.*;
import com.nttdata.report.domain.bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * REPORTOPERATIONSIMPL.
 * Implementa las consultas (Reportes) de los productos y clientes.
 */
@Service
@Slf4j
public class BalanceReportOperationsImpl
        implements BalanceReportOperations {
    /**
     * Repositorio de las consultas de las creditos.
     */
    private final BalanceReportRepository repository;

    /**
     * Constructor.
     * @param reportRepository
     */
    public BalanceReportOperationsImpl(BalanceReportRepository reportRepository){
        this.repository = reportRepository;
    }

    /**
     * Obtenemos todos las afiliaciones de cuenta bancaria por cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<AccountAffiliation>
     */
    @Override
    public
    Flux<AccountAffiliation>
    getAccountAffiliationByIdCustomer(String idCustomer) {
        return repository.getAccountAffiliationByIdCustomer(idCustomer);
    }

    /**
     * Obtenemos todos los clientes.
     * @return Flux<Customer>
     */
    @Override
    public
    Flux<Customer>
    getAllCustomer() {
        return repository.getAllCustomer();
    }

    /**
     * Obtenemos los datos de un producto de cuenta bancaria.
     * @param idAccount codigo de la cuenta bancaria.
     * @return Mono<Account>
     */
    @Override
    public
    Mono<Account>
    getProductAccountById(String idAccount) {
        return repository.getProductAccountById(idAccount);
    }

    /**
     * Obtenemos los datos de un producto de credito.
     * @param idCredit codigo del credito.
     * @return Mono<Credit>
     */
    @Override
    public
    Mono<Credit>
    getProductCreditById(String idCredit) {
        return repository.getProductCreditById(idCredit);
    }

    /**
     * Obtnemos las afiliaciones a credito de un cliente.
     * @param idCustomer Codigo del cliente.
     * @return  Flux<CreditAffiliation>
     */
    @Override
    public
    Flux<CreditAffiliation>
    getCreditAffiliationByIdCustomer(String idCustomer) {
        return repository.getCreditAffiliationByIdCustomer(idCustomer);
    }

    /**
     * Obtenemos todos los balances de todos los productos de los clientes.
     * @return  Flux<BalanceReport>
     */
    @Override
    public
    Flux<BalanceReport>
    getBalanceReport() {
        //Obtenemos todos los clientes
        Flux<Customer> customers = getAllCustomer();

        return
         //Recorremos todos los clientes
        customers.flatMap(
                customer -> {
                    BalanceReport balanceReport = new BalanceReport();
                    //Asignamos los datos del cliente
                    balanceReport.setCustomer(customer);
                    //Obtenemos todas las afiliaciones de cuenta bancaria del cliente
                    Flux<AccountAffiliation> accountAffiliation = getAccountAffiliationByIdCustomer(customer.getCode());
                    //Recorremos todas las cuentas bancarias
                    Flux<AccountBalanceData> accountBalance = accountAffiliation.flatMap(accAffiliation -> {
                        AccountBalanceData data = new AccountBalanceData();
                        //Obtenemos los datos de la cuenta
                        Mono<Account> account = getProductAccountById(accAffiliation.getIdAccount());
                        account.map(acc -> {
                            //Asignamos los datos para la consulta - reporte de Saldos
                            data.setAccountType(acc.getAccountType());
                            data.setBalance(accAffiliation.getBalance());
                            //Retornamos los datos de la cuenta bancaria
                            return Flux.just(data);
                        });
                        //Retornamos los datos de la cuenta bancaria
                        return Flux.just(data);
                    });
                    //Asignamos los saldos de la cuenta bancaria a la consulta - reporte de saldos
                    balanceReport.setBalanceAccounts(accountBalance);
                    //Obtenemos todas las afiliaciones de credito del cliente
                    Flux<CreditAffiliation> creditAffiliation = getCreditAffiliationByIdCustomer(customer.getCode());
                    //Recorremos todas los creditos
                    Flux<CreditBalanceData> creditBalance =  creditAffiliation.flatMap(creAffiliation -> {
                        CreditBalanceData data = new CreditBalanceData();
                        //Obtenemos los datos del credito
                        Mono<Credit> credit = getProductCreditById(creAffiliation.getIdCredit());
                        credit.map(cre -> {
                            //Asignamos los datos para la consulta - reporte de Saldos
                            data.setCreditType(cre.getCreditType());
                            data.setBalance(creAffiliation.getBalance());
                            //Retornamos los datos del credito
                            return Flux.just(data);
                        });
                        //Retornamos los datos del credito
                        return Flux.just(data);
                    });
                    //Asignamos los saldos del credito a la consulta - reporte de saldos
                    balanceReport.setBalanceCredits(creditBalance);

                    return Flux.just(balanceReport);
                }
        );
    }


}
