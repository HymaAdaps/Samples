package com.customcalendar;

public interface IParserListener<T> {

    void successResponse(int requestCode, T response);

    void unSuccessResponse(int requestCode, T response);

    void errorResponse(int requestCode, String error);

    void noInternetConnection(int requestCode);

}