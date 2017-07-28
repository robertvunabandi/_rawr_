package com.codepath.rawr.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.rawr.R;
import com.codepath.rawr.RawrApp;
import com.codepath.rawr.UpdateAdditionalDetailsActivity;
import com.codepath.rawr.models.TravelNotice;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.codepath.rawr.fragments.TravelFragment.DB_URLS;

public class UpcomingTripAdapter extends RecyclerView.Adapter<UpcomingTripAdapter.ViewHolder> {

    // declare variables
    static private List<TravelNotice> mTrips;
    Context context;

    public UpcomingTripAdapter(List<TravelNotice> trips) {
        mTrips = trips;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tripsView = inflater.inflate(R.layout.item_upcoming_trip, parent, false);
        ViewHolder viewHolder = new ViewHolder(tripsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final UpcomingTripAdapter.ViewHolder holder, final int position) {
        TravelNotice trips = mTrips.get(position);
        holder.tv_from.setText(trips.dep_iata);
        holder.tv_to.setText(trips.arr_iata);
        holder.tv_dateFrom.setText(trips.getDepartureDaySimple());
        holder.tv_dateTo.setText(trips.getArrivalDaySimple());
        holder.tv_fromTime.setText(trips.getDepartureTime());
        holder.tv_toTime.setText(trips.getArrivalTime());
        holder.tv_airlineCode.setText(trips.airline);
        holder.tv_airlineNo.setText(trips.flight_num);

        holder.cb_envelope.setChecked(trips.item_envelopes);
        holder.cb_largeBox.setChecked(trips.item_lgbox);
        holder.cb_smallBox.setChecked(trips.item_smbox);
        holder.cb_clothing.setChecked(trips.item_clothing);
        holder.cb_other.setChecked(trips.item_other);
        holder.cb_fragile.setChecked(trips.item_fragile);
        holder.cb_liquids.setChecked(trips.item_liquid);
        holder.cb_envelope.setEnabled(false);
        holder.cb_largeBox.setEnabled(false);
        holder.cb_smallBox.setEnabled(false);
        holder.cb_clothing.setEnabled(false);
        holder.cb_other.setEnabled(false);
        holder.cb_fragile.setEnabled(false);
        holder.cb_liquids.setEnabled(false);
        holder.tv_dropoff.setText(trips.drop_off_flexibility);
        holder.tv_pickup.setText(trips.pick_up_flexibility);


        // toggle button
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



        // edit button
        holder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {



                Intent i = new Intent(context, UpdateAdditionalDetailsActivity.class);
                i.putExtra("travel_notice_id", mTrips.get(position).id);
                i.putExtra("tuid", mTrips.get(position).tuid);

                ((Activity) context).startActivityForResult(i, RawrApp.ADDITIONAL_DETAILS_CODE);

                // TODO - GET THE NEW TRIP FROM THE RESPONSE AND REPLACE IT HERE TO FIX THE REFRESH PROBLEM THING


                notifyDataSetChanged();

            }
        });


        // delete button
        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                params.put("travel_notice_id", mTrips.get(position).id);
                params.put("tuid", mTrips.get(position).tuid);

                client.post(DB_URLS[0] + "/travel_notice_delete", params, new JsonHttpResponseHandler() {
                    // implement endpoint here
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        mTrips.remove(position);
                        Toast.makeText(context, String.format("%s", "Travel Notice deleted!"), Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();


                        // TODO - maybe notify the shipper that the traveller cancelled the trip
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(context, String.format("error 1 %s", errorResponse), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Toast.makeText(context, String.format("error 2 %s", errorResponse), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(context, String.format("error 3"), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        // requester details button
        holder.bt_rquester_details.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // Toggle the expandable view
                // Intent i = new Intent(this, RequestDetailsActivity.class);

                // TODO - create a RequestDetailsActivity java class and send the appropriate intent extras to be able to view the requesters for each future trip and be able to message them
                // TODO - ^^^ this ^^^ could maybe just be a dialog box with a list of travelers and what they're bringing and a button to take you to a message conversation with them
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout relative_layout;
        public TextView tv_from;
        public TextView tv_to;
        public TextView tv_arrow;
        public TextView tv_fromTime;
        public TextView tv_toTime;
        public TextView tv_dateFrom;
        public TextView tv_dateTo;
        public Button bt_pending;
        public Button bt_accepted;
        public TextView tv_airlineCode;
        public TextView tv_airlineNo;
        public Button bt_edit;
        public Button bt_rquester_details;
        public Button bt_delete;

        final ImageView ivToggleInfo;
        final ExpandableRelativeLayout erl_info;

        public RelativeLayout rlCheckBoxes;
        public CheckBox cb_envelope;
        public CheckBox cb_largeBox;
        public CheckBox cb_smallBox;
        public CheckBox cb_clothing;
        public CheckBox cb_other;
        public CheckBox cb_fragile;
        public CheckBox cb_liquids;
        public TextView tv_dropoff;
        public TextView tv_pickup;

        public ViewHolder(View itemView) {
            super(itemView);

            relative_layout = (RelativeLayout) itemView.findViewById(R.id.relative_layout);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_to = (TextView) itemView.findViewById(R.id.tv_to);
            tv_arrow = (TextView) itemView.findViewById(R.id.tv_arrow);
            tv_fromTime = (TextView) itemView.findViewById(R.id.tv_fromTime);
            tv_toTime = (TextView) itemView.findViewById(R.id.tv_toTime);
            tv_dateFrom = (TextView) itemView.findViewById(R.id.tv_dateFrom);
            tv_dateTo = (TextView) itemView.findViewById(R.id.tv_dateTo);

            bt_pending = (Button) itemView.findViewById(R.id.bt_pending);
            bt_accepted = (Button) itemView.findViewById(R.id.bt_accepted);


            tv_airlineCode = (TextView) itemView.findViewById(R.id.tv_airlineCode);
            tv_airlineNo = (TextView) itemView.findViewById(R.id.tv_airlineNo);
            bt_edit = (Button) itemView.findViewById(R.id.bt_edit);
            bt_delete = (Button) itemView.findViewById(R.id.bt_delete);
            bt_rquester_details = (Button) itemView.findViewById(R.id.bt_rquester_details);

            ivToggleInfo = (ImageView) itemView.findViewById(R.id.iv_toggleInfo);
            erl_info = (ExpandableRelativeLayout) itemView.findViewById(R.id.erl_info);

            rlCheckBoxes = (RelativeLayout) itemView.findViewById(R.id.rlCheckBoxes);
            cb_envelope = (CheckBox) itemView.findViewById(R.id.cb_envelope);
            cb_largeBox = (CheckBox) itemView.findViewById(R.id.cb_largeBox);
            cb_smallBox = (CheckBox) itemView.findViewById(R.id.cb_smallBox);
            cb_clothing = (CheckBox) itemView.findViewById(R.id.cb_clothing);
            cb_other = (CheckBox) itemView.findViewById(R.id.cb_other);
            cb_fragile = (CheckBox) itemView.findViewById(R.id.cb_fragile);
            cb_liquids = (CheckBox) itemView.findViewById(R.id.cb_liquids);

            tv_dropoff = (TextView) itemView.findViewById(R.id.tv_dropoff);
            tv_pickup = (TextView) itemView.findViewById(R.id.tv_pickup);
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
