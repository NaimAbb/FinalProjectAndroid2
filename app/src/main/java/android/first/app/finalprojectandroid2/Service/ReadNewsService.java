package android.first.app.finalprojectandroid2.Service;

import android.app.Service;
import android.content.Intent;
import android.first.app.finalprojectandroid2.News;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadNewsService extends Service {
    FirebaseFirestore firestore;
    RequestQueue requestQueue;
    Boolean isFinished=false;
    boolean isRunning = true;
    static HashMap<String , News> map = new HashMap<>();
    public ReadNewsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        News n3 = new News();
        n3.setTitle("jjjj");
        n3.setTime("Mon, 21 May 2019 22:03:22 +0000");
        n3.setDescription("wefwef");
        n3.setType("الرياضة");
        n3.setLink("wefwef");
        map.put("jjjj",n3);

        if (isRunning) {

            Toast.makeText(getApplicationContext(), "run", Toast.LENGTH_LONG).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for (int i = 0; i < 86400; i++) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (i == 86399) {
                                ArrayList<News> AllNews = new ArrayList<>();
                                ArrayList<News> ArabWorldNews = getArabWorldNews();
                                ArrayList<News> SportNews = getSportNews();
                                ArrayList<News> ConomyNews = getConomyNews();
                                ArrayList<News> EducationNews = getEducationNews();
                                ArrayList<News> PressNews = getPressNews();
                                ArrayList<News> ScienceNews = getScienceNews();
                                ArrayList<News> RealestateNews = getRealestateNews();
                                ArrayList<News> ArtsNews = getArtsNews();
                                ArrayList<News> TechNews = getTechNews();
                                ArrayList<News> HealthNews = getHealthNews();
                                ArrayList<News> TravelNews = getTravelNews();
                                ArrayList<News> PoliticalNews = getPoliticalNews();
                                ArrayList<News> AsiaNews = getAsiaNews();
                                ArrayList<News> AfricaNews = getAfricaNews();
                                ArrayList<News> EuropeNews = getEuropeNews();
                                ArrayList<News> AmericaNews = getAmericaNews();
                                ArrayList<News> GulfNews = getGulfNews();

                                for (String ll:map.keySet()){
                                    Log.d("ttt",ll);
                                }


                            /*if (!NotificationService.isRunning) {
                                Intent intent = new Intent(getApplicationContext(), NotificationService.class);
                                startService(intent);
                            }*/

                            }


                        }

                    }
                }
            }).start();
            return Service.START_STICKY;
        }else{
            return Service.START_NOT_STICKY;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    //----------------------------------------------------------------------------------------------
    public  ArrayList<News> getArabWorldNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> ArabWorldNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/arab-world";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("العالم العربي");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    ArabWorldNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:ArabWorldNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return ArabWorldNews;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getSportNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> SportNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/sport";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("الرياضة");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    SportNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:SportNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return SportNews;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getConomyNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> ConomyNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/economy";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("الاقتصاد");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    ConomyNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:ConomyNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    // throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return ConomyNews;

    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getEducationNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> EducationNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/education";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("التعليم");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    EducationNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:EducationNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    // throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return EducationNews;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getPressNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> PressNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/press";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("الإعلام");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    PressNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:PressNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return PressNews;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getScienceNews(){
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> ScienceNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/science";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("علوم");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    ScienceNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:ScienceNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return ScienceNews;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getRealestateNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> RealestateNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/realestate";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("عقارات");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    RealestateNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:RealestateNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return RealestateNews;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getArtsNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> ArtsNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/arts";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("أنغام وفنون");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    ArtsNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:ArtsNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return ArtsNews;

    }
    /////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getTechNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> TechNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/information-technology";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("تقنية المعلومات");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    TechNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:TechNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return TechNews;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getHealthNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> HealthNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/health";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("صحتك");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    HealthNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:HealthNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return HealthNews;

    }
    //////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getTravelNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> TravelNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/travel";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("السياحة");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    TravelNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:TravelNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return TravelNews;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getPoliticalNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> PoliticalNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/political";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("منوعات");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    PoliticalNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:PoliticalNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return PoliticalNews;
    }
    /////////////////////////////////////////////////
    public  ArrayList<News> getAsiaNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> AsiaNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/asia";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("آسيا");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    AsiaNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:AsiaNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return AsiaNews;
    }
    ///////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getAfricaNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> AfricaNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/africa";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("أفريقيا");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    AfricaNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:AfricaNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return AfricaNews;

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getEuropeNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> EuropeNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/europe";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("أوروبا");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    EuropeNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:EuropeNews){
                        ////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return EuropeNews;

    }
    ////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getAmericaNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> AmericaNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/america";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("الأميركيتين");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    AmericaNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:AmericaNews){
                        /////////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        ///////////////////////////
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        return AmericaNews;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public  ArrayList<News> getGulfNews() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final ArrayList<News> GulfNews=new ArrayList<>();
        String url = "https://aawsat.com/feed/gulf";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String url = response;
                XmlPullParser parser = Xml.newPullParser();
                try {
                    parser.setInput(new StringReader(url));
                    int eventType = parser.getEventType();
                    boolean done = false;
                    News item = null;
                    while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                        String name = null;
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item")) {
                                    item = new News();
                                } else if (item != null) {
                                    if (name.equalsIgnoreCase("title")) {
                                        item.setTitle(parser.nextText());
                                        item.setType("الخليج");
                                    } else if (name.equalsIgnoreCase("link")) {
                                        item.setLink(parser.nextText());
                                    } else if (name.equalsIgnoreCase("pubDate")) {
                                        item.setTime(parser.nextText());
                                    }else if (name.equalsIgnoreCase("description")) {
                                        item.setDescription(parser.nextText());
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                name = parser.getName();
                                if (name.equalsIgnoreCase("item") && item != null) {
                                    GulfNews.add(item);
                                } else if (name.equalsIgnoreCase("channel")) {
                                    done = true;
                                }
                                break;
                        }
                        eventType = parser.next();
                    }
                    firestore = FirebaseFirestore.getInstance();
                    final CollectionReference c = firestore.collection("News");
                    for(final News news:GulfNews){
                        //////////////////////////
                        StringRequest getimage=new StringRequest(Request.Method.GET, news.getLink(), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String html=response;
                                Document doc = Jsoup.parse(html);
                                if (doc.select("div").hasClass("new_photo")) {
                                    Elements div1 = doc.select("div");
                                    for (Element a : div1) {
                                        if (a.hasClass("node_new_photo")) {
                                            Element linkimg = a.selectFirst("img");
                                            if (linkimg.hasClass("img-responsive")) {
                                                news.setUrlimage(linkimg.attr("src"));
                                            }
                                        }
                                    }
                                }else if(doc.select("ul").hasClass("amazingslider-slides")){
                                    Element div = doc.getElementById("amazingslider-1");
                                    Element ul =div.selectFirst("ul");
                                    Element li=ul.selectFirst("li");
                                    Element img=li.selectFirst("img");
                                    news.setUrlimage(img.attr("src"));
                                }else{
                                    news.setUrlimage("Not Found Image !");
                                }

                                String title = news.getTitle();
                                c.document(title).set(news);
                                map.put(title,news);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        ///////////////////////////
                    }
                } catch (Exception e) {
                  //  throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
        isFinished=true;
        return GulfNews;
    }
}

