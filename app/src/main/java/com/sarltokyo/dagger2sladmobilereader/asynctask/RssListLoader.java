package com.sarltokyo.dagger2sladmobilereader.asynctask;

import android.content.Context;
import android.util.Xml;
import com.sarltokyo.dagger2sladmobilereader.App;
import com.sarltokyo.dagger2sladmobilereader.data.Item;
import com.sarltokyo.dagger2sladmobilereader.data.RssApi;
import com.sarltokyo.dagger2sladmobilereader.data.RssData;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
//        App.getInstance().setParm(param);
//        App.getInstance().setMockMode(false);
//        App.getInstance().component().inject(this);
    }

    @Override
    public RssData loadInBackground() {
////        mRssData = getRssData(mParam);
//        mRssData = new RssApi(mParam).getRssData();

        // ここだと、OK
//        App.getInstance().setParm(mParam);
//        App.getInstance().setMockMode(false);
        App.getInstance().component().inject(this);

        return mRssData;
    }

//    public RssData getRssData(String param) {
//        RssData rssData;
//        try {
//            InputStream is = getInputStream(mParam);
//            List<Item> items = parseXml(is);
//            rssData = new RssData();
//            rssData.setItems(items);
//            rssData.setSuccess(true);
//        } catch (IOException e) {
//            rssData = new RssData();
//            rssData.setSuccess(false);
//            rssData.setErrorMessage(e.toString());
//        } catch (XmlPullParserException e) {
//            rssData = new RssData();
//            rssData.setSuccess(false);
//            rssData.setErrorMessage(e.toString());
//        }
//        return rssData;
//    }
//
//    public InputStream getInputStream(String param) throws IOException {
//        URL url = new URL(param);
//        return url.openConnection().getInputStream();
//    }
//
//    // XMLをパースする
//    public List<Item> parseXml(InputStream is)  throws IOException, XmlPullParserException {
//        List<Item> items = new ArrayList<Item>();
//        XmlPullParser parser = Xml.newPullParser();
//        try {
//            parser.setInput(is, null);
//            int eventType = parser.getEventType();
//            Item currentItem = null;
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                String tag = null;
//                switch (eventType) {
//                    case XmlPullParser.START_TAG:
//                        tag = parser.getName();
//                        if (tag.equals("item")) {
//                            currentItem = new Item();
//                        } else if (currentItem != null) {
//                            if (tag.equals("title")) {
//                                currentItem.setTitle(parser.nextText());
//                            } else if (tag.equals("link")) {
//                                currentItem.setLink(parser.nextText());
//                            } else if (tag.equals("description")) {
//                                currentItem.setDescription(htmlTagRemover(parser.nextText()));
//                            }
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        tag = parser.getName();
//                        if (tag.equals("item")) {
//                            items.add(currentItem);
//                        }
//                        break;
//                }
//                eventType = parser.next();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        } catch (XmlPullParserException e) {
//            throw e;
//        }
//        return items;
//    }
//
//    /**
//     * HTMLタグ削除（すべて）
//     * http://it--trick.appspot.com/article/30051/40001/70001/70002.html
//     *
//     * @param str 文字列
//     * @return HTMLタグ削除後の文字列
//     */
//    public static String htmlTagRemover(String str) {
//        // 文字列のすべてのタグを取り除く
//        return str.replaceAll("<.+?>", "");
//    }
}
