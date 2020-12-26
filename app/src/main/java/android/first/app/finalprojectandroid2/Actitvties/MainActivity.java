package android.first.app.finalprojectandroid2.Actitvties;

import android.content.Intent;
import android.content.SharedPreferences;
import android.first.app.finalprojectandroid2.Adapter.ListChoiceAdapter;
import android.first.app.finalprojectandroid2.R;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private  ListView listView;
   private ArrayList<String> choices;
    private Button next;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.ListChoice);
        next = findViewById(R.id.Next);
        choices = new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("Choices.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                choices.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListChoiceAdapter l = new ListChoiceAdapter(choices, getApplicationContext());
        listView.setAdapter(l);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.header ,null);
        listView.addHeaderView(view);

        SharedPreferences sp = getSharedPreferences("ListChoice", MODE_PRIVATE);
        if(sp.contains("Choices")) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }else{

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("ListChoice",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Set<String> set = new HashSet(ListChoiceAdapter.choice);
                    editor.putStringSet("Choices", set);
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }
            });

        }

    }
}
