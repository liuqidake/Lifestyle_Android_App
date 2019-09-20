package com.example.nicolemorris.lifestyle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class
ProfilePicFragment extends Fragment
        implements View.OnClickListener {

    public static final int PICK_IMAGE = 1;

    Button bTakePic,bSelectPic,bNext;
    ProfilePicOnDataPass mDataPasser;
    ImageView mIvPic;
    String image;
    String name;

    FileOutputStream out;




    //Callback interface
    public interface ProfilePicOnDataPass{
        public void onProfilePicDataPass(String image);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (ProfilePicOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_pic, container, false);
        bNext = view.findViewById(R.id.b_next);
        bNext.setOnClickListener(this);

        bTakePic = view.findViewById(R.id.button_take_pic);
        bTakePic.setOnClickListener(this);

        bSelectPic = view.findViewById(R.id.button_select_pic);
        bSelectPic.setOnClickListener(this);

        //name = getArguments().getString("username");

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data == null){
                Toast.makeText(getContext(), "Unable to upload image", Toast.LENGTH_SHORT).show();
            }else{
                mIvPic = (ImageView) getActivity().findViewById(R.id.iv_profile);
                Bundle extras = data.getExtras();
                if(extras != null){
                    Uri profile_image = getImageUri((Bitmap)extras.get("data"));
                    image = profile_image.toString();
                    mIvPic.setImageURI(profile_image);
                }else {
                    try {
                        Uri profile_image = data.getData();
                        image = profile_image.toString();
                        System.out.println("uri is "+image);
                        //image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageFilePath);
                        mIvPic.setImageURI(profile_image);
                    }catch(Exception e){
                        Toast.makeText(getActivity(), "Not able to get the image from gallery", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }else{
            System.out.println("result is not ok");
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.b_next: {
                //NEED TO ADD DATE TO PASS FOR STORAGE :)
                mDataPasser.onProfilePicDataPass(image);
                break;
            }
            case R.id.button_select_pic:{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_IMAGE);
                break;
            }
            case R.id.button_take_pic: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(intent, PICK_IMAGE);
                }
                break;
            }
        }
    }

//    private Uri convertBitmaptoUri(Bitmap image){
//        Uri tempUri = getImageUri(getApplicationContext(), photo);
//        File finalFile = new File(getRealPathFromURI(tempUri));
//    }

    public Uri getImageUri(Bitmap image) {
        try {
            File f = new File(getContext().getCacheDir(), "temp");
            f.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            return Uri.fromFile(f);
        }catch(Exception e){
            Toast.makeText(getActivity(), "Unable to get Image", Toast.LENGTH_SHORT).show();
            return  null;
        }
    }


//
//    private void saveProfileImage(Uri image){
//        try{
//            String file = name;
//            out = getActivity().openFileOutput(file, Context.MODE_PRIVATE);
//            image.
//            ObjectOutputStream os = new ObjectOutputStream(out);
//            os.writeObject(this);
//            os.close();
//            out.close();
//        } catch(Exception e){
//            System.out.println(e);
//            Toast.makeText(getActivity(), "Cannot save profile image", Toast.LENGTH_SHORT).show();
//        }
//
//    }

}
