package com.document.service.document_service.document.comman;

public final class JobCardApiConstants {

    private JobCardApiConstants() {
    }

    public static final String JOB_CARD_BASE_URL = "/api/v1/job-cards";

    public static final String CREATE_JOB_CARD = "";

    public static final String GET_JOB_CARD = "/{jobCardId}";

    public static final String GET_ALL_JOB_CARDS = "";

    public static final String GET_JOB_CARDS_BY_CUSTOMER = "/customer/{customerId}";

    public static final String GET_JOB_CARDS_BY_VEHICLE = "/vehicle/{vehicleId}";

    public static final String GET_JOB_CARDS_BY_MERCHANT = "/merchant/{merchantId}";

    public static final String UPDATE_JOB_CARD = "/{jobCardId}";

    public static final String DELETE_JOB_CARD = "/{jobCardId}";

    public static final String GET_JOB_CARDS_BY_STATUS = "/status";
}

