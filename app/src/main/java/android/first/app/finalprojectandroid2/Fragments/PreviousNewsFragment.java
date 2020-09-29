package android.first.app.finalprojectandroid2.Fragments;



import android.content.Context;
import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Adapter.PreviouesNewsAdapter;
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
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


public class PreviousNewsFragment extends Fragment {
    FirebaseFirestore firestore = null;
    RecyclerView rv;

    public PreviousNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_previous_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        final SwipeRefreshLayout srl = getView().findViewById(R.id.swipeLayout2);
        rv = getView().findViewById(R.id.recyclerView2);
        final ArrayList<News> list = new ArrayList<>();
        final ArrayList<News> listNewsNew = new ArrayList<>();
        final PreviouesNewsAdapter adapter = new PreviouesNewsAdapter(listNewsNew, R.layout.last_news_item,getActivity().getApplicationContext(),this);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        CollectionReference reference = firestore.collection("News");
        Task<QuerySnapshot> task = reference.get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d("ttt","ddd");
                Iterator<QueryDocumentSnapshot> iter =  queryDocumentSnapshots.iterator();
                while (iter.hasNext()){
                    News s =  iter.next().toObject(News.class);
                    list.add(s);
                }


                SharedPreferences sp =  getActivity().getSharedPreferences("ListChoice",Context.MODE_PRIVATE);
                Set<String> set =  sp.getStringSet("Choices",null);
                ArrayList<String> listSet = new ArrayList<>(set);
                for (News n:list) {
                    if (n != null) {
                        for(String s:listSet) {
                            if (s.equalsIgnoreCase(n.getType())){
                                listNewsNew.add(n);
                            }

                        }

                    }
                }
                if (listNewsNew.isEmpty()){
                    for (News n:list) {
                        if (n != null) {
                            for(String s:listSet) {
                                if (s.equalsIgnoreCase(n.getType())){
                                    listNewsNew.add(n);
                                }
                            }

                        }
                    }
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
                                SharedPreferences sp =  getActivity().getSharedPreferences("ListChoice",Context.MODE_PRIVATE);
                                Set<String> set =  sp.getStringSet("Choices",null);
                                ArrayList<String> listSet = new ArrayList<>(set);
                                for (News n:list2) {
                                    if (n != null) {
                                        for(String s:listSet) {
                                            if (s.equalsIgnoreCase(n.getType())){
                                                list3.add(n);
                                            }

                                        }

                                    }
                                }

                                final PreviouesNewsAdapter adapter = new PreviouesNewsAdapter(list3, R.layout.last_news_item,getActivity().getApplicationContext(),PreviousNewsFragment.this);
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
