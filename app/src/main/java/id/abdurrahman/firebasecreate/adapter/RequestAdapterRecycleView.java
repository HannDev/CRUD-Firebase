package id.abdurrahman.firebasecreate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.abdurrahman.firebasecreate.MainActivity;
import id.abdurrahman.firebasecreate.R;
import id.abdurrahman.firebasecreate.model.Requests;

public class RequestAdapterRecycleView extends RecyclerView.Adapter<RequestAdapterRecycleView.MyViewHolder> {

    private List<Requests> requestslist;
    private Activity activityList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public TextView textTitle, textEmail, textStatus;

        public MyViewHolder(View view) {
            super(view);

            linearLayout = view.findViewById(R.id.linear_layout);
            textTitle = view.findViewById(R.id.text_title);
            textEmail = view.findViewById(R.id.text_email);
            textStatus = view.findViewById(R.id.text_status);

        }
    }

    public RequestAdapterRecycleView(List<Requests> List, Activity activity) {
        this.requestslist = List;
        this.activityList = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Requests requests = requestslist.get(position);

        holder.textTitle.setText(requests.getUsername());
        holder.textEmail.setText(requests.getEmail());
        holder.textStatus.setText(requests.getStatus());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activityList, MainActivity.class);
                intent.putExtra("id", requests.getKey());
                intent.putExtra("username", requests.getUsername());
                intent.putExtra("email", requests.getEmail());
                intent.putExtra("status", requests.getStatus());

                activityList.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestslist.size();
    }
}
