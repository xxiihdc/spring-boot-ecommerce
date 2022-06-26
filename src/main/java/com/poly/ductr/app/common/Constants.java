package com.poly.ductr.app.common;

public class Constants {
    public static final Integer PRODUCT_STATUS_HIDE = 0;
    public static final Integer PRODUCT_STATUS_STOP_BUSINESS = 1;
    public static final Integer PRODUCT_STATUS_AVAILABLE = 2;
    public static final Integer PRODUCT_STATUS_PRE_ORDER = 3;
    public static final Integer PRODUCT_STATUS_OUT_OF_STOCK = 4;
    public static final String JWT_SECRET_KEY = "Nguyen_Kieu_Khanh_pretty_cute@123Your security configuration must be updated before running your application in production.";
    public static final long JWT_EXPIRATION_TIME =86400000L;
    public static final String USERNAME = "Username";

    public static final Integer ORDER_STATUS_WAIT_FOR_CONFIRM = 1;

    public static final Integer ORDER_STATUS_CANCEL = 0;

    public static final Integer ORDER_STATUS_DELIVERY = 2;

    public static final Integer ORDER_STATUS_DONE = 3;

    public static final String GOOGLE_CLIENT_ID = "866719052048-s4k9pnihvn9gp7rqfk66spf6bug0cnh2.apps.googleusercontent.com";

    public static final String GOOGLE_CLIENT_SECRET = "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";

    public static final String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";

    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";

    public static final String GOOGLE_GRANT_TYPE = "authorization_code";

    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";


}