package edu.upc.dsa.dsa_error404_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private final List<Group> groupList;
    private final GroupListActivity context;


    public GroupAdapter(GroupListActivity context, List<Group> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Group group = groupList.get(position);


        holder.tvGroupName.setText(group.getName());


        holder.btnJoinGroup.setOnClickListener(v -> {

            context.joinGroup(group);
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroupName;
        Button btnJoinGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGroupName = itemView.findViewById(R.id.tvGroupName);
            btnJoinGroup = itemView.findViewById(R.id.btnJoinGroup);
        }
    }
}
