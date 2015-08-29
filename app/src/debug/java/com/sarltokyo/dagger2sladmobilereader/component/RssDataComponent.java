package com.sarltokyo.dagger2sladmobilereader.component;

import com.sarltokyo.dagger2sladmobilereader.InjectedBaseActivityTest;
import com.sarltokyo.dagger2sladmobilereader.app.RssListFragment;
import com.sarltokyo.dagger2sladmobilereader.asynctask.RssListLoader;
import com.sarltokyo.dagger2sladmobilereader.module.DebugRssDataModule;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by osabe on 15/08/29.
 */
@Singleton
@Component(modules = {DebugRssDataModule.class})
public interface RssDataComponent {

    void inject(RssListLoader loader);
    void inject(InjectedBaseActivityTest test);

    public final static class Initializer {
        public static RssDataComponent init(boolean mockMode) {
            String param = RssListFragment.getParam();

            return DaggerRssDataComponent.builder()
                    .debugRssDataModule(new DebugRssDataModule(mockMode, param))
                    .build();
        }
    }
}


