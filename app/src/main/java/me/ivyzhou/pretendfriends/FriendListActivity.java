package me.ivyzhou.pretendfriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ivyzhou.pretendfriends.models.UserContent;
import me.ivyzhou.pretendfriends.networking.FriendsRetrofitClient;
import me.ivyzhou.pretendfriends.models.User;
import me.ivyzhou.pretendfriends.models.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Friends. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FriendDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FriendListActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    ProgressBar spinner;

    @BindView(R.id.friend_list)
    RecyclerView recyclerView;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.friend_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Default to empty recyclerview
        recyclerView.setAdapter(new UserAdapter(new ArrayList<User>()));

        FriendsRetrofitClient.getInstance().loadUserData(25, new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                spinner.setVisibility(View.GONE);
                UserList userList = response.body();
                if (response.isSuccessful() && userList != null) {
                    UserContent.getInstance().setUsers(userList);
                    recyclerView.setAdapter(new UserAdapter(userList.getUsers()));
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                spinner.setVisibility(View.GONE);
            }
        });
    }

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

        private final List<User> mValues;

        public UserAdapter(List<User> users) {
            mValues = users;
        }

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friend_list_content, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final UserViewHolder holder, int position) {
            final String id = Integer.toString(position);
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(id);
            holder.mContentView.setText(holder.mItem.getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(FriendDetailFragment.ARG_USER_ID, holder.mItem.getId());
                        FriendDetailFragment fragment = new FriendDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.friend_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, FriendDetailActivity.class);
                        intent.putExtra(FriendDetailFragment.ARG_USER_ID, holder.mItem.getId());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class UserViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public User mItem;

            public UserViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
