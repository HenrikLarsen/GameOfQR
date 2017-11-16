package com.example.henriklarsen.gameofqr;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GameActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private String color = "BLACK";
    private GameViewer layoutView;
    private StaticBoard staticBoard = new StaticBoard();
    private GameOfLife gameOfLife = new GameOfLife(staticBoard);
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        String textFromMain = getIntent().getStringExtra("output");

        // Creating the QR code in the same format as the board.
        QRCode qrcode = new QRCode();
        try {
            qrcode = Encoder.encode(textFromMain, ErrorCorrectionLevel.H);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        ByteMatrix byteMatrix = qrcode.getMatrix();
        byte[][] playBoard = byteMatrix.getArray();

        // Copies the QR code to the main board.
        byte[][] mainBoard = new byte[playBoard.length*3][playBoard.length*3];
        for(int i = 0; i < playBoard.length; i++){
            for(int j = 0; j < playBoard[0].length; j++){
                mainBoard[i][j]= playBoard[i][j];
            }
        }

        // Sets the board with the QR code.
        staticBoard.setBoard(mainBoard);
        calculateCellSize(playBoard);

        // Displaying the board.
        layoutView = new GameViewer(this, staticBoard , gameOfLife);
        LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout);
        l.addView(layoutView);

        // Button Listeners
        final Button startBTN = (Button)findViewById(R.id.startBTN);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(startBTN.getText().equals("Start game")){
                    // Starts the game.
                    startBTN.setText("Pause game");
                    gameOfLife.setIsAnimating(true);
                }else if(startBTN.getText().equals("Pause game")){
                    // Pauses the game.
                    startBTN.setText("Start game");
                    gameOfLife.setIsAnimating(false);
                }
                layoutView.postInvalidate();
            }

        });

        final Button takePictureBTN = (Button)findViewById(R.id.takePictureBTN);
        takePictureBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Pauses the game and run "takePicture" method.
                startBTN.setText("Start game");
                gameOfLife.setIsAnimating(false);
                takePicture();
            }
        });

        final Button settingBTN = (Button)findViewById(R.id.settingBTN);
        settingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                // Opens the settings dialog.
                showSettingDialog();
            }

        });

    }

    private void calculateCellSize(byte[][] b){
        // Sets the cells size relative to the size of the QR-Code
        // and the screens dimensions.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        staticBoard.setCellSize((width - 100)/b.length);
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Creates a new file.
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Starts the camera intent.
            if (imageFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "GameOfQR" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Path of the picture
        picturePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Creates a Bitmap of the picture taken.
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap photoBitmap = BitmapFactory.decodeFile(picturePath, options);
            setImageToView(photoBitmap);
        }
    }

    private int[][] floydSteinBerg(Bitmap bm){
        // int array representing the image by colors
        int[][] colorValue = new int[bm.getWidth()][bm.getHeight()];
        for (int y = 0; y < bm.getHeight(); y++) {
            for (int x = 0; x < bm.getWidth(); x++) {
                int currentPixel = bm.getPixel(x, y);
                int redValue = Color.red(currentPixel);
                int blueValue = Color.blue(currentPixel);
                int greenValue = Color.green(currentPixel);
                colorValue[x][y] = (int) (greenValue * .3 + redValue * .3 + blueValue * .3);
            }
        }

        for (int y = 0; y < bm.getHeight(); y++) {
            for (int x = 0; x < bm.getWidth(); x++) {
                int newPixel;
                
                // Sets a either a black or a white color value to each cell.
                if(colorValue[x][y] > 127){
                    newPixel = 255;
                }else{
                    newPixel = 0;
                }

                // gets the error diffusion.
                int error = colorValue[x][y] - newPixel;

                // adds the residual quantization error of a pixel onto its neighboring pixels, to be dealt with later.
                if (x + 1 < bm.getWidth()) {
                    colorValue[x + 1][y] = colorValue[x + 1][y] + error * 7/16;
                }
                if (x - 1 >= 0 && y + 1 < bm.getHeight()) {
                    colorValue[x - 1][y + 1] = colorValue[x - 1][y + 1] + error * 3/16;
                }
                if (y + 1 < bm.getHeight()){
                    colorValue[x][y + 1] = colorValue[x][y + 1] + error * 5/16;
                }
                if (x + 1 < bm.getWidth() && y + 1 < bm.getHeight()){
                    colorValue[x + 1][y + 1] = colorValue[x + 1][y + 1] + error/16;
                }
            }
        }
        return colorValue;
    }

    private void setImageToView(Bitmap bm){
        // Creates scealed down bitmap fo the picture.
        Bitmap newBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(picturePath), bm.getWidth()/10, bm.getHeight()/10);

        // Flips the picture 90 degrees.
        Bitmap orientedBitmap = fixOrientation(newBitmap);

        // Floyd-Steinberg dithering to get a good halftone representation of the picture.
        int[][] boardArray = floydSteinBerg(orientedBitmap);
        byte[][] playBoard = new byte[orientedBitmap.getWidth()][orientedBitmap.getHeight()];
        byte[][] mainBoard = new byte[playBoard.length*2][playBoard[0].length*3];

        // creating a board representation of the picture
        for (int x = 0; x < orientedBitmap.getWidth(); x++) {
            for (int y = 0; y < orientedBitmap.getHeight(); y++) {
                if(boardArray[x][y] < 127){
                    playBoard[x][y] = 1;
                } else{
                    playBoard[x][y] = 0;
                }
                mainBoard[x][y]= playBoard[x][y];
            }
        }

        // Sets the cell size and the board
        calculateCellSize(playBoard);
        staticBoard.setBoard(mainBoard);
    }

    private Bitmap fixOrientation(Bitmap bm) {
        // Rotates the board 90 degrees.
        if (bm.getWidth() > bm.getHeight()) {
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bm = Bitmap.createBitmap(bm , 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            return bm;
        }
        return null;
    }

    // Making the popup dialog where the users can change the color and size of the cells.
    private void showSettingDialog(){
        // Setting up the dialog
        AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Seek bar to change the cell size.
        SeekBar seek = new SeekBar(this);
        seek.setProgress(staticBoard.getCellSize());
        seek.setMax(100);

        // Spinner to change colors.
        Spinner spinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,R.array.colorList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(color));

        TextView color = new TextView(this);
        color.setText("Color: ");
        color.setTextSize(17);

        TextView size = new TextView(this);
        size.setText("Size: ");
        size.setTextSize(17);

        // Adds every element to the layout in the dialog.
        layout.addView(size);
        layout.addView(seek);
        layout.addView(color);
        layout.addView(spinner);

        popDialog.setTitle("Settings");
        popDialog.setView(layout);

        //Listener to the spinner.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                layoutView.setDrawColor(parent.getItemAtPosition(position));
                layoutView.postInvalidate();
                setColor(parent.getItemAtPosition(position));
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // Listener to the Seek bar.
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                staticBoard.setCellSize(progress);
                layoutView.postInvalidate();
            }

            public void onStartTrackingTouch(SeekBar arg0) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        popDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Showing the dialog
        popDialog.create();
        popDialog.show();
    }

    private void setColor(Object color){
        if(color.equals("RED")){
            this.color = "RED";
        }else if(color.equals("GREEN")){
            this.color = "GREEN";
        }else if(color.equals("BLUE")){
            this.color = "BLUE";
        }else if(color.equals("YELLOW")){
            this.color = "YELLOW";
        }else if(color.equals("BLACK")){
            this.color = "BLACK";
        }else if(color.equals("BROWN")){
            this.color = "BROWN";
        }else if(color.equals("ORANGE")){
            this.color = "ORANGE";
        }else if(color.equals("GRAY")){
            this.color = "GRAY";
        }else if(color.equals("PURPLE")){
            this.color = "PURPLE";
        }else if(color.equals("PINK")){
            this.color = "PINK";
        }else{
            this.color = "BLACK";
        }
    }
}
