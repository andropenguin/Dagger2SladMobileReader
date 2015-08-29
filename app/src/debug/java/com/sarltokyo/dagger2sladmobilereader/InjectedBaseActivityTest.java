package com.sarltokyo.dagger2sladmobilereader;

import android.test.ActivityInstrumentationTestCase2;
import com.sarltokyo.dagger2sladmobilereader.app.MainActivity;
import com.sarltokyo.dagger2sladmobilereader.app.RssListFragment;
import com.sarltokyo.dagger2sladmobilereader.data.RssData;

import javax.inject.Inject;

/**
 * Created by osabe on 15/08/29.
 */
public class InjectedBaseActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    @Inject
    RssData mockRssData;

    public InjectedBaseActivityTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        App app =
                (App)getInstrumentation().getTargetContext().getApplicationContext();
//        app.setParm(RssListFragment.RSS_FEED_URL); // todo
        app.setMockMode(true);
        app.component().inject(this);
    }

    @Override
    protected void tearDown() throws Exception {
        App.getInstance().setMockMode(false);
    }

//    protected RssData getRssData() {
//        return mockRssData;
//    }
}
