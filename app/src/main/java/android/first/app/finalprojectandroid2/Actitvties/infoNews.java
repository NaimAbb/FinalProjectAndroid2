package android.first.app.finalprojectandroid2.Actitvties;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.first.app.finalprojectandroid2.News;
import android.first.app.finalprojectandroid2.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class infoNews extends AppCompatActivity {
    TextView typeInfo;
    TextView descriptionInfo;
    TextView titleInfo;
    TextView timeInfo;
    TextView dateinfo;
    Button linkInfo;
    ImageView imageNewsInfo;

    /** One second (in milliseconds) */
    private static final int _A_SECOND = 1000;
    /** One minute (in milliseconds) */
    private static final int _A_MINUTE = 60 * _A_SECOND;
    /** One hour (in milliseconds) */
    private static final int _AN_HOUR = 60 * _A_MINUTE;
    /** One day (in milliseconds) */
    private static final int _A_DAY = 24 * _AN_HOUR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_news);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        typeInfo=findViewById(R.id.typeInfo);
        dateinfo=findViewById(R.id.dateinfo);
        descriptionInfo=findViewById(R.id.descriptionInfo);
        titleInfo=findViewById(R.id.titleInfo);
        timeInfo=findViewById(R.id.timeInfo);
        linkInfo=findViewById(R.id.linkInfo);
        imageNewsInfo=findViewById(R.id.imageNewsInfo);
        final News n =(News)getIntent().getSerializableExtra("News");
        //******************************
        String start=n.getUrlimage();
        if(start!=null){
            Picasso.get().load(n.getUrlimage()).into(imageNewsInfo);
        }else{
            Picasso.get().load(R.drawable.t1).into(imageNewsInfo);
        }
        //*******************************
        titleInfo.setText(n.getTitle()+"");
        descriptionInfo.setText(n.getDescription()+"");
        typeInfo.setText(n.getType()+"");
        //**************************************
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = sdf.parse(n.getTime());
            long Milliseconds= date.getTime();
            String result=getTimeAgo(Milliseconds,getApplicationContext());
            timeInfo.setText(result+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //******************************************
        titleInfo.setText(n.getTitle()+"");
        linkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                Uri uri= Uri.parse(n.getLink());
                i.setData(uri);
                startActivity(i);
            }
        });
        //******************************************
        String date =n.getTime();
        String newdate=date.substring(0,16);
        dateinfo.setText(newdate+"");



    }



    public  String getTimeAgo(long time, Context context) {

        final long now =new Date().getTime();
        if (time > now || time <= 0) return "";


        final Resources res = context.getResources();
        final long time_difference = now - time;
        if (time_difference < _A_MINUTE)
            return res.getString(R.string.just_now);
        else if (time_difference < 50 * _A_MINUTE)
            return res.getString(R.string.time_ago,
                    res.getQuantityString(R.plurals.minutes, (int) time_difference / _A_MINUTE, time_difference / _A_MINUTE));
        else if (time_difference < 24 * _AN_HOUR)
            return res.getString(R.string.time_ago,
                    res.getQuantityString(R.plurals.hours, (int) time_difference / _AN_HOUR, time_difference / _AN_HOUR));
        else if (time_difference < 48 * _AN_HOUR)
            return res.getString(R.string.yesterday);
        else
            return res.getString(R.string.time_ago,
                    res.getQuantityString(R.plurals.days, (int) time_difference / _A_DAY, time_difference / _A_DAY));
    }
}
