package ca.mcgill.ecse321.rsms_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AppointmentAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] appointments;
    String[] plateNos;
    String[] times;
    String[] prices;
    String[] statuses;

    public AppointmentAdapter(Context c,  String[] appointments, String[] plateNos,
                              String[] times, String[] prices, String[] statuses){
        this.appointments = appointments;
        this.plateNos = plateNos;
        this.times = times;
        this.prices = prices;
        this.statuses = statuses;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return appointments.length;
    }

    @Override
    public Object getItem(int i) {
        return appointments[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.appointment_view_and_pay_detail, null);
        TextView appointmentTextView = (TextView) view.findViewById(R.id.appointmentTextView);
        TextView plateNoTextView = (TextView) view.findViewById(R.id.plateNoTextView);
        TextView timeTextView = (TextView) view.findViewById(R.id.timeTextView);
        TextView priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        TextView statusTextView = (TextView) view.findViewById(R.id.statusTextView);

        String appointment = appointments[i];
        String plateNo = plateNos[i];
        String time = times[i];
        String price = prices[i];
        String status = statuses[i];

        appointmentTextView.setText(appointment);
        plateNoTextView.setText(plateNo);
        timeTextView.setText(time);
        priceTextView.setText(price);
        statusTextView.setText(status);

        return view;
    }
}
