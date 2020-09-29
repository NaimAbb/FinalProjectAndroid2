package android.first.app.finalprojectandroid2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Actitvties.infoNews;
import android.first.app.finalprojectandroid2.Fragments.PreviousNewsFragment;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PreviouesNewsAdapter extends RecyclerView.Adapter<PreviouesNewsAdapter.MyViewHolder> {
    ArrayList<News> list;
    int idLayout;
    Context contxt;
    PreviousNewsFragment previousNewsFragment;
    public PreviouesNewsAdapter(ArrayList<News> list , int idLayout, Context contxt , PreviousNewsFragment previousNewsFragment){
        this.list = list;
        this.idLayout = idLayout;
        this.contxt=contxt;
        this.previousNewsFragment = previousNewsFragment;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(idLayout,viewGroup,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull  final MyViewHolder vh, int i) {
        final News n = list.get(i);
        String date =n.getTime();
        String newdate=date.substring(0,16);
        vh.nameTv.setText(n.getType());
        vh.timeTv.setText("بتاريخ : "+newdate);
        vh.textNewsTv.setText(n.getTitle());
        //**************************************
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
                Intent i = new Intent(previousNewsFragment.getContext(), infoNews.class);
                i.putExtra("News", n);
                previousNewsFragment.startActivity(i);
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
                        if(!LastNewsAdapter.Favorites.contains(n.getTitle())){
                            LastNewsAdapter.Favorites.add(n.getTitle());
                            contxt.getSharedPreferences("Favorites",MODE_PRIVATE).edit().clear();
                            SharedPreferences sharedPreferences = contxt.getSharedPreferences("Favorites",contxt.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Set<String> set = new HashSet(LastNewsAdapter.Favorites);
                            editor.putStringSet("Favorite", set);
                            editor.commit();
                            //*************************

                        }
                        SharedPreferences sharedPreferences = contxt.getSharedPreferences("Favorites",contxt.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Set<String> set = new HashSet(LastNewsAdapter.Favorites);
                        editor.putStringSet("Favorite", set);
                        editor.commit();
                    }else{
                        if(LastNewsAdapter.Favorites.contains(n.getTitle())){
                            LastNewsAdapter.Favorites.remove(n.getTitle());
                            SharedPreferences sharedPreferences = contxt.getSharedPreferences("Favorites",contxt.MODE_PRIVATE);
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
            SharedPreferences sp2 =  contxt.getSharedPreferences("Favorites",Context.MODE_PRIVATE);
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
}
