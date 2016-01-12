package hevs.labo.projetandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Darlène on 25.11.2015.
 */
public class MobileArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public MobileArrayAdapter(Context context, String[] values) {

        super(context, R.layout.activity_list_room, values);
        this.context = context;
        this.values = values;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        View rowView = inflater.inflate(R.layout.activity_list_room, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.label);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        String[] parts = s.split(" ");
        String part2 = parts[2];

       // System.out.println(s);

        if (part2.equals("OCCUP")) {
            imageView.setImageResource(R.drawable.selecticon);
        } else if (s.equals("NOCCUP")) {
            imageView.setImageResource(R.drawable.crossicon);
        }

        return rowView;
    }
}
