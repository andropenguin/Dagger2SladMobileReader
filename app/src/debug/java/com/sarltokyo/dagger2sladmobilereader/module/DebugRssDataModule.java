package com.sarltokyo.dagger2sladmobilereader.module;

import com.sarltokyo.dagger2sladmobilereader.model.RssApi;
import com.sarltokyo.dagger2sladmobilereader.model.RssData;
import static org.mockito.Mockito.mock;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by osabe on 15/08/29.
 */

@Module
public class DebugRssDataModule {
    private final boolean mockMode;
    private String mParam;

    public DebugRssDataModule(boolean provideMocks, String param) {
        mockMode = provideMocks;
        mParam = param;
    }

    @Provides
    @Singleton
    RssData provideRssData() {
        if (mockMode) {
           return mock(RssData.class);
        } else {
            return new RssApi(mParam).getRssData();
        }
    }
}
