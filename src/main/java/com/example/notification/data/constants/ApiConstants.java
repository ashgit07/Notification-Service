package com.example.notification.data.constants;

public class ApiConstants {
    public static final String BASE_URL = "/v1";
    public static final String HEALTH_CHECK = "/health";
    public static final String SEND_SMS = "/sms/send";
    public static final String GET_ALL_BLACKLIST = "/blacklist";
    public static final String ADD_TO_BLACKLIST = "/blacklist";
    public static final String REMOVE_FROM_BLACKLIST = "/blacklist/remove";
    public static final String GET_SMS= "/sms/get";
//    public static final String GET_ALL_SMS_OF_PHONE_NUMBER = "/sms/get";
    public static final String GET_ALL_SMS_OF_PHONE_NUMBER_WITH_TIME = "/sms/get/time";


    public static final String GET_ALL_SMS_WITH_TEXT = "/sms/get/text" ;

    public static  final String AUTHENTICATION_VALUE = "${spring.authentication.access.value}";
}
