package com.ttrm.ttconnection.entity;

import android.provider.ContactsContract;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class BaseBean<T> {
    private int errorCode;
    private String errorMsg;
    private BData<T> data;
}
