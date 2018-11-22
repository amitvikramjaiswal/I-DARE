package com.opensource.app.idare.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by amitvikramjaiswal on 28/6/18.
 */
public class ImageEditor {
    public static final int IMAGE_EDITOR_REQUEST_CODE = CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

    public static void cropImage(Uri uri, Activity activity) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }

    public static void cropImage(Uri uri, Fragment fragment, Context context) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(context, fragment);
    }

    public static Uri getUpdatedImage(int resultCode, Intent data) throws Exception {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                return result.getUri();
            case CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE:
                throw result.getError();
        }

        return null;
    }
}
