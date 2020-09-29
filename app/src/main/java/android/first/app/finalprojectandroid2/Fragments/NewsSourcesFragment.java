package android.first.app.finalprojectandroid2.Fragments;


import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Adapter.ListChoiceAdapterEdit;
import android.first.app.finalprojectandroid2.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsSourcesFragment extends Fragment {


    public NewsSourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_sources, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences sp =  getContext().getSharedPreferences("Favorites",MODE_PRIVATE);
        if(sp.contains("Favorite")){
            Set<String> set1 =  sp.getStringSet("Favorite",null);
            ArrayList<String> listSet = new ArrayList<>(set1);
            for(String s:listSet){
                Log.d("ttt",s);

            }
        }

        ListView listChoiceFragment = getView().findViewById(R.id.ListChoiceFragment);
        Button nextFragment = getView().findViewById(R.id.NextFragment);
        ArrayList<String> Choices = new ArrayList<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("Choices.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                Choices.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListChoiceAdapterEdit l = new ListChoiceAdapterEdit(Choices, getContext());
        listChoiceFragment.setAdapter(l);

            nextFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                boolean h =   getContext().getSharedPreferences("ListChoice",MODE_PRIVATE).edit().clear().commit();
               //     Log.d("ttt",h+"");
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ListChoice", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> set = new HashSet(ListChoiceAdapterEdit.choice);
                    editor.putStringSet("Choices", set);
                    editor.commit();
            //        Intent i = new Intent(getContext(), HomeActivity.class);
             //       startActivity(i);
                }
            });



    }

    }




