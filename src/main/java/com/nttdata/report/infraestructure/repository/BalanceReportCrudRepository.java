package com.nttdata.report.infraestructure.repository;

import com.nttdata.report.application.BalanceReportRepository;
import com.nttdata.report.domain.BalanceReport;
import com.nttdata.report.domain.bean.*;
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
 * BALANCEREPORTCRUDREPOSITORY.
 * Implementa las consultas (Reporte) de saldos.
 */
@Component
@Slf4j
public class BalanceReportCrudRepository
        implements BalanceReportRepository {
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
    public BalanceReportCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("accountReport");
    }

    /**
     * Obtiene las afiliaciones de cuenta bancaria del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<AccountAffiliation>
     */
    @Override
    public
    Flux<AccountAffiliation>
    getAccountAffiliationByIdCustomer(String idCustomer) {
        log.info("[getAccountAffiliationByIdCustomer] Inicio:"+idCustomer);
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.AFFILIATION_ACCOUNT_GET_BY_IDCUSTOMER,
                                        idCustomer
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(AccountAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCustomerById] Error en la llamada:"
                                    + UriService.AFFILIATION_ACCOUNT_GET_BY_IDCUSTOMER
                                    + idCustomer);
                            return Flux.just(new AccountAffiliation());
                        });
    }
    /**
     * Obtenemos todos los clientes.
     * @return Flux<Customer>
     */
    @Override
    public
    Flux<Customer>
    getAllCustomer() {
        log.info("[getAllCustomer] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.CUSTOMER_ALL
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(Customer.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCustomerById] Error en la llamada:"
                                    + UriService.CUSTOMER_ALL);
                            return Flux.just(new Customer());
                        });
    }

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
    /**
     * Obtenemos los datos del producto: Credito.
     * @param idCredit codigo del credito
     * @return Mono<Credit>
     */
    @Override
    public
    Mono<Credit>
    getProductCreditById(String idCredit) {
        log.info("[getProductCreditById] Inicio");
        return reactiveCircuitBreaker
                .run(
                    webClient
                            .get()
                            .uri(
                                    UriService.PRODUCT_CREDIT_GET_BY_ID,
                                    idCredit
                            )
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(Credit.class),
                    throwable -> {
                        log.info("throwable => {}", throwable.toString());
                        log.info("[getProductAccountById] Error en la llamada:"
                                + UriService.PRODUCT_CREDIT_GET_BY_ID
                                + idCredit);
                        return Mono.just(new Credit());
                    });
    }

    /**
     * Obtiene las afiliaciones de credito del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<CreditAffiliation>
     */
    @Override
    public
    Flux<CreditAffiliation>
    getCreditAffiliationByIdCustomer(String idCustomer) {
        log.info("[getCreditAffiliationByIdCustomer] Inicio:"+idCustomer);
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.AFFILIATION_CREDIT_GET_BY_IDCUSTOMER,
                                        idCustomer
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(CreditAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCustomerById] Error en la llamada:"
                                    + UriService.AFFILIATION_CREDIT_GET_BY_IDCUSTOMER
                                    + idCustomer);
                            return Flux.just(new CreditAffiliation());
                        });
    }

    @Override
    public
    Flux<BalanceReport>
    getBalanceReport() {
        return null;
    }
}
