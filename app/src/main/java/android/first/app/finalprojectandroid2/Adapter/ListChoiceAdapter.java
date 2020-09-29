package android.first.app.finalprojectandroid2.Adapter;

import android.content.Context;
import android.first.app.finalprojectandroid2.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;

public class ListChoiceAdapter extends BaseAdapter {
    ArrayList<String> Choices;
    Context context;
   public static ArrayList<String> choice = new ArrayList<>();

    public ListChoiceAdapter(ArrayList<String> Choices,Context context){
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
        final CheckedTextView tv = view.findViewById(R.id.Choice);
        String get = Choices.get(position);
        tv.setText(get);

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

        /*
        if(convertView == null){
            convertView= LayoutInflater.from(context).inflate(R.layout.checked_hoice_item,null);
            CheckedTextView Choice=convertView.findViewById(R.id.Choice);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.Choice=Choice;
            convertView.setTag(viewHolder);
        }
        final ViewHolder viewHolder=(ViewHolder)convertView.getTag();
        viewHolder.Choice.setText(Choices.get(position));
        viewHolder.Choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.Choice.isChecked()){
                    viewHolder.Choice.setCheckMarkDrawable(null);
                    viewHolder.Choice.setChecked(false);
                    if(choice.contains(viewHolder.Choice.getText().toString())){
                        choice.remove(viewHolder.Choice.getText().toString());
                    }
                }else{
                    viewHolder.Choice.setCheckMarkDrawable(R.drawable.ic_check_circle_green_500_36dp);
                    viewHolder.Choice.setChecked(true);
                    if(!choice.contains(viewHolder.Choice.getText().toString())){
                        choice.add(viewHolder.Choice.getText().toString());
                    }
                }

            }
        });

        for (int i =0;i<choice.size();i++){
            String tag =  viewHolder.Choice.getText().toString();
            if (tag.equalsIgnoreCase(choice.get(i))){
                viewHolder.Choice.setCheckMarkDrawable(R.drawable.ic_check_circle_green_500_36dp);
            }
        }*/
        return view;
    }
    class ViewHolder{
        CheckedTextView Choice;
    }
}
