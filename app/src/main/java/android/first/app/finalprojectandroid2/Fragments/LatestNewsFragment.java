package android.first.app.finalprojectandroid2.Fragments;

import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Adapter.LastNewsAdapter;
import android.first.app.finalprojectandroid2.News;
import android.first.app.finalprojectandroid2.R;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class LatestNewsFragment extends Fragment {
        FirebaseFirestore firestore = null;
         RecyclerView rv ;
         public LatestNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();

        final SwipeRefreshLayout srl = getView().findViewById(R.id.swipeLayout);
        rv = getView().findViewById(R.id.recyclerView);
        final ArrayList<News> list = new ArrayList<>();
        final ArrayList<News> listNewsNew = new ArrayList<>();
        final LastNewsAdapter adapter = new LastNewsAdapter(listNewsNew, R.layout.last_news_item,getActivity().getApplicationContext(),this);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        CollectionReference reference = firestore.collection("News");
        Task<QuerySnapshot> task = reference.get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

               Iterator<QueryDocumentSnapshot> iter =  queryDocumentSnapshots.iterator();
               while (iter.hasNext()){
                  News s =  iter.next().toObject(News.class);
                   list.add(s);
               }
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                String currentDateandTime = sdf.format(new Date());
               String currentDate =  currentDateandTime.substring(0,16);


               SharedPreferences sp =  getActivity().getSharedPreferences("ListChoice", MODE_PRIVATE);
               Set<String> set =  sp.getStringSet("Choices",null);
              ArrayList<String> listSet = new ArrayList<>(set);
                for (News n:list) {
                    if (n != null) {
                        for(String s:listSet) {
                            if (s.equalsIgnoreCase(n.getType())){
                                String time = n.getTime();
                                String nowDate = time.substring(0, 16);
                                if (nowDate.equalsIgnoreCase(currentDate)) {

                                    listNewsNew.add(n);
                                }
                            }

                        }

                    }
                }
                if (listNewsNew.isEmpty()){
                    rv.setBackgroundResource(R.drawable.i);
                }
                adapter.notifyDataSetChanged();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });


        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            srl.setRefreshing(false);
                        CollectionReference reference = firestore.collection("News");
                        final ArrayList<News> list2 = new ArrayList<>();
                        final ArrayList<News> list3 = new ArrayList<>();
                        Task<QuerySnapshot> task = reference.get();
                        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Iterator<QueryDocumentSnapshot> iter =  queryDocumentSnapshots.iterator();
                                while (iter.hasNext()){
                                    News s =  iter.next().toObject(News.class);
                                    list2.add(s);
                                }
                               SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                                String currentDateandTime = sdf.format(new Date());
                                String currentDate =  currentDateandTime.substring(0,16);

                                SharedPreferences sp =  getActivity().getSharedPreferences("ListChoice", MODE_PRIVATE);
                                Set<String> set =  sp.getStringSet("Choices",null);
                                ArrayList<String> listSet = new ArrayList<>(set);
                                for (News n:list2) {
                                    if (n != null) {
                                        for(String s:listSet) {
                                            if (s.equalsIgnoreCase(n.getType())){
                                                String time = n.getTime();
                                                String nowDate = time.substring(0, 16);
                                                if (nowDate.equalsIgnoreCase(currentDate)) {
                                                    list3.add(n);
                                                }
                                            }

                                        }

                                    }
                                }

                                final LastNewsAdapter adapter = new LastNewsAdapter(list3, R.layout.last_news_item,getActivity().getApplicationContext(),LatestNewsFragment.this);
                                rv.setAdapter(adapter);
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                rv.setLayoutManager(llm);
                            }
                        });
                        task.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                },3000);
            }
        });

    }

}
