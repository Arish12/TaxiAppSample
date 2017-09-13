package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import arish.aaataxi.arisetech.com.aaataxi.R;

public class HistoryArray extends ArrayAdapter<String> {
    private String[] ids;
    private String[] emails;
    private String[] pickupaddress;
    private String[] dropofaddress;
    private String[] noofpassenger;
    private String[] booktime;
    private String[] reservedate;
    private Activity context;


    public HistoryArray(Activity context, String[] idss, String[] emailss, String[] pickupaddresss, String[] dropofaddresss, String[] noofpassengers, String[] booktimes, String[] reservedates) {
        super(context, R.layout.activity_history_array, idss);
        this.context = context;
        this.emails = emailss;
        this.pickupaddress = pickupaddresss;
        this.dropofaddress = dropofaddresss;
        this.noofpassenger = noofpassengers;
        this.booktime = booktimes;
        this.reservedate = reservedates;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_history_array, null, true);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textEmail1);
        TextView textViewPickUpAdd = (TextView) listViewItem.findViewById(R.id.textPickup2);
        TextView textViewDropOf = (TextView) listViewItem.findViewById(R.id.dropof6);
        TextView textViewPassen = (TextView) listViewItem.findViewById(R.id.noofpasse3);
        TextView textViewBooktime = (TextView) listViewItem.findViewById(R.id.textTime4);
        TextView textViewReserveDate = (TextView) listViewItem.findViewById(R.id.textReserveDate5);

        textViewEmail.setText(emails[position]);
        textViewPickUpAdd.setText(pickupaddress[position]);
        textViewDropOf.setText(dropofaddress[position]);
        textViewPassen.setText(noofpassenger[position]);
        textViewBooktime.setText(booktime[position]);
        textViewReserveDate.setText(reservedate[position]);

        return listViewItem;
    }
}