package com.sarltokyo.dagger2sladmobilereader.model;

import java.util.List;

/**
 * Created by osabe on 15/08/29.
 */
public class RssData {

    /** Rssリストの取得の成否を保持する。成功した時: true */
    boolean mSuccess = false;

    /** Rssリスト取得に失敗したとき、エラーメッセージを保持する */
    String mErrorMessage;

    List<Item> mItems;

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }
}
