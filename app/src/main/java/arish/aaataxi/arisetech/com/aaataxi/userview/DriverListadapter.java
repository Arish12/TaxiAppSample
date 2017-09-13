package arish.aaataxi.arisetech.com.aaataxi.userview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import arish.aaataxi.arisetech.com.aaataxi.R;

/**
 * Created by Arish on 7/12/2016.
 */
public class DriverListadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Context context;
    private LayoutInflater inflater;
    List<Driverlist> Driverlistdata= Collections.emptyList();

    // create constructor to innitilize context and data sent from MainActivity
    public DriverListadapter(Context context, List<Driverlist> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.Driverlistdata=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.driver_client_adapter, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Driverlist current=Driverlistdata.get(position);
        myHolder.address.setText("address: " + current.address);
        myHolder.email.setText("email: " + current.email);
        myHolder.phone.setText("phone_no " + current.phone);
        myHolder.taxino.setText("taxi_no " + current.taxino);

        // load image into imageview using glide
//        Glide.with(context).load("http://www.aaataxinj.com/admin/" + current.fishImage)
//                .placeholder(R.drawable.class)
//                .error(R.drawable.class)
//                .into(myHolder.ivFish);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return Driverlistdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView address;
        TextView email;
        TextView phone;
        TextView taxino;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            address= (TextView) itemView.findViewById(R.id.client_driver_Address);
            email = (TextView) itemView.findViewById(R.id.client_driver_Email);
            phone = (TextView) itemView.findViewById(R.id.client_driver_Phone);
            taxino = (TextView) itemView.findViewById(R.id.driver_taxi_No);

        }

    }
}
