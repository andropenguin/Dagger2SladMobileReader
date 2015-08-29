package com.sarltokyo.dagger2sladmobilereader;

import android.app.Application;
import com.sarltokyo.dagger2sladmobilereader.app.RssListFragment;
import com.sarltokyo.dagger2sladmobilereader.component.RssDataComponent;

/**
 * Created by osabe on 15/08/29.
 */
public class App extends Application {

    private static App sInstance;
    private RssDataComponent component;
    private String mParam;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
//        component = RssDataComponent.Initializer.init(false, mParam);
        component = RssDataComponent.Initializer.init(false, RssListFragment.RSS_FEED_URL);
    }

    public static App getInstance() {
        return sInstance;
    }

    public RssDataComponent component() {
        return component;
    }

    public void setMockMode(boolean useMock) {
        component = RssDataComponent.Initializer.init(useMock, mParam);
    }

//    public void setParm(String param) {
//        mParam = param;
//    }
}

