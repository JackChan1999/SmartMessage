package com.google.smartmessage.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：SmartMessage
 * Package_Name：PACKAGE_NAME
 * Version：1.0
 * time：2016/2/16 12:35
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class DatabaseLoader extends AsyncTaskLoader<Cursor> {

    private Uri mNotificationUri;
    private String mTable;
    private String[] mColumns;
    private String mSelection;
    private String[] mSelectionArgs;
    private String mGroupBy;
    private String mHaving;
    private String mOrderBy;
    private SQLiteOpenHelper mSqLiteOpenHelper;

    private Cursor mCursor;
    final ForceLoadContentObserver mObserver;

    public DatabaseLoader(Context context) {
        super(context);
        this.mObserver = new ForceLoadContentObserver();
    }

    public DatabaseLoader(Context context, SQLiteOpenHelper sqLiteOpenHelper, Uri mNotificationUri,
                          String mTable, String[] mColumns, String mSelection, String[] mSelectionArgs,
                          String mGroupBy, String mHaving, String mOrderBy) {
        super(context);
        this.mNotificationUri = mNotificationUri;
        this.mTable = mTable;
        this.mColumns = mColumns;
        this.mSelection = mSelection;
        this.mSelectionArgs = mSelectionArgs;
        this.mGroupBy = mGroupBy;
        this.mHaving = mHaving;
        this.mOrderBy = mOrderBy;
        this.mSqLiteOpenHelper = sqLiteOpenHelper;
        this.mObserver = new ForceLoadContentObserver();
    }

    @Override
    public Cursor loadInBackground() {
        SQLiteDatabase database = mSqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(mTable, mColumns, mSelection, mSelectionArgs, mGroupBy,
                mHaving, mOrderBy);
        if (cursor != null) {
            cursor.getCount();
            cursor.registerContentObserver(mObserver);
            cursor.setNotificationUri(getContext().getContentResolver(), mNotificationUri);
        }
        return cursor;
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;

        if (isStarted()) {
            super.deliverResult(cursor);
        }

        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        mCursor = null;
    }

    public SQLiteOpenHelper getSqLiteOpenHelper() {
        return mSqLiteOpenHelper;
    }

    public void setSqLiteOpenHelper(SQLiteOpenHelper mSqLiteOpenHelper) {
        this.mSqLiteOpenHelper = mSqLiteOpenHelper;
    }

    public Uri getNotificationUri() {
        return mNotificationUri;
    }

    public void setNotificationUri(Uri mNotificationUri) {
        this.mNotificationUri = mNotificationUri;
    }

    public String getTable() {
        return mTable;
    }

    public void setTable(String mTable) {
        this.mTable = mTable;
    }

    public String[] getColumns() {
        return mColumns;
    }

    public void setColumns(String[] mColumns) {
        this.mColumns = mColumns;
    }

    public String getSelection() {
        return mSelection;
    }

    public void setSelection(String mSelection) {
        this.mSelection = mSelection;
    }

    public String[] getSelectionArgs() {
        return mSelectionArgs;
    }

    public void setSelectionArgs(String[] mSelectionArgs) {
        this.mSelectionArgs = mSelectionArgs;
    }

    public String getGroupBy() {
        return mGroupBy;
    }

    public void setGroupBy(String mGroupBy) {
        this.mGroupBy = mGroupBy;
    }

    public String getHaving() {
        return mHaving;
    }

    public void setHaving(String mHaving) {
        this.mHaving = mHaving;
    }

    public String getOrderBy() {
        return mOrderBy;
    }

    public void setOrderBy(String mOrderBy) {
        this.mOrderBy = mOrderBy;
    }
}