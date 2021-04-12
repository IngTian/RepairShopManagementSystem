package ca.mcgill.ecse321.rsms_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShiftAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] shift_id;
    String[] shift_time;
    String[] shift_date;

    public ShiftAdapter(Context c, String[] shiftId,
                              String[] time, String[] date){
        this.shift_id = shiftId;
        this.shift_time = time;
        this.shift_date = date;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shift_id.length;
    }

    @Override
    public Object getItem(int i) {
        return shift_id[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.single_shift, null);
        TextView shiftIdTextView = (TextView) view.findViewById(R.id.shiftid);
        TextView timeTextView = (TextView) view.findViewById(R.id.time);
        TextView dateTextView = (TextView) view.findViewById(R.id.date);

        String shiftId = shift_id[i];
        String time = shift_time[i];
        String date = shift_date[i];

        shiftIdTextView.setText(shiftId);
        timeTextView.setText(time);
        dateTextView.setText(date);

        return view;
    }
}
