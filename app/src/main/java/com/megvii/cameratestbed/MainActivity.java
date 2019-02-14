 package com.megvii.cameratestbed;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.megvii.camera.CameraView;

 public class MainActivity extends AppCompatActivity {


     private static final String TAG = "MainActivity";

     private static final int REQUEST_CAMERA_PERMISSION = 1;

     private static final String FRAGMENT_DIALOG = "dialog";

    private CameraView mCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraView = findViewById(R.id.cvView);
        if(mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }

    }

    private CameraView.Callback mCallback = new CameraView.Callback() {
        @Override
        public void onCameraOpened(CameraView cameraView) {
        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            super.onCameraClosed(cameraView);
        }

        @Override
        public void onPictureTaken(CameraView cameraView, byte[] data) {
            super.onPictureTaken(cameraView, data);
        }
    };

     @Override
     protected void onResume() {
         super.onResume();
         if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
             mCameraView.start();
         } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
             ConfirmationDialogFragment.newInstance(R.string.camera_permission_confirmation, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION, R.string.camera_permission_not_granted)
                     .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
         } else {
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
         }
     }

     @Override
     protected void onPause() {
         if(mCameraView != null) {
             mCameraView.stop();
         }
         super.onPause();
     }



     @Override
     protected void onDestroy() {
         super.onDestroy();
     }

     public static class ConfirmationDialogFragment extends DialogFragment {

         private static final String ARG_MESSAGE = "message";
         private static final String ARG_PERMISSIONS = "permissions";
         private static final String ARG_REQUEST_CODE = "request_code";
         private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

         public static ConfirmationDialogFragment newInstance(@StringRes int message,
                                                              String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
             ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
             Bundle args = new Bundle();
             args.putInt(ARG_MESSAGE, message);
             args.putStringArray(ARG_PERMISSIONS, permissions);
             args.putInt(ARG_REQUEST_CODE, requestCode);
             args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
             fragment.setArguments(args);
             return fragment;
         }

         @NonNull
         @Override
         public Dialog onCreateDialog(Bundle savedInstanceState) {
             final Bundle args = getArguments();
             return new AlertDialog.Builder(getActivity())
                     .setMessage(args.getInt(ARG_MESSAGE))
                     .setPositiveButton(android.R.string.ok,
                             new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                                     if (permissions == null) {
                                         throw new IllegalArgumentException();
                                     }
                                     ActivityCompat.requestPermissions(getActivity(),
                                             permissions, args.getInt(ARG_REQUEST_CODE));
                                 }
                             })
                     .setNegativeButton(android.R.string.cancel,
                             new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     Toast.makeText(getActivity(),
                                             args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                             Toast.LENGTH_SHORT).show();
                                 }
                             })
                     .create();
         }

     }
 }
