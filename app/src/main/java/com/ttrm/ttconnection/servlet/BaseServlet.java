package com.ttrm.ttconnection.servlet;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ttrm.ttconnection.entity.BaseBean;

import java.lang.reflect.Type;

/**
 * Created by MaRufei
 * time on 2017/8/17
 * Phone 13213580912
 * Email 867814102@qq.com
 */

public class BaseServlet<P, S, T> extends AsyncTask<P, S, T> {

    private OnLoadListener<P, S, T> mListener;
    private int mDataType;
    private Exception ex;

    public BaseServlet(int dataType) {
        mDataType = dataType;

        mListener = new OnLoadListener<P, S, T>() {
            @Override
            public void onDataStart() {
            }

            @Override
            public T doInWorkerThread(int dataType, P... params)
                    throws Exception {
                return null;
            }

            @Override
            public void onDataGet(T result) {
            }

            @Override
            public void onDataFail(Exception e) {
            }

            @Override
            public void onDataFinish() {
            }

            @Override
            public void onDataProgress(S... values) {
            }

        };
    }

    public void setOnLoadListener(OnLoadListener<P, S, T> listener) {
        if (listener != null) {
            mListener = listener;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mListener.onDataStart();
    }

    @Override
    protected T doInBackground(P... params) {
        try {
            return (T) mListener.doInWorkerThread(mDataType,params);
        } catch (Exception e) {
            e.printStackTrace();
            ex = e;
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(S... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(T result) {
        super.onPostExecute(result);
        if (ex != null) {
            mListener.onDataFail(ex);
        } else {
            mListener.onDataGet(result);
        }
        mListener.onDataFinish();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mListener.onDataFinish();
    }

    /**
     *
     * @Description：异步数据回调接口类
     */
    public interface OnLoadListener<P, S, T> {

        /**
         *
         * @Description：异步数据加载开始时回调
         */
        void onDataStart();

        /**
         *
         * @Description：异步数据请求回调，运行子线程中
         * @param dataType
         * @return
         * @throws Exception
         *
         */
        T doInWorkerThread(int dataType,P... params) throws Exception;

        /**
         *
         * @Description：异步数据进行的进度回调，用来显示进度条或更新UI等
         * @param values
         * @return
         * @throws Exception
         *
         */
        void onDataProgress(S... values);

        /**
         *
         * @Description：异步数据返回时回调
         * @param result
         * void
         */
        void onDataGet(T result);

        /**
         *
         * @Description：异步数据请求失败时回调
         * @param e
         * void
         */
        void onDataFail(Exception e);

        /**
         *
         * @Description：异步数据结束时回调
         * void
         */
        void onDataFinish();

    }

}
