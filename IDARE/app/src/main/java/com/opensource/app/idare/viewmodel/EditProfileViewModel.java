package com.opensource.app.idare.viewmodel;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.opensource.app.idare.R;
import com.opensource.app.idare.model.data.entity.ProfilePic;
import com.opensource.app.idare.model.data.entity.UserProfileRequestModel;
import com.opensource.app.idare.model.data.entity.UserProfileResponseModel;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.impl.ProfileServiceImpl;
import com.opensource.app.idare.model.service.impl.SessionFacadeImpl;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.ImageEditor;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Session;
import com.opensource.app.idare.utils.Utils;
import com.opensource.app.idare.utils.handler.AlertDialogHandler;
import com.opensource.app.idare.view.activity.MainActivity;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akokala on 11/6/2017.
 */

public class EditProfileViewModel extends BaseViewModel implements TextWatcher {

    private static final String TAG = EditProfileViewModel.class.getSimpleName();
    private static final int IMAGE_PICKER_REQUEST_CODE = 2;
    private static final int IMAGE_EDITOR_REQUEST_CODE = CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;;
    private DataListener dataListener;
    private Session session;

    private ObservableField<String> nameFromEditTExt = new ObservableField<>("");
    private ObservableField<String> emailFromEditText = new ObservableField<>("");
    private ObservableField<String> alternativeNumFromEditText = new ObservableField<>("");
    private ObservableField<String> passwordFromEditText = new ObservableField<>("");
    private ObservableField<Boolean> enableSaveButton = new ObservableField<>(false);
    private ObservableField<Bitmap> observableProfilePic = new ObservableField<>();

    private String phoneNumberFromBundle;
    private String userChoosenTask;
    private Uri dpUri;
    private ProfilePic profilePic;

    public EditProfileViewModel(Context context, DataListener dataListener, String phoneNumberFromBundle) {
        super(context);
        this.dataListener = dataListener;
        this.phoneNumberFromBundle = phoneNumberFromBundle;

        setListener();

        prepopulateUserDetails();
    }

    // Show the prepopulated value in the Ui for Edit User
    private void prepopulateUserDetails() {
        UserProfileResponseModel userProfileResponseModel = Session.getInstance().getUserProfileResponseModel();
        profilePic = Session.getInstance().getProfilePic();
        if (userProfileResponseModel != null) {

            nameFromEditTExt.set(PreferencesManager.getInstance(getContext()).getUserDetails().getName());
            emailFromEditText.set(userProfileResponseModel.getEmail());
            alternativeNumFromEditText.set(userProfileResponseModel.getAlternate());
            passwordFromEditText.set(PreferencesManager.getInstance(getContext()).getUserPass());
        }
        if (profilePic != null && !profilePic.getProfilePicUri().isEmpty()) {
            try {
                observableProfilePic.set(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(profilePic.getProfilePicUri())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListener() {
        dataListener.getName().addTextChangedListener(this);
        dataListener.getEmail().addTextChangedListener(this);
        dataListener.getAlternativeNumber().addTextChangedListener(this);
        dataListener.getPassword().addTextChangedListener(this);
    }

    @BindingAdapter("android:image_bitmap")
    public static void setProfilePic(ImageView profilePic, Bitmap bitmap) {
        profilePic.setImageBitmap(bitmap);
    }

    public ObservableField<String> getNameFromEditTExt() {
        return nameFromEditTExt;
    }

    public ObservableField<String> getEmailFromEditText() {
        return emailFromEditText;
    }

    public ObservableField<String> getAlternativeNumFromEditText() {
        return alternativeNumFromEditText;
    }

    public ObservableField<String> getPasswordFromEditText() {
        return passwordFromEditText;
    }

    public ObservableField<Boolean> getEnableSaveButton() {
        return enableSaveButton;
    }

    public ObservableField<Bitmap> getObservableProfilePic() {
        return observableProfilePic;
    }

    public View.OnClickListener onPicClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        };
    }

    private void selectImage() {
        Intent in = getPickImageChooserIntent();

        if (in != null) {
            dataListener.startActivityForResult(in, IMAGE_PICKER_REQUEST_CODE);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getContext().getResources().getString(R.string.camera_no_permissions))
                    .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                            intent.setData(uri);
                            dataListener.startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
        }
    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        Log.d(TAG, "FileUri = " + outputFileUri);

        List<Intent> allIntents = new ArrayList();
        PackageManager packageManager = getContext().getPackageManager();

        // collect all camera intents
//        if (cameraGranted) {
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
//            }
        }

//        if (writeGranted) {
        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }
//        }

