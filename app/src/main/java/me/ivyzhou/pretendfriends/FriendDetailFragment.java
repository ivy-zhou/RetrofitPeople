package me.ivyzhou.pretendfriends;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.ivyzhou.pretendfriends.models.User;
import me.ivyzhou.pretendfriends.models.UserContent;

/**
 * A fragment representing a single Friend detail screen.
 * This fragment is either contained in a {@link FriendListActivity}
 * in two-pane mode (on tablets) or a {@link FriendDetailActivity}
 * on handsets.
 */
public class FriendDetailFragment extends Fragment {

    @BindView(R.id.profile_image)
    ImageView profileImage;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_USER_ID = "item_user";

    /**
     * The dummy content this fragment is presenting.
     */
    private User mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FriendDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_USER_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = UserContent.getInstance().getUser(getArguments().getString(ARG_USER_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                ButterKnife.bind(this, appBarLayout);
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.friend_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.friend_detail)).setText(mItem.getEmail());

            if (profileImage != null)
                Picasso.with(rootView.getContext()).load(mItem.getThumbnailUrl())
                        .fit().centerCrop().into(profileImage);
        }

        return rootView;
    }
}
