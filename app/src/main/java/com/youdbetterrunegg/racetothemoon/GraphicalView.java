package com.youdbetterrunegg.racetothemoon;



import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;
public class GraphicalView extends ImageView{
    private Context mContext;
    int x1 = -1;
    int y1 = -1;
    int x2 = -1;
    int y2 = -1;


    private Handler h;
    private final int FRAME_RATE = 2;
    Bitmap rocket;
    Bitmap background;
    Bitmap numbers;
    int shipsize;
    float shipratio;
    Paint bigTextPaint = new Paint();
    Paint smallTextPaint = new Paint();
    Paint scaleimages = new Paint();
    Random rand = new Random();
    private String equation;


    public GraphicalView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        mContext = context;
        h = new Handler();
        shipratio = (float) 1.749;
        equation = String.valueOf(rand.nextInt(4)) + "+" + String.valueOf(rand.nextInt(4) +"=");
        scaleimages.setAntiAlias(true);
        scaleimages.setFilterBitmap(true);

        bigTextPaint.setColor(Color.parseColor("#ffffffff"));
        bigTextPaint.setAntiAlias(true);
        smallTextPaint.setColor(Color.parseColor("#ffffffff"));
        smallTextPaint.setAntiAlias(true);
        rocket = BitmapFactory.decodeResource(getResources(), R.drawable.rocket);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.deepfield);
        numbers = BitmapFactory.decodeResource(getResources(), R.drawable.numbers);
    }
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
    private int screenWidth;
    private int screenHeight;
    private boolean winner = false;
    private String playerwin= "";
    private int count;
    protected void onDraw(Canvas c) {
        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        shipsize = screenWidth/10;
        bigTextPaint.setTextSize(((int) this.getHeight() / 10));
        smallTextPaint.setTextSize(((int) this.getHeight() / 15));
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);
        rocket = Bitmap.createScaledBitmap(rocket, shipsize, Math.round(shipsize*shipratio), true);
        numbers = Bitmap.createScaledBitmap(numbers, screenWidth/3, (int)((screenWidth/3)*1.468), true);
        c.drawBitmap(background, 0, 0,scaleimages);


        if (x1<0 && y1 <0) {
            x1 = screenWidth/4 - (rocket.getWidth()/2);
            x2 = (screenWidth/4)*3- (rocket.getWidth()/2);
            y1 = screenHeight - numbers.getHeight() - rocket.getHeight();
            y2 = y1;
        } else {
            if(!winner){
                count = count + 1;
                if (count == 10){
                    //new question
                    equation = String.valueOf(rand.nextInt(4)) + "+" + String.valueOf(rand.nextInt(4) +"=");
                    count =0;
                }
                y1 += rand.nextInt(10)-16;
                y2 += rand.nextInt(10)-16;
                if (y1 <= 0) {
                    playerwin = "Player 1 wins";
                    winner = true;}
                if (y2 <= 0) {
                    playerwin = "Player 2 wins";
                    winner = true;}
            }
        }

        //
        //

        //
        //

        c.drawText(playerwin, screenWidth/4, screenHeight/2, smallTextPaint);
        c.drawText(equation, screenWidth/10, screenHeight - (numbers.getHeight()/2), bigTextPaint);
        c.drawBitmap(numbers, screenWidth - numbers.getWidth(),  screenHeight - numbers.getHeight(), scaleimages);
        c.drawBitmap(rocket, x1, y1, scaleimages);
        c.drawBitmap(rocket, x2, y2, scaleimages);
        h.postDelayed(r, FRAME_RATE);
    }
}