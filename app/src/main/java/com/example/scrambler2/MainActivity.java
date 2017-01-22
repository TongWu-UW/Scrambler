package com.example.scrambler2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import android.support.v7.app.AlertDialog;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.support.v4.view.MotionEventCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 1;
    public static final int CONTACT_VIEW = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateButton();
    }

    public void generateButton() {
        final Button cameraB = (Button) findViewById(R.id.button);
        cameraB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cameraM();
            }
        });}


    public void cameraM (){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (resultCode == RESULT_CANCELED){
            setContentView(R.layout.activity_main);
            generateButton();
        }
        else if (resultCode == RESULT_OK) {
            Log.v("test", "captured data");
            Bundle capturedImage = data.getExtras();
            Bitmap bitImage = capturedImage.getParcelable("data");
            // Make sure the request was successful

            if (requestCode == CAMERA_REQUEST) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), bitImage, "", "");
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Set Password");
                    builder.setCancelable(false);

                    // Set up the input
                    final EditText input = new EditText(this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Scramble!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                    }
                });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cameraM();
                    }
                });

                builder.show();
            }

        }

    }
}

