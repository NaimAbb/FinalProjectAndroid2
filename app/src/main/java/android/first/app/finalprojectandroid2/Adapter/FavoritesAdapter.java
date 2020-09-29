package android.first.app.finalprojectandroid2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.first.app.finalprojectandroid2.Actitvties.infoNews;
import android.first.app.finalprojectandroid2.Fragments.GeneralChatFragment;
import android.first.app.finalprojectandroid2.News;
import android.first.app.finalprojectandroid2.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder>{

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
    GeneralChatFragment generalChatFragment;
    public FavoritesAdapter(ArrayList<News> list , int idLayout, Context contxt , GeneralChatFragment generalChatFragment){
        this.list = list;
        this.idLayout = idLayout;
        this.contxt=contxt;
        this.generalChatFragment = generalChatFragment;
    }

    @NonNull
    @Override
    public FavoritesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(idLayout,viewGroup,false);
        FavoritesAdapter.MyViewHolder vh = new FavoritesAdapter.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoritesAdapter.MyViewHolder vh, final int i) {
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
                Intent i = new Intent(generalChatFragment.getContext(), infoNews.class);
                i.putExtra("News", n);
                generalChatFragment.startActivity(i);
            }
        });
        //**************************************
        vh.shinestar.setChecked(true);
        vh.shinestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(i);
                notifyDataSetChanged();
            }
        });
        vh.shinestar.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                    LastNewsAdapter.Favorites.remove(n.getTitle());
                    SharedPreferences sp = contxt.getSharedPreferences("Favorites", contxt.MODE_PRIVATE);
                    Set<String> set = sp.getStringSet("Favorite",null);
                    if (set != null){
                        ArrayList<String> list = new ArrayList<>(set);
                        list.remove(n.getTitle());
                        SharedPreferences sp2 = contxt.getSharedPreferences("Favorites",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp2.edit();
                        Set<String> set2 = new HashSet<>(LastNewsAdapter.Favorites);
                        editor.putStringSet("Favorite",set2);
                        editor.commit();

                    }

                }

        });
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
