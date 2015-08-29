package com.sarltokyo.dagger2sladmobilereader.asynctask;

import android.content.Context;
import com.sarltokyo.dagger2sladmobilereader.App;
import com.sarltokyo.dagger2sladmobilereader.model.RssData;

import javax.inject.Inject;

/**
 * Created by osabe on 15/08/29.
 */
public class RssListLoader extends AbstractAsyncTaskLoader<RssData> {

    private String mParam;

    @Inject
    public RssData mRssData;

    public RssListLoader(Context context, String param) {
        super(context);
        mParam = param;

        // ここに書くと、プロダクション環境で、
        // android.os.NetworkOnMainThreadException が発生する
//        App.getInstance().component().inject(this);
    }

    @Override
    public RssData loadInBackground() {
//        mRssData = new RssApi(mParam).getRssData();

        // ここだと、OK
        App.getInstance().component().inject(this);

        return mRssData;
    }
}
