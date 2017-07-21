package com.codepath.rawr.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.rawr.R;
import com.codepath.rawr.models.TravelNotice;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.List;

/**
 * Created by rdicker on 7/21/17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {


    // declare variables
    private List<TravelNotice> mTrips;
    Context context;


    public SearchResultAdapter(List<TravelNotice> trips){
        mTrips = trips;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tripView = inflater.inflate(R.layout.item_search_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(tripView) {
        };
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TravelNotice trip = mTrips.get(position);


        holder.tvName.setText(trip.tuid + "'s Trip:");

        holder.tv_from_isr.setText(trip.dep_iata);
        holder.tv_to_isr.setText(trip.arr_iata);

        holder.tv_dateFrom_isr.setText(trip.dep_month + "/" + trip.dep_day + "/" + String.valueOf(trip.dep_year).substring(2));
        holder.tv_dateTo_isr.setText(trip.arr_month + "/" + trip.arr_day + "/" + String.valueOf(trip.arr_year).substring(2));

        holder.tv_fromTime_isr.setText(trip.dep_hour + ":" + trip.dep_min);
        holder.tv_toTime_isr.setText(trip.arr_hour + ":" + trip.arr_min);

        holder.cb_envelope_isr.setChecked(trip.item_envelopes);
        holder.cb_largeBox_isr.setChecked(trip.item_lgbox);
        holder.cb_smallBox_isr.setChecked(trip.item_smbox);
        holder.cb_clothing_isr.setChecked(trip.item_clothing);
        holder.cb_other_isr.setChecked(trip.item_other);

        holder.cb_envelope_isr.setEnabled(false);
        holder.cb_largeBox_isr.setEnabled(false);
        holder.cb_smallBox_isr.setEnabled(false);
        holder.cb_clothing_isr.setEnabled(false);
        holder.cb_other_isr.setEnabled(false);

        holder.tv_dropoff.setText(trip.drop_off_flexibility);
        holder.tv_pickup.setText(trip.pick_up_flexibility);

        holder.ivToggleInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // Toggle the expandable view
                holder.erl_info.toggle();

                // TODO - Change the drawable to either expanded or collapsed
                // TODO - Add filters in XML
            }
        });

    }



    @Override
    public int getItemCount() {
        return 0;
    }






    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tv_from_isr;
        public TextView tv_arrow_isr;
        public TextView tv_to_isr;
        public TextView tv_fromTime_isr;
        public TextView tv_toTime_isr;
        public TextView tv_dateFrom_isr;
        public TextView tv_dateTo_isr;

        final ImageView ivToggleInfo;
        final ExpandableRelativeLayout erl_info;

        public RelativeLayout rlCheckBoxes;
        public CheckBox cb_envelope_isr;
        public CheckBox cb_largeBox_isr;
        public CheckBox cb_smallBox_isr;
        public CheckBox cb_clothing_isr;
        public CheckBox cb_other_isr;

        public TextView tv_dropoff;
        public TextView tv_pickup;

        public Button btn_request_isr;
        public Button btn_askQ_isr;




        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName_isr);
            tv_arrow_isr = (TextView) itemView.findViewById(R.id.tv_arrow_isr);
            tv_from_isr = (TextView) itemView.findViewById(R.id.tv_from_isr);
            tv_to_isr = (TextView) itemView.findViewById(R.id.tv_to_isr);
            tv_fromTime_isr = (TextView) itemView.findViewById(R.id.tv_fromTime_isr);
            tv_toTime_isr = (TextView) itemView.findViewById(R.id.tv_toTime_isr);
            tv_dateFrom_isr = (TextView) itemView.findViewById(R.id.tv_dateFrom_isr);
            tv_dateTo_isr = (TextView) itemView.findViewById(R.id.tv_dateTo_isr);

            ivToggleInfo = (ImageView) itemView.findViewById(R.id.iv_toggleInfo);
            erl_info = (ExpandableRelativeLayout) itemView.findViewById(R.id.erl_info);

            rlCheckBoxes = (RelativeLayout) itemView.findViewById(R.id.rlCheckBoxes);
            cb_envelope_isr = (CheckBox) itemView.findViewById(R.id.cb_envelope_isr);
            cb_largeBox_isr = (CheckBox) itemView.findViewById(R.id.cb_largeBox_isr);
            cb_smallBox_isr = (CheckBox) itemView.findViewById(R.id.cb_smallBox_isr);
            cb_clothing_isr = (CheckBox) itemView.findViewById(R.id.cb_clothing_isr);
            cb_other_isr = (CheckBox) itemView.findViewById(R.id.cb_other_isr);

            tv_dropoff = (TextView) itemView.findViewById(R.id.tv_dropoff_isr);
            tv_pickup = (TextView) itemView.findViewById(R.id.tv_pickup_isr);

            btn_request_isr = (Button) itemView.findViewById(R.id.btn_request_isr);
            btn_askQ_isr = (Button) itemView.findViewById(R.id.btn_askQ_isr);

            btn_request_isr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    // Intent i = new Intent(context, RequestActivity.class);
                    // context.startActivity(i);
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you a")
                           /* .setTitle(R.string.dialog_title)  */   ;

                    builder.setPositiveButton("sender", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                    /*       Intent i = new Intent(context, SenderFormActivity.class);
                            i.putExtra("trip_id", mTrips.get(pos).id);
                            i.putExtra("sender_id", "596d0b5626bffc280b32187e");
                            context.startActivity(i);   */
                        }
                    });
                    builder.setNegativeButton("receiver", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                     /*       Intent i = new Intent(context, RecipientFormActivity.class);
                            i.putExtra("trip_id", mTrips.get(pos).id);
                            i.putExtra("recipient_id", "596d0b5626bffc280b32187e");
                            context.startActivity(i);   */
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });

            btn_askQ_isr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    // Intent i = new Intent(context, NewConversationActivity.class);
                    // context.startActivity(i);
                }
            });

        }
    }



    public void clear() {
        mTrips.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<TravelNotice> list) {
        mTrips.addAll(list);
        notifyDataSetChanged();
    }

}