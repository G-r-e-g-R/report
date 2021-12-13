package com.nttdata.report.application;

import com.nttdata.report.domain.CreditMovementData;
import com.nttdata.report.domain.CreditMovementReport;
import com.nttdata.report.domain.CreditMovementReport;
import com.nttdata.report.domain.bean.Credit;
import com.nttdata.report.domain.bean.CreditAffiliation;
import com.nttdata.report.domain.bean.CreditMovement;
import com.nttdata.report.domain.bean.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITREPORTOPERATIONSIMPL.
 * Implementa las consultas (Reportes) de los movimientos
 *                         de Credito de un cliente.
 */
@Service
@Slf4j
public class CreditMovementReportOperationsImpl
        implements CreditMovementReportOperations {
    /**
     * Repositorio de las consultas de las creditos.
     */
    private final CreditMovementReportRepository repository;

    /**
     * Constructor.
     * @param creditReportRepository
     */
    public CreditMovementReportOperationsImpl(CreditMovementReportRepository creditReportRepository){
        this.repository = creditReportRepository;
    }

    @Override
    public
    Flux<CreditMovementReport>
    getCreditMovementReport(String idCustomer, String idCredit) {
        //Obtenemos las afiliaciones
        Flux<CreditAffiliation>  affiliations = getCreditAffiliationByIdCustomerByIdCredit(idCustomer, idCredit);
        //Recorremos todas las afiliaciones
        Flux<CreditMovementData> creditMovementData =
                affiliations.flatMap(affiliation -> {
                    //Obtenemos los movimientos
                    Flux<CreditMovement> movements = getCreditMovementByIdAffiliation(affiliation.getId());
                    return
                            movements.flatMap(movement -> {
                                CreditMovementData data = new CreditMovementData();
                                data.setMovementDate(movement.getMovementDate());
                                data.setAmount(movement.getAmount());
                                data.setMovementType(movement.getMovementType());
                                return Flux.just(data);
                            }).switchIfEmpty(Flux.just(new CreditMovementData()));
                }).switchIfEmpty(Flux.just(new CreditMovementData()));
        //Preparamos el reporte con los datos de los movimientos
        CreditMovementReport report = new CreditMovementReport();
        //Asignamos los datos del credito
        getProductCreditById(idCredit).map(credit -> {
            report.setCredit(credit);
            return credit;
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
     * @param idCredit Codigo de la cuenta.
     * @return Flux<CreditAffiliation>
     */
    @Override
    public
    Flux<CreditAffiliation>
    getCreditAffiliationByIdCustomerByIdCredit(String idCustomer, String idCredit){
        return repository.getCreditAffiliationByIdCustomerByIdCredit(idCustomer, idCredit);
    }

    /**
     * Obtenemos los datos de los movimientos de una afiliacion.
     * @param idAffiliation codigo de afiliacion.
     * @return Flux<CreditMovement>
     */
    @Override
    public
    Flux<CreditMovement>
    getCreditMovementByIdAffiliation(String idAffiliation) {
        return repository.getCreditMovementByIdAffiliation(idAffiliation);
    }

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    @Override
    public Mono<Customer>
    getCustomerById(String idCustomer) {
        log.info("[getCustomerById] idCredit:"+idCustomer);
        return repository.getCustomerById(idCustomer);
    }

    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idCredit codigo de la cuenta bancaria
     * @return Flux<Credit>
     */
    @Override
    public
    Mono<Credit>
    getProductCreditById(String idCredit) {
        log.info("[getProductCreditById] idCredit:"+idCredit);
        return repository.getProductCreditById(idCredit);
    }
}
