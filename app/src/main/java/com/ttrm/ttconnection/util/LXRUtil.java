package com.ttrm.ttconnection.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.tencent.mm.opensdk.channel.MMessageActV2;
import com.ttrm.ttconnection.MainActivity;
import com.ttrm.ttconnection.activity.LocationAddActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/1/4.
 */

public class LXRUtil {
    static String TAG = "TAG--LXRUtil";
    static int addNum = 0;
    static int delNum = 0;

    /**
     * 添加联系人到通讯录
     *
     * @param mContext
     * @param name
     * @param phone
     */

    public static void addContacts(Activity mContext, String name, String phone, int index, int type,boolean isLast) {
        try {
            ContentValues values = new ContentValues();
            // 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
            Uri rawContactUri = mContext.getContentResolver().insert(
                    ContactsContract.RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);

            // 往data表插入姓名数据
            values.clear();
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);// 内容类型
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, "天天人脉-" + name);
            mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
            values.clear();
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.CommonDataKinds.Note.NOTE, "天天人脉");
            mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);
            // 往data表插入电话数据
            values.clear();
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
            values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,
                    values);

            MyUtils.Loge(TAG, "添加成功" + index);
        } catch (Exception e) {

        }

        MyUtils.Loge(TAG,"isLast:"+isLast);
/*        if(isLast) {
            //添加通讯录，是否成功都发送所添加的条目下表

            Message message = Message.obtain();
            message.what = KeyUtils.SAVE_CODE;
            message.obj = index + 1;
            switch (type) {
                case 1:
                    MainActivity.handler.sendMessage(message);
                    break;
                case 2:
                    LocationAddActivity.handler.sendMessage(message);
                    break;
            }
        }*/
        if(type==1){
            if(isLast){
                Message message = Message.obtain();
                message.what = KeyUtils.SAVE_CODE;
                message.obj = index + 1;
                MainActivity.handler.sendMessage(message);
            }else {
                Message message= Message.obtain();
                message.what=KeyUtils.LOADING_CODE;
                message.obj=index+1;
                MainActivity.handler.sendMessage(message);
            }
        }
        if(type==2){
            if(isLast){
                Message message = Message.obtain();
                message.what = KeyUtils.SAVE_CODE;
                message.obj = index + 1;
                LocationAddActivity.handler.sendMessage(message);
            }else {
                Message message= Message.obtain();
                message.what=KeyUtils.LOADING_CODE;
                message.obj=index+1;
                LocationAddActivity.handler.sendMessage(message);
            }
        }
    }

    /**
     * 清除手机通讯录里包含“天天人脉”的联系人
     *
     * @param mContext
     */

    public static void deleteContacts(Activity mContext) {
        try {
            List<String> nameList = new ArrayList<>();
            Cursor cursor = mContext.managedQuery(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) != null) {
                    if (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).contains("天天人脉")) {
                        nameList.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    }
                }
            }
            for (int i = 0; i < nameList.size(); i++) {
                String name = nameList.get(i);
                //根据姓名求id
                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                ContentResolver resolver = mContext.getContentResolver();
                Cursor cursor1 = resolver.query(uri, new String[]{ContactsContract.Contacts.Data._ID}, "display_name=?", new String[]{name}, null);
                if (cursor1.moveToFirst()) {
                    int id = cursor1.getInt(0);
                    //根据id删除data中的相应数据
                    resolver.delete(uri, "display_name=?", new String[]{name});
                    uri = Uri.parse("content://com.android.contacts/data");
                    resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
                    Log.e(TAG, "删除成功");
                    delNum++;
                }
                cursor1.close();
            }
        } catch (Exception e) {
            MyUtils.Loge("AAA", "");
        }
        Message message = Message.obtain();
        message.what = KeyUtils.DELETE_CODE;
        MainActivity.handler.sendMessage(message);
    }
}
