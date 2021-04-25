package com.you.system.util;

public class ResultEntity<T> {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";
    private String operationResult;
    private String operationMessage;
    private T queryData;

    public static <E> ResultEntity<E> successWithoutData() {
        return new ResultEntity("SUCCESS", "NO_MESSAGE", (Object) null);
    }

    public static <E> ResultEntity<E> successWithData(E data) {
        return new ResultEntity("SUCCESS", "NO_MESSAGE", data);
    }

    public static <E> ResultEntity<E> failed(String message) {
        return new ResultEntity("FAILED", message, (Object) null);
    }

    public ResultEntity() {
    }

    public ResultEntity(String operationResult, String operationMessage, T queryData) {
        this.operationResult = operationResult;
        this.operationMessage = operationMessage;
        this.queryData = queryData;
    }

    public String toString() {
        return "AjaxResultEntity [operationResult=" + this.operationResult + ", operationMessage=" + this.operationMessage + ", queryData=" + this.queryData + "]";
    }

    public String getOperationResult() {
        return this.operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOperationMessage() {
        return this.operationMessage;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public T getQueryData() {
        return this.queryData;
    }

    public void setQueryData(T queryData) {
        this.queryData = queryData;
    }

}