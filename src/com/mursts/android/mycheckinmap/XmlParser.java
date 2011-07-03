package com.mursts.android.mycheckinmap;

import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlParser {

    public static ArrayList<CheckinInfo> parser(String xml) throws Exception {
        ArrayList<CheckinInfo> checkinList = new ArrayList<CheckinInfo>();
        ArrayList<String> dupList = new ArrayList<String>();

        final XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        final XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));

        String title = null;
        String link = null;
        String date = null;
        String geo = null;
        CheckinInfo checkin;

        int e = parser.getEventType();
        while(e != XmlPullParser.END_DOCUMENT) {
            switch(e) {
            case XmlPullParser.START_TAG:
                String tag = parser.getName();
                if(parser.getDepth() == 4) {
                    if("title".equals(tag)) {
                        title = parser.nextText();
                    }
                    if("link".equals(tag)) {
                        link = parser.nextText();
                    }
                    if("pubDate".equals(tag)) {
                        date = parser.nextText();
                    }
                    if("georss:point".equals(tag)) {
                        geo = parser.nextText();
                    }
                }
                break;
            case XmlPullParser.END_TAG:
                if(parser.getDepth() == 3) {
                    if("item".equals(parser.getName())) {
                        if(!dupList.contains(title)) {
                            checkin = new CheckinInfo(title, link, date, geo);
                            checkinList.add(checkin);
                            dupList.add(title);
                        }
                    }
                }
                break;
            }
            e = parser.next();
        }
        return checkinList;
    }
}
