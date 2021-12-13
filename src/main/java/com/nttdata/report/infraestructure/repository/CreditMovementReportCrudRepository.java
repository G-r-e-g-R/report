package com.nttdata.report.infraestructure.repository;

import com.nttdata.report.application.CreditMovementReportRepository;
import com.nttdata.report.domain.CreditBalanceData;
import com.nttdata.report.domain.CreditMovementReport;
import com.nttdata.report.domain.CreditBalanceReport;
import com.nttdata.report.domain.CreditMovementReport;
import com.nttdata.report.domain.bean.Credit;
import com.nttdata.report.domain.bean.CreditAffiliation;
import com.nttdata.report.domain.bean.CreditMovement;
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
 * CREDITREPORTREPOSITORY.
 * Implementa las consultas (Reporte) de credito.
 */
@Component
@Slf4j
public class CreditMovementReportCrudRepository
        implements CreditMovementReportRepository {
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
    public CreditMovementReportCrudRepository(
            final ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder()
                .baseUrl(UriService.BASE_URI)
                .build();
        this.reactiveCircuitBreaker = circuitBreakerFactory.create("creditReport");
    }

    /**
     * Obtiene los saldos del producto
     * @param creditAffiliation
     * @return
     */
    private CreditBalanceData setBalance(CreditAffiliation creditAffiliation){
        CreditBalanceData creditBalanceData = new CreditBalanceData();
        creditBalanceData.setBalance(creditAffiliation.getBalance());
        Mono<Credit> credit = getProductCreditById(creditAffiliation.getIdCredit());
        credit.map(a -> {
            creditBalanceData.setCreditType(a.getCreditType());
            return a;
        });
        return creditBalanceData;
    }
    @Override
    public Flux<CreditMovementReport> getCreditMovementReport(String idCustomer, String idCredit) {
        return null;
    }

    /**
     * Obtiene las afiliaciones de credito de un cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<CreditAffiliation>
     */
    @Override
    public
    Flux<CreditAffiliation>
    getCreditAffiliationByIdCustomerByIdCredit(String idCustomer, String idCredit) {
        log.info("[getCreditAffiliationByIdCustomer] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.AFFILIATION_CREDIT_GET_BY_IDCUSTOMER_BY_IDCREDIT,
                                        idCustomer,
                                        idCredit
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(CreditAffiliation.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCreditAffiliationByIdCustomer] Error en la llamada:"
                                    + UriService.AFFILIATION_CREDIT_GET_BY_IDCUSTOMER_BY_IDCREDIT
                                    + idCustomer + "-" +idCredit);
                            return Flux.just(new CreditAffiliation());
                        });
    }

    /**
     * Obtiene los movimientos del credito por codigo de afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @return
     */
    @Override
    public
    Flux<CreditMovement>
    getCreditMovementByIdAffiliation(String idAffiliation) {
        log.info("[getCreditMovementByIdAffiliation] Inicio");
        return reactiveCircuitBreaker
                .run(
                        webClient
                                .get()
                                .uri(
                                        UriService.TRANSACTION_CREDIT_GET_BY_IDAFFILIATION,
                                        idAffiliation
                                )
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .bodyToFlux(CreditMovement.class),
                        throwable -> {
                            log.info("throwable => {}", throwable.toString());
                            log.info("[getCreditMovementByIdAffiliation] Error en la llamada:"
                                    + UriService.TRANSACTION_CREDIT_GET_BY_IDAFFILIATION
                                    + idAffiliation );
                            return Flux.just(new CreditMovement());
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
     * Obtenemos los datos del producto: Credito.
     * @param idCredit codigo de credito.
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
                            log.info("[getProductCreditById] Error en la llamada:"
                                    + UriService.PRODUCT_CREDIT_GET_BY_ID
                                    + idCredit);
                            return Mono.just(new Credit());
                        });
    }

}
