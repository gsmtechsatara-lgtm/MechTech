package com.document.service.document_service.document.comman;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String BASE_URL = "/api/v1/documents";

    public static final String UPLOAD_DOCUMENT = "/upload";

    public static final String UPDATE_DOCUMENT = "/{documentId}";

    public static final String DELETE_DOCUMENT = "/{documentId}";

    public static final String GET_DOCUMENT = "/{documentId}";

    public static final String GET_BY_REFERENCE = "/reference";

    public static final String DOWNLOAD_DOCUMENT = "/download/{documentId}";
}

