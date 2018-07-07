package com.heed.theuserentity.fragments;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.heed.theuserentity.utils.GlideApp;

public class BaseFragment extends Fragment {

    /**
     * Basic Glide helper method to inflate an ImageView
     *
     * @param src           URI to load image from
     * @param viewToInflate ImageView where image will be shown
     */
    public void basicLoadWithGlide(String src, View viewToInflate) {
        GlideApp.with(this).load(src).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) viewToInflate);
    }

    /**
     * Basic Glide helper method to inflate an ImageView with a circular transform
     *
     * @param src              URI to load image from
     * @param viewToInflate    ImageView where image will be shown
     * @param placeHolderImage identifier of a placeholder image
     */
    public void basicLoadWithGlideCircular(String src, View viewToInflate, int placeHolderImage) {
        GlideApp.with(this).load(src).placeholder(placeHolderImage).apply(RequestOptions.circleCropTransform()).into((ImageView) viewToInflate);
    }

    /**
     * Basic Glide helper method to inflate an ImageView
     *
     * @param src              URI to load from
     * @param viewToInflate    ImageView where image will be shown
     * @param placeHolderImage Drawable of a placeholder image
     * @param errorImage       Drawable of an error image if failed to load
     */
    public void basicLoadWithGlide(String src, View viewToInflate, @Nullable Drawable placeHolderImage, @Nullable Drawable errorImage) {
        GlideApp.with(this).load(src).placeholder(placeHolderImage).error(errorImage).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) viewToInflate);
    }

    /**
     * Basic Glide helper method to inflate an ImageView
     *
     * @param src              URI to load from
     * @param viewToInflate    ImageView where image will be shown
     * @param placeHolderImage identifier of a placeholder image
     * @param errorImage       identifier of an error image if failed to load
     */
    public void basicLoadWithGlide(String src, View viewToInflate, int placeHolderImage, int errorImage) {
        GlideApp.with(this).load(src).placeholder(placeHolderImage).error(errorImage).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) viewToInflate);
    }

    /**
     * Basic Glide helper method to inflate an ImageView
     *
     * @param src              URI to load from
     * @param viewToInflate    ImageView where image will be shown
     * @param placeHolderImage Drawable of a placeholder image
     * @param errorImage       identifier of an error image if failed to load
     */
    public void basicLoadWithGlide(String src, View viewToInflate, @Nullable Drawable placeHolderImage, int errorImage) {
        GlideApp.with(this).load(src).placeholder(placeHolderImage).error(errorImage).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) viewToInflate);
    }

    /**
     * Basic Glide helper method to inflate an ImageView
     *
     * @param src              URI to load from
     * @param viewToInflate    ImageView where image will be shown
     * @param placeHolderImage identifier of a placeholder image
     * @param errorImage       Drawable of an error image if failed to load
     */
    public void basicLoadWithGlide(String src, View viewToInflate, int placeHolderImage, @Nullable Drawable errorImage) {
        GlideApp.with(this).load(src).placeholder(placeHolderImage).error(errorImage).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) viewToInflate);
    }

    /**
     * To display simple re-usable Snackbar with message and Close button
     *
     * @param container The container the Snackbar will be shown in
     * @param message   The message to be displayed
     */
    public void basicSnackBarWClose(View container, String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT)
                .setAction("Close", v -> {
                }).show();
    }

    /**
     * To display simple re-usable Snackbar with message and Close button
     *
     * @param container  The container the Snackbar will be shown in
     * @param message    The message to be displayed
     * @param lengthLong boolean indicating whether the Long length should be used for Snackbar or not
     */
    public void basicSnackBarWClose(View container, String message, boolean lengthLong) {
        Snackbar.make(container, message, (lengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT))
                .setAction("Close", v -> {
                }).show();
    }

    /**
     * To display simple re-usable Snackbar which cannot be closed until something external happens
     *
     * @param container The container the Snackbar will be shown in
     * @param message   The message to be displayed
     */
    public void basicIndefiniteSnackBar(View container, String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    public void showBasicToast(String toastMessage) {
        Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG).show();
    }
}
