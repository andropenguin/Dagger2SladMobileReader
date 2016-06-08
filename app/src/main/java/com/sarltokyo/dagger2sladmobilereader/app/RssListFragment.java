package com.sarltokyo.dagger2sladmobilereader.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.sarltokyo.dagger2sladmobilereader.adapter.RssListAdapter;
import com.sarltokyo.dagger2sladmobilereader.asynctask.RssListLoader;
import com.sarltokyo.dagger2sladmobilereader.model.Item;
import com.sarltokyo.dagger2sladmobilereader.model.RssData;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by osabe on 15/08/19.
 */
public class RssListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<RssData> {
    private final static String TAG = RssListFragment.class.getSimpleName();

    public static final String RSS_FEED_URL = "http://rss.rssad.jp/rss/slashdot/mobile.rss";
    private List<Item> mItems;
    private RssListAdapter mAdapter;

    public static String getParam() {
        return RSS_FEED_URL;
    }

    // アイテムがタップされたときのリスナー
    public interface OnListItemClickListener {
        public void onListItemClick(int position, Item item);
    }

    // アイテムがタップされたときのリスナー
    private OnListItemClickListener mOnListItemClickListener;

    // アイテムがタップされたときのリスナーをセット

    public void setOnListItemClickListener(OnListItemClickListener l) {
        mOnListItemClickListener = l;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // これがないと、詳細画面で、ActionBarを非表示にしたとき、リスト画面に戻った時、ActionBarが
        // 表示されない
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (!actionBar.isShowing()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }

        // Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
        mItems = new ArrayList<Item>();
        mAdapter = new RssListAdapter(getActivity(), mItems);

        // アダプタをリストビューにセットする
        setListAdapter(mAdapter);

        getRssDataByAstyncTaskLoader();
    }

    // リストのアイテムがタップされたときに呼び出される
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        showDetail(position);
    }

    private void showDetail(int position) {
        // タップされたときのリスナーのメソッドを呼び出す
        if (mOnListItemClickListener != null) {
            Item item = (Item)(mAdapter.getItem(position));
            mOnListItemClickListener.onListItemClick(position, item);
        }
    }

    void getRssDataByAstyncTaskLoader() {
        LoaderManager manager = getActivity().getSupportLoaderManager();
        if (manager.getLoader(0) != null) {
            manager.destroyLoader(0);
        }
        manager.initLoader(0, null, this);
    }

    protected void refreshRss() {
        getRssDataByAstyncTaskLoader();
    }

    void setRssResult(List<Item> items) {
        // todo: ダブリコード
        mAdapter = new RssListAdapter(getActivity(), items);
        setListAdapter(mAdapter);
    }

    void setRssError(String errorStr) {
        Toast.makeText(getActivity(), errorStr, Toast.LENGTH_LONG).show();
    }


    @Override
    public Loader<RssData> onCreateLoader(int id, Bundle bundle) {
        return new RssListLoader(getActivity(), RSS_FEED_URL);
    }

    @Override
    public void onLoadFinished(Loader<RssData> loader, RssData data) {
        if (data == null) {
            Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_LONG).show();
        }
        if (data.isSuccess()) {
            setRssResult(data.getItems());
        } else {
            setRssError(data.getErrorMessage());
        }
    }

    @Override
    public void onLoaderReset(Loader<RssData> loader) {
        // nop
    }
}
