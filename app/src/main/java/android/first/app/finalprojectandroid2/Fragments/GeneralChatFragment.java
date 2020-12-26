package android.first.app.finalprojectandroid2.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Adapter.FavoritesAdapter;
import android.first.app.finalprojectandroid2.News;
import android.first.app.finalprojectandroid2.R;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class GeneralChatFragment extends Fragment {
   private FirebaseFirestore firestore = null;
    private RecyclerView favorites;
    FavoritesAdapter adapter;
     ArrayList<News> listNewsNew;
    public GeneralChatFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general_chat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        listNewsNew= new ArrayList<>();
        favorites=getActivity().findViewById(R.id.favorites);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        favorites.setLayoutManager(llm);

        SharedPreferences sp =  getActivity().getSharedPreferences("Favorites",Context.MODE_PRIVATE);
        if(sp.contains("Favorite")){
            Set<String> set =  sp.getStringSet("Favorite",null);
            final ArrayList<String> listSet = new ArrayList<>(set);
            for(String s :listSet){
                Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
            //************************************************************
            CollectionReference reference = firestore.collection("News");
            Task<QuerySnapshot> task = reference.get();
            task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Iterator<QueryDocumentSnapshot> iter = queryDocumentSnapshots.iterator();
                    while (iter.hasNext()) {
                        News s = iter.next().toObject(News.class);
                        for(String x :listSet){
                            if(s.getTitle().equalsIgnoreCase(x)){
                                listNewsNew.add(s);
                            }
                        }
                    }
                    //****************************
                    adapter = new FavoritesAdapter(listNewsNew, R.layout.last_news_item,getActivity().getApplicationContext(),GeneralChatFragment.this);
                    favorites.setAdapter(adapter);
                }
            });
        }else{
            favorites.setBackgroundResource(R.drawable.f);
        }
    }
}

