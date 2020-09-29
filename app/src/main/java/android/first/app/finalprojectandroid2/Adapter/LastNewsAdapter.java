package android.first.app.finalprojectandroid2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.first.app.finalprojectandroid2.Actitvties.HomeActivity;
import android.first.app.finalprojectandroid2.Actitvties.infoNews;
import android.first.app.finalprojectandroid2.Fragments.LatestNewsFragment;
import android.first.app.finalprojectandroid2.News;
import android.first.app.finalprojectandroid2.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class LastNewsAdapter extends RecyclerView.Adapter<LastNewsAdapter.MyViewHolder> {

    /** One second (in milliseconds) */
    private static final int _A_SECOND = 1000;
    /** One minute (in milliseconds) */
    private static final int _A_MINUTE = 60 * _A_SECOND;
    /** One hour (in milliseconds) */
    private static final int _AN_HOUR = 60 * _A_MINUTE;
    /** One day (in milliseconds) */
    private static final int _A_DAY = 24 * _AN_HOUR;

    ArrayList<News> list;
        int idLayout;
        Context contxt;
        public static ArrayList<String>Favorites=new ArrayList<>();
        LatestNewsFragment latestNewsFragment ;
    public LastNewsAdapter(ArrayList<News> list , int idLayout, Context contxt , LatestNewsFragment latestNewsFragment ){
        this.list = list;
        this.idLayout = idLayout;
        this.contxt=contxt;
        this.latestNewsFragment = latestNewsFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(idLayout,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder vh, final int i) {

        final News n = list.get(i);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Date date = sdf.parse(n.getTime());
            long Milliseconds= date.getTime();
            String result=getTimeAgo(Milliseconds,contxt.getApplicationContext());
            vh.timeTv.setText(result+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vh.nameTv.setText(n.getType());
        vh.textNewsTv.setText(n.getTitle());
        //*************************************************
        String start=n.getUrlimage();
        if(start!=null){
            Picasso.get().load(n.getUrlimage()).into(vh.imageNews);
        }else{
            Picasso.get().load(R.drawable.t1).into(vh.imageNews);
        }
        //**************************************
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(latestNewsFragment.getContext(), infoNews.class);
                i.putExtra("News", n);
                latestNewsFragment.startActivity(i);
            }
        });
        //**************************************
        SharedPreferences sp =  contxt.getSharedPreferences("Favorites",contxt.MODE_PRIVATE);
        if(sp.contains("Favorite")){
            Set<String> set1 =  sp.getStringSet("Favorite",null);
            ArrayList<String> listSet = new ArrayList<>(set1);
            for(String s:listSet){
                if(n.getTitle().equalsIgnoreCase(s)){
                    vh.shinestar.setChecked(true);
                    vh.shinestar.setSelected(true);

                }
            }
        }
        //**************************************
        String a=vh.textNewsTv.getText()+"";
        String b=n.getTitle()+"";
        if(a.equalsIgnoreCase(b)){
            vh.shinestar.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final View view, boolean checked) {
                    if(checked){
                        if(!LastNewsAdapter.Favorites.contains(n.getTitle())) {
                            LastNewsAdapter.Favorites.add(n.getTitle());
                            contxt.getSharedPreferences("Favorites",MODE_PRIVATE).edit().clear();
                            SharedPreferences sharedPreferences = contxt.getSharedPreferences("Favorites", contxt.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Set<String> set = new HashSet(LastNewsAdapter.Favorites);
                            editor.putStringSet("Favorite", set);
                            editor.commit();
                            for (String m: LastNewsAdapter.Favorites){
                                Log.d("ttt",m);
                            }
                            //*************************

                        }
                    }else{
                        if(LastNewsAdapter.Favorites.contains(n.getTitle())){
                            LastNewsAdapter.Favorites.remove(n.getTitle());
                            SharedPreferences sharedPreferences = contxt.getSharedPreferences("Favorites", contxt.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Set<String> set = new HashSet(LastNewsAdapter.Favorites);
                            editor.putStringSet("Favorite", set);
                            editor.commit();
                            //**********************************
                            SharedPreferences sp =  contxt.getSharedPreferences("Favorites",contxt.MODE_PRIVATE);
                            if(sp.contains("Favorite")){
                                Set<String> set2 =  sp.getStringSet("Favorite",null);
                                ArrayList<String> listSet = new ArrayList<>(set2);
                                for(String s:listSet){
                                    if(n.getTitle().equalsIgnoreCase(s)){
                                        vh.shinestar.setChecked(true);
                                    }
                                }
                            }
                        }
                    }
                }
            });
            //************************************
            SharedPreferences sp2 =  contxt.getSharedPreferences("Favorites",contxt.MODE_PRIVATE);
            if(sp2.contains("Favorite")){
                Set<String> set0 =  sp2.getStringSet("Favorite",null);
                ArrayList<String> listSet = new ArrayList<>(set0);
                for(String s:listSet){
                    if(n.getTitle().equalsIgnoreCase(s)){
                        vh.shinestar.setChecked(true);
                    }
                }
            }
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageNews;
        TextView nameTv;
        TextView timeTv;
        TextView textNewsTv;
        ShineButton shinestar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            textNewsTv = itemView.findViewById(R.id.textNewsTv);
            imageNews=itemView.findViewById(R.id.imageNews);
            shinestar=itemView.findViewById(R.id.shinestar);
        }
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
