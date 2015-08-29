package com.sarltokyo.dagger2sladmobilereader;

import android.app.Instrumentation;
import android.os.IBinder;
import android.support.test.espresso.Root;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;
import android.widget.ListView;
import com.sarltokyo.dagger2sladmobilereader.app.MainActivity;
import com.sarltokyo.dagger2sladmobilereader.app.R;
import com.sarltokyo.dagger2sladmobilereader.app.RssListFragment;
import com.sarltokyo.dagger2sladmobilereader.model.Item;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by osabe on 15/08/29.
 */
public class MainActivityTest extends InjectedBaseActivityTest {

    private final static int DUMMY_DATA_NUM = 10;
    private final static int DUMMY_REFRESH_DATA_NUM = 5;


    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testSuccess() throws Exception {
        // ダミーデータ作成
        List<Item> items = createDummyItems(DUMMY_DATA_NUM);

        when(mockRssData.getItems()).thenReturn(items);
        when(mockRssData.isSuccess()).thenReturn(true);

        MainActivity activity = getActivity();

        Instrumentation.ActivityMonitor monitor
                = getInstrumentation().addMonitor(MainActivity.class.getName(),
                null, true);

        // リストの行数のテスト
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.my_fragment);
        RssListFragment listFragment = (RssListFragment)fragment;
        final ListView listView = listFragment.getListView();
        assertThat(listView.getCount(), is(DUMMY_DATA_NUM));

        // リストの各行の内容表示のテスト
        for (int i = 0; i < DUMMY_DATA_NUM; i++) {
            onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(i)
                    .onChildView(withId(R.id.item_title))
                    .check(matches(withText("dummy title" + i)));

            onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(i)
                    .onChildView(withId(R.id.item_descr))
                    .check(matches(withText("dummy text" + i)));
        }

        getInstrumentation().removeMonitor(monitor);
    }

    @Test
    public void testToDetail() throws Exception {
        // ダミーデータ作成
        List<Item> items = createDummyItems(DUMMY_DATA_NUM);

        when(mockRssData.getItems()).thenReturn(items);
        when(mockRssData.isSuccess()).thenReturn(true);

        getActivity();

        Instrumentation.ActivityMonitor monitor
                = getInstrumentation().addMonitor(MainActivity.class.getName(),
                null, true);

        // 遷移先の画面の内容をテストする
        onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(0)
                .perform(click());
        onView(withId(R.id.item_detail_title)).check(matches(withText("dummy title0")));
        onView(withId(R.id.item_detail_link)).check(matches(withText("http://dummy0.com")));
        onView(withId(R.id.item_detail_descr)).check(matches(withText("dummy text0")));

        getInstrumentation().removeMonitor(monitor);
    }

    @Test
    public void testRefresh() throws Exception {
        // ダミーデータ作成
        List<Item> items = createDummyItems(DUMMY_DATA_NUM);

        when(mockRssData.getItems()).thenReturn(items);
        when(mockRssData.isSuccess()).thenReturn(true);

        MainActivity activity = getActivity();

        Instrumentation.ActivityMonitor monitor
                = getInstrumentation().addMonitor(MainActivity.class.getName(),
                null, true);

        // 更新用データ作成
        List<Item> refreshItems = createDummyRefreshItems(DUMMY_REFRESH_DATA_NUM);
        when(mockRssData.getItems()).thenReturn(refreshItems);
        when(mockRssData.isSuccess()).thenReturn(true);

        onView(withId(R.id.action_refresh)).perform(click());

        // リストの行数のテスト
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.my_fragment);
        RssListFragment listFragment = (RssListFragment)fragment;
        final ListView listView = listFragment.getListView();
        assertThat(listView.getCount(), is(DUMMY_REFRESH_DATA_NUM));

        // リストの各行の内容表示のテスト
        for (int i = 0; i < DUMMY_REFRESH_DATA_NUM; i++) {
            onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(i)
                    .onChildView(withId(R.id.item_title))
                    .check(matches(withText("dummy title" + (DUMMY_DATA_NUM +i))));

            onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(i)
                    .onChildView(withId(R.id.item_descr))
                    .check(matches(withText("dummy text" + (DUMMY_DATA_NUM + i))));
        }

        getInstrumentation().removeMonitor(monitor);
    }

    @Test
    public void testToastIsDisplayedByIOException() throws Exception {

        when(mockRssData.isSuccess()).thenReturn(false);
        when(mockRssData.getErrorMessage()).thenReturn("IOException");

        getActivity();

        onView(withText("IOException")).inRoot(isToast());
    }

    @Test
    public void testToastIsDisplayedByXmlPullParserException() throws Exception {

        when(mockRssData.isSuccess()).thenReturn(false);
        when(mockRssData.getErrorMessage()).thenReturn("XmlPullParserException");

        getActivity();

        onView(withText("XmlPullParserException")).inRoot(isToast());
    }


    /**
     * ダミーデータ作成
     * @param count
     * @return
     */
    private List<Item> createDummyItems(int count) {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < count; i++) {
            Item item = new Item();
            item.setTitle("dummy title" + i);
            item.setLink("http://dummy" + i + ".com");
            item.setDescription("dummy text" + i);
            items.add(item);
        }
        return items;
    }

    /**
     * 更新用データ作成
     * @param count
     * @return
     */
    private List<Item> createDummyRefreshItems(int count) {
        List<Item> refreshItems = new ArrayList<Item>();
        for (int i = DUMMY_DATA_NUM; i < DUMMY_DATA_NUM + count; i++) {
            Item item = new Item();
            item.setTitle("dummy title" + i);
            item.setLink("http://dummy" + i + ".com");
            item.setDescription("dummy text" + i);
            refreshItems.add(item);
        }
        return refreshItems;
    }

    /**
     * Matcher that is Toast window.
     * http://baroqueworksdevjp.blogspot.jp/2015/03/espressotoast.html
     */
    public static Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    if (windowToken == appToken) {
                        // windowToken == appToken means this window isn't contained by any other windows.
                        // if it was a window for an activity, it would have TYPE_BASE_APPLICATION.
                        return true;
                    }
                }
                return false;
            }
        };
    }
}

