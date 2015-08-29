package com.sarltokyo.dagger2sladmobilereader.module;

import com.sarltokyo.dagger2sladmobilereader.data.RssApi;
import com.sarltokyo.dagger2sladmobilereader.data.RssData;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by osabe on 15/08/29.
 */
@Module
public class RssDataModule {
    private String mParam;

    RssDataModule(String param) {
        mParam = param;
    }

    @Provides
    @Singleton
    RssData provideRssData() {
        return new RssApi(mParam).getRssData();
    }
}

