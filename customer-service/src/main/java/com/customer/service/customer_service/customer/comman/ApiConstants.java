package com.customer.service.customer_service.customer.comman;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_BASE = "/api/v1/customers";

    public static final String CREATE_CUSTOMER = "";
    public static final String UPDATE_CUSTOMER = "/{customerCode}";
    public static final String DELETE_CUSTOMER = "/{customerCode}";
    public static final String GET_CUSTOMER = "/{customerCode}";
    public static final String GET_CUSTOMER_LIST = "";
    public static final String QUERY_MERCHANT_ID = "merchantId";
}

