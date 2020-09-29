package android.first.app.finalprojectandroid2.Adapter;

import android.content.Context;
import android.first.app.finalprojectandroid2.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class ListChoiceAdapterEdit extends BaseAdapter {
    ArrayList<String> Choices;
    Context context;
    CheckedTextView tv ;
    public static ArrayList<String> choice =new ArrayList<>();


    public ListChoiceAdapterEdit(ArrayList<String> Choices,Context context){
        this.Choices=Choices;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Choices.size();
    }

    @Override
    public Object getItem(int position) {
        return Choices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.checked_hoice_item,null);
        String get = Choices.get(position);
      final CheckedTextView  tv = view.findViewById(R.id.Choice);
      tv.setCheckMarkDrawable(null);
        tv.setText(get);

        Set<String> h = context.getSharedPreferences("ListChoice",MODE_PRIVATE).getStringSet("Choices",null);
        ArrayList<String> list = new ArrayList<>(h);


        for (int i = 0;i<list.size();i++){
            if (list != null){
                if (list.get(i).equals(Choices.get(position))){
                    tv.setCheckMarkDrawable(R.drawable.ic_check_circle_light_blue_300_36dp);
                    tv.setSelected(true);
                }
            }
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv.isChecked()){
                    tv.setCheckMarkDrawable(null);
                   tv.setChecked(false);
                    if(choice.contains(tv.getText().toString())){
                        choice.remove(tv.getText().toString());
                    }
                }else{
                    tv.setCheckMarkDrawable(R.drawable.ic_check_circle_light_blue_300_36dp);
                    tv.setChecked(true);
                    if(!choice.contains(tv.getText().toString())){
                        choice.add(tv.getText().toString());
                    }
                }
            }
        });

        for (int i =0;i<choice.size();i++){
           String tag =  tv.getText().toString();
           if (tag.equalsIgnoreCase(choice.get(i))){
               tv.setCheckMarkDrawable(R.drawable.ic_check_circle_light_blue_300_36dp);
           }
        }






        return view;
    }
    class ViewHolder{
        CheckedTextView Choice;
    }

}
