package com.nttdata.report.infraestructure.client;

/**
 * Clase que contiene los End Points de los servicios de Cliente y Producto.
 */
public class UriService {
    /**
     * Constructor.
     */
    protected  UriService() {
        super();
    }

    /**
     * URI base para obtener los servicios.
     */
    public static final String BASE_URI = "http://localhost:8092";

    /**
     * Servicios de Customer: Obtener todos los clientes.
     */
    public static final String CUSTOMER_ALL = "/customers";

    /**
     * Servicios de Customer: Obtener un cliente.
     */
    public static final String CUSTOMER_GET_BY_ID = "/customers/{id}";

    /**
     * Servicios de Producto: Obtener un producto de cuenta bancaria.
     */
    public static final
    String PRODUCT_ACCOUNT_GET_BY_ID = "/products/account/{id}";

    /**
     * Servicios de Producto: Obtener un producto de credito.
     */
    public static final
    String PRODUCT_CREDIT_GET_BY_ID = "/products/credit/{id}";
    /**
     * Servicio de Afiliación: Obtiene la afiliación de cuenta bancaria.
     */
    public static  final
    String AFFILIATION_ACCOUNT_GET_BY_ID = "/affiliations/accounts";

    public static final
    String AFFILIATION_ACCOUNT_GET_BY_IDCUSTOMER = "/affiliations/accounts/customers/{id}";
    /**
     * Servicio de Afiliación: Listado de Afiliaciones por cliente y por cuenta.
     */
    public static final
    String AFFILIATION_ACCOUNT_GET_BY_IDCUSTOMER_BY_IDACCOUNT = "/affiliations/accounts/{idCustomer}/{idAccount}";
    /**
     * Servicio de Afiliación: Obtiene la afiliación de credito.
     */
    public static final
    String AFFILIATION_CREDIT_GET_BY_ID = "/affiliations/credits";
    /**
     * Servicio de Afiliación: Listado de Afiliaciones de creditos por cliente.
     */
    public static final
    String AFFILIATION_CREDIT_GET_BY_IDCUSTOMER ="/affiliations/credits/customers/{id}";
    /**
     * Servicio de Afiliación: Listado de Afiliaciones por cliente y por credito.
     */
    public static final
    String AFFILIATION_CREDIT_GET_BY_IDCUSTOMER_BY_IDCREDIT = "/affiliations/credits/{idCustomer}/{iCredit}";
    /**
     * Servicio de Transaction: Listado de movimientos por afiliacion de cuentas.
     */
    public static final
    String TRANSACTION_ACCOUNT_GET_BY_IDAFFILIATION = "/transactions/accounts/affiliation/{id}";
    /**
     * Servicio de Transaction: Listado de movimientos por afiliacion de credito.
     */
    public static final
    String TRANSACTION_CREDIT_GET_BY_IDAFFILIATION = "/transactions/credits/affiliation/{id}";
}
