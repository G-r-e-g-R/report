package com.nttdata.report.infraestructure.repository;

import com.nttdata.report.application.AccountMovementReportRepository;
import com.nttdata.report.domain.AccountBalanceData;
import com.nttdata.report.domain.AccountMovementReport;
import com.nttdata.report.domain.bean.Account;
import com.nttdata.report.domain.bean.AccountAffiliation;
import com.nttdata.report.domain.bean.AccountMovement;
import com.nttdata.report.domain.bean.Customer;
import com.nttdata.report.infraestructure.client.UriService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTREPORTREPOSITORY.
 * Implementa las consultas (Reporte) de cuentas bancarias.
 */
@Component
@Slf4j
public class AccountMovementReportCrudRepository
        implements AccountMovementReportRepository {

    /**
     * Servicio web cliente.
     */
    private final WebClient webClient;
    /**
     * Circuit Breaker.
     */
    private final ReactiveCircuitBreaker reactiveCircuitBreaker;

    /**
     * Constructor.
     * @param circuitBreakerFactory corto circuito.
     */
    public AccountMovementReportCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("accountReport");
    }

    /**
     * Obtiene los saldos del producto
     * @param accountAffiliation
     * @return
     */
    private AccountBalanceData setBalance(AccountAffiliation accountAffiliation){
        AccountBalanceData accountBalanceData = new AccountBalanceData();
        accountBalanceData.setBalance(accountAffiliation.getBalance());
        Mono<Account> account = getProductAccountById(accountAffiliation.getIdAccount());
        account.map(a -> {
            accountBalanceData.setAccountType(a.getAccountType());
            return a;
        });
        return accountBalanceData;
    }
    @Override
    public Flux<AccountMovementReport> getAccountMovementReport(String idCustomer, String idAccount) {
        return null;
    }

    /**
     * Obtiene las afiliaciones de cuenta bancaria de un cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<AccountAffiliation>
     */
    @Override
    public
    Flux<AccountAffiliation>
    getAccountAffiliationByIdCustomerByIdAccount(String idCustomer, String idAccount) {
        log.info("[getAccountAffiliationByIdCustomer] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.AFFILIATION_ACCOUNT_GET_BY_IDCUSTOMER_BY_IDACCOUNT,
                                        idCustomer,
                                        idAccount
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(AccountAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getAccountAffiliationByIdCustomer] Error en la llamada:"
                                    + UriService.AFFILIATION_ACCOUNT_GET_BY_IDCUSTOMER_BY_IDACCOUNT
                                    + idCustomer + "-" +idAccount);
                            return Flux.just(new AccountAffiliation());
                        });
    }

    @Override
    public
    Flux<AccountMovement>
    getAccountMovementByIdAffiliation(String idAffiliation) {
        log.info("[getAccountMovementByIdAffiliation] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.TRANSACTION_ACCOUNT_GET_BY_IDAFFILIATION,
                                        idAffiliation
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(AccountMovement.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getAccountMovementByIdAffiliation] Error en la llamada:"
                                    + UriService.TRANSACTION_ACCOUNT_GET_BY_IDAFFILIATION
                                    + idAffiliation );
                            return Flux.just(new AccountMovement());
                        });
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return
     */
    @Override
    public Mono<Customer> getCustomerById(String idCustomer) {
        log.info("[getCustomerById] Inicio:"+idCustomer);
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.CUSTOMER_GET_BY_ID,
                                        idCustomer
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(Customer.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCustomerById] Error en la llamada:"
                                    + UriService.CUSTOMER_GET_BY_ID
                                    + idCustomer);
                            return Mono.just(new Customer());
                        });
    }


    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Mono<Account>
     */
    @Override
    public
    Mono<Account>
    getProductAccountById(String idAccount) {
        log.info("[getProductAccountById] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.PRODUCT_ACCOUNT_GET_BY_ID,
                                        idAccount
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToMono(Account.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getProductAccountById] Error en la llamada:"
                                    + UriService.PRODUCT_ACCOUNT_GET_BY_ID
                                    + idAccount);
                            return Mono.just(new Account());
                        });
    }

}