        if (allIntents == null || allIntents.size() == 0) {
            return null;
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getContext().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICKER_REQUEST_CODE:
                    Uri selectedImage;
                    Uri outputFile = getCaptureImageOutputUri();
                    boolean isCamera = (data == null ||
                            data.getData() == null ||
                            data.getData().toString().contains(outputFile.getPath()));

                    if (isCamera) {     /** CAMERA **/
                        selectedImage = outputFile;
                    } else {            /** ALBUM **/
                        selectedImage = data.getData();
                    }

                    if (selectedImage != null) {
                        ImageEditor.cropImage(selectedImage, dataListener.getActivity());
                    }
                    break;
                case IMAGE_EDITOR_REQUEST_CODE:
                    try {
                        Uri uri = ImageEditor.getUpdatedImage(resultCode, data);
                        encodeImage(uri);

                        observableProfilePic.set(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri));
                        dpUri = uri;
                    } catch (Exception e) {
                        Log.e(TAG, "ImageEditor Error: ", e);
                    }
                    break;
            }
        }
    }

    private void encodeImage(Uri uri) {
        final InputStream imageStream;
        try {
            imageStream = getContext().getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] b = baos.toByteArray();
//            String base64Image = Base64.encodeToString(b, Base64.NO_WRAP);
            profilePic = new ProfilePic();
            profilePic.setArrByteImage(b);
            profilePic.setProfilePicUri(uri.toString());
            Session.getInstance().setProfilePic(profilePic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Set the value for name
    public TextWatcher nameTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                nameFromEditTExt.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for password
    public TextWatcher passwordTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                passwordFromEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for mobile
    public TextWatcher alternativeNumTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                alternativeNumFromEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Set the value for email
    public TextWatcher emailTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                emailFromEditText.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }


    // Service Call for Post profile details
    private void postProfileDetails() {
        dataListener.hideKeyBoard();
        dataListener.showProgress();
        UserProfileRequestModel userProfileBody = SessionFacadeImpl.getInstance().getRequestBody(phoneNumberFromBundle, passwordFromEditText.get(), nameFromEditTExt.get(),
                emailFromEditText.get(), phoneNumberFromBundle, alternativeNumFromEditText.get(), PreferencesManager.getInstance(getContext()).getLastLocation());
        SessionFacadeImpl.getInstance().postProfileDetails(getContext(), userProfileBody, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                dataListener.hideProgress();
                if (response != null) {
                    // Store response in UserContext Model
                    PreferencesManager.getInstance(getContext()).setUserProfileResponse(response);

                    dataListener.hideKeyBoard();
                    dataListener.finish();
//                    if (!Session.getInstance().isRegisteredToFCM())
//                        registerDeviceToFcm();
                        dataListener.startActivity(MainActivity.getStartIntent(getContext(), response.getName()));
                }
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                dataListener.hideProgress();
                dataListener.hideKeyBoard();
                if (!Utils.isNetworkAvailable(getContext())) {
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                            getContext().getResources().getString(R.string.network_error_message), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                } else {
                    dataListener.showAlertDialog(getContext().getResources().getString(R.string.error_title),
                            getContext().getResources().getString(R.string.error_message), false, getContext().getResources().getString(R.string.positive_button),
                            null, new AlertDialogHandler() {
                                @Override
                                public void onPositiveButtonClicked() {
                                }

                                @Override
                                public void onNegativeButtonClicked() {
                                }
                            });
                }
            }
        });
    }

//    private void registerDeviceToFcm() {
//        String token = FirebaseInstanceId.getInstance().getToken();
//        SessionFacadeImpl.getInstance().registerDeviceToFCM(getContext(), NotificationServiceImpl.getRequestBody(token), null, null);
//    }

    // Onclick Save Button
    public View.OnClickListener saveClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferencesManager.getInstance(getContext()).wasUserLoggedIn()) {
                    updateProfile();
                } else {
                    postProfileDetails();
                }
            }
        };
    }

    private void updateProfile() {
        UserProfileRequestModel requestBody = SessionFacadeImpl.getInstance().getRequestBody(Session.getInstance().getUserProfileResponseModel().getUsername(), null, nameFromEditTExt.get(), emailFromEditText.get(),
                Session.getInstance().getUserProfileResponseModel().getUsername(), alternativeNumFromEditText.get(), PreferencesManager.getInstance(getContext()).getLastLocation());
        SessionFacadeImpl.getInstance().updateProfile(getContext(), Session.getInstance().getUserProfileResponseModel().getId(), requestBody, new IDAREResponseHandler.ResponseListener<UserProfileResponseModel>() {
            @Override
            public void onSuccess(UserProfileResponseModel response) {
                enableSaveButton.set(false);
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                enableSaveButton.set(true);
            }
        });
        uploadProfilePic();
    }

    private void uploadProfilePic() {
        ProfileServiceImpl.getInstance().uploadProfilePic(getContext(), profilePic, new IDAREResponseHandler.ResponseListener<ProfilePic>() {
            @Override
            public void onSuccess(ProfilePic response) {

            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {

            }
        });

        SessionFacadeImpl.getInstance().uploadProfilePic(getContext(), profilePic, new IDAREResponseHandler.ResponseListener<ProfilePic>() {
            @Override
            public void onSuccess(ProfilePic response) {
                Log.d(TAG, "base64 image - " + response.getArrByteImage());
            }
        }, new IDAREResponseHandler.ErrorListener() {
            @Override
            public void onError(IDAREErrorWrapper error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (Utils.emptyOrNullCheck(nameFromEditTExt.get()) && Utils.emptyOrNullCheck(emailFromEditText.get())
                && Utils.isValidEmail(emailFromEditText.get()) && Utils.emptyOrNullCheck(passwordFromEditText.get())) {
            enableSaveButton.set(true);
        } else {
            enableSaveButton.set(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public interface DataListener extends BaseViewModel.DataListener {

        EditText getName();

        EditText getEmail();

        EditText getAlternativeNumber();

        EditText getPassword();

    }
}
