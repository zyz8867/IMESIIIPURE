package com.sunlandgroup.network;


public interface OnRequestCallback {
    <T>void onRequestFinish(String reqId, String reqName, T bean);

    Class<?> getBeanClass(String reqId, String reqName);
}
