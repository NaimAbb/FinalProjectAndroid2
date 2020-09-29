package android.first.app.finalprojectandroid2.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Actitvties.HomeActivity;
import android.first.app.finalprojectandroid2.News;
import android.first.app.finalprojectandroid2.R;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Xml;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class NotificationService extends Service {
    FirebaseFirestore firestore = null;
    RequestQueue requestQueue = null;
    ArrayList<News> list = new ArrayList<>();
    int id = 0;
    boolean isRunning = true;


    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning) {
            Log.d("ttt", "runningNotifiaction");


            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for (int i = 0; i < 600 ;i++) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (i == 599) {
                                list.clear();

                               // ArrayList<News> AllNews = new ArrayList<>();

                                ArrayList<News> ArabWorldNews = getArabWorldNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(ArabWorldNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                ArrayList<News> SportNews = getSportNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(SportNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> ConomyNews = getConomyNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(ConomyNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> EducationNews = getEducationNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(EducationNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> PressNews = getPressNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(PressNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> ScienceNews = getScienceNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(ScienceNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> RealestateNews = getRealestateNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(RealestateNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> ArtsNews = getArtsNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(ArtsNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> TechNews = getTechNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(TechNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> HealthNews = getHealthNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(HealthNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> TravelNews = getTravelNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(TravelNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> PoliticalNews = getPoliticalNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(PoliticalNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> AsiaNews = getAsiaNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(AsiaNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> AfricaNews = getAfricaNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(AfricaNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> EuropeNews = getEuropeNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(EuropeNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> AmericaNews = getAmericaNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(AmericaNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                ArrayList<News> GulfNews = getGulfNews();
                                try {
                                    Thread.sleep(5000);
                                    list.addAll(GulfNews);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                                SharedPreferences sp = getSharedPreferences("ListChoice", Context.MODE_PRIVATE);
                                Set<String> set = sp.getStringSet("Choices", null);
                                final ArrayList<String> listSet = new ArrayList<>(set);

                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                CollectionReference reference = firestore.collection("News");
                                Task<QuerySnapshot> task = reference.get();
                                task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        ArrayList<News> list2 = new ArrayList<>();
                                        Iterator<QueryDocumentSnapshot> iter = queryDocumentSnapshots.iterator();
                                        while (iter.hasNext()){
                                            News n  = iter.next().toObject(News.class);
                                            list2.add(n);
                                        }
                                        for (News n2:list){
                                            boolean result = false;
                                            for (int j = 0;j<listSet.size();j++){
                                                if (listSet.get(j).equalsIgnoreCase(n2.getType())){
                                                    for (News n3:list2){
                                                        if (n2.getTitle().equalsIgnoreCase(n3.getTitle())){
                                                            result = true;
                                                            break;
                                                        }
                                                    }
                                                    if (!result){
                                                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"ch1");
                                                        builder.setContentTitle("أخبار جديدة");
                                                        builder.setContentText(n2.getTitle());
                                                        builder.setVibrate(new long[]{1000,1000,1000});
                                                        builder.setLights(Color.RED,3000,2000);
                                                        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                                                        builder.setPriority(Notification.PRIORITY_MAX);
                                                        builder.setDefaults(Notification.DEFAULT_ALL);
                                                        builder.setSmallIcon(R.drawable.icon);
                                                        Intent intent2 = new Intent(getApplicationContext(), HomeActivity.class);
                                                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(),0,intent2,0);
                                                        builder.setContentIntent(pIntent);
                                                        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
                                                        builder.setAutoCancel(true);
                                                        Notification n = builder.build();
                                                        NotificationManager manager  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                        manager.notify(id,n);
                                                        id++;
                                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                                        CollectionReference collectionReference = firebaseFirestore.collection("News");
                                                        collectionReference.document(n2.getTitle()).set(n2);
                                                    }
                                                }
                                            }
                                        }

                                    }

                                });






                            }
                        }
                    }
                }
            });
            t.start();
            return super.onStartCommand(intent, flags, startId);
        }else {
            return Service.START_NOT_STICKY;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
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
                                    list.add(item);
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
                             //   c.document(title).set(news);

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
                                    list.add(item);
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
                              //  c.document(title).set(news);



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
                                    list.add(item);
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
                             //   c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                   // list.addAll(ConomyNews);
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
                                    list.add(item);
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
                           //     c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                  //  list.addAll(EducationNews);
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
                                    list.add(item);
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
                               // c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                 //   list.addAll(PressNews);
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
                                    list.add(item);
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
                         //       c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                  //  list.addAll(ScienceNews);
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
                                    list.add(item);
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
                             //   c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                 //   list.addAll(RealestateNews);
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
                                    list.add(item);
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
                             //   c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                //    list.addAll(ArtsNews);
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
                                    list.add(item);
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
                         //       c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                  //  list.addAll(TechNews);
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
                                    list.add(item);
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
                             //   c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                   // list.addAll(HealthNews);
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
                                    list.add(item);
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
                             //   c.document(title).set(news);


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                   // list.addAll(TravelNews);
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
                                    list.add(item);
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
                             //   c.document(title).set(news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                //    list.addAll(PoliticalNews);
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
                                    list.add(item);
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
//                                c.document(title).set(news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                  //  list.addAll(AsiaNews);
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
                                    list.add(item);
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
//                                c.document(title).set(news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                  //  list.addAll(AfricaNews);
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
                                    list.add(item);
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
                             //   c.document(title).set(news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        /////////////////////////////
                    }
                 //   list.addAll(EuropeNews);
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
                                    list.add(item);
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
                              //  c.document(title).set(news);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        ///////////////////////////
                    }
              //      list.addAll(AmericaNews);
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
                                    list.add(item);
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
                           //     c.document(title).set(news);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(getimage);
                        ///////////////////////////
                    }
                  //  list.addAll(GulfNews);
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
        return GulfNews;
    }
}
