package com.heed.theuserentity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.heed.theuserentity.R;
import com.heed.theuserentity.activities.MainActivity;
import com.heed.theuserentity.models.User;
import com.heed.theuserentity.utils.ApiServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author howard
 */
public class MainFragment extends BaseFragment {

    private final static String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.user_profile_image)
    ImageView mProfileImageView;
    @BindView(R.id.user_name)
    TextView mProfileNameTextView;
    @BindView(R.id.phone_number)
    TextView mProfilePhoneNumberTextView;
    @BindView(R.id.email_address)
    TextView mProfileEmailAddressTextView;

    private User user;
    private MaterialDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = ((MainActivity) getActivity()).showIndeterminateProgressDialog("Loading ...", true).build();
    }

    @OnClick(R.id.delete_button)
    public void deleteUser() {
        deleteUserProfile();
    }

    public void getUserProfileFromRefreshClick(MenuItem refreshItem) {
        getUserProfile(refreshItem);
    }

    private void getUserProfile(MenuItem refreshItem) {
        toggleLoadingDialog(true);

        ApiServiceGenerator.getUserInfo(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();

                    if (user != null) {
                        basicLoadWithGlideCircular(user.getProfilePicture(), mProfileImageView, R.mipmap.ic_user_icon);
                        mProfileNameTextView.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));
                        mProfilePhoneNumberTextView.setText(user.getPhoneNumber());
                        mProfileEmailAddressTextView.setText(user.getEmail());
                    }
                }
                toggleLoadingDialog(false);

                if (refreshItem != null) {
                    refreshItem.getActionView().clearAnimation();
                    refreshItem.setActionView(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                toggleLoadingDialog(false);
                showBasicToast(getString(R.string.string_get_user_info_fail));

                if (refreshItem != null) {
                    refreshItem.getActionView().clearAnimation();
                    refreshItem.setActionView(null);
                }
            }
        });
    }

    private void deleteUserProfile() {
        toggleLoadingDialog(true);

        if (user != null && user.getId() != null) {
            ApiServiceGenerator.deleteUser(user.getId(), new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    switch (response.code()) {
                        case 200:
                            showBasicToast(getString(R.string.string_delete_user_success));
                            break;
                        default:
                            showBasicToast(getString(R.string.string_delete_user_fail));
                            break;
                    }
                    toggleLoadingDialog(false);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.e(TAG, t.getMessage(), t);
                    toggleLoadingDialog(false);
                    showBasicToast(getString(R.string.string_delete_user_fail));
                }
            });
        } else {
            toggleLoadingDialog(false);
            showBasicToast(getString(R.string.string_delete_user_fail));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserProfile(null);
    }

    private void toggleLoadingDialog(boolean show) {
        if (show) dialog.show();
        else dialog.dismiss();
    }
}
