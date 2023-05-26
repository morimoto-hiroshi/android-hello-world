package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;

public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
    }

    // Viewを継承したクラス
    class MyView extends View {
        MyView me = this;
        Paint paint;
        Path path;
        float StrokeWidth1 = 20f;
        float StrokeWidth2 = 40f;
        float dp;
        Bitmap bmp;
        float bmpPosX = 100;
        float bmpPosY = 100;
        float bmpTargetX;
        float bmpTargetY;
        private final Handler handler = new Handler(Looper.getMainLooper());
        private Runnable runnable;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
            path = new Path();

            // スクリーンサイズからdpiのようなものを作る
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            dp = getResources().getDisplayMetrics().density;
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.boy);
            Log.d("debug", "fdp=" + dp);
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("debug", "ACTION_DOWN "
                            + "pressure:" + motionEvent.getPressure()
                            + " x:" + motionEvent.getX()
                            + " y:" + motionEvent.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("debug", "ACTION_UP "
                            + "pressure:" + motionEvent.getPressure()
                            + " x:" + motionEvent.getX()
                            + " y:" + motionEvent.getY()
                            + " duration(mSec): " + (motionEvent.getEventTime() - motionEvent.getDownTime()));
                    // 画像の位置をアニメーション
                    bmpTargetX = motionEvent.getX();
                    bmpTargetY = motionEvent.getY();
                    startAnimation();
                    this.performClick();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d("debug", "ACTION_MOVE "
                            + "pressure:" + motionEvent.getPressure()
                            + " x:" + motionEvent.getX()
                            + " y:" + motionEvent.getY());
                    // 画像の位置を変えて再描画
                    bmpPosX = motionEvent.getX();
                    bmpPosY = motionEvent.getY();
                    this.invalidate();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    Log.d("debug", "ACTION_CANCEL");
                    break;
            }
            return true; // false を返すと ACTION_MOVE, ACTION_UP が来ない
        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }

        void startAnimation() {
            if (runnable == null) {
                Log.d("debug", "*** start task");
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        float difX = Math.min(Math.abs(bmpTargetX - bmpPosX), 10);
                        float difY = Math.min(Math.abs(bmpTargetY - bmpPosY), 10);
                        bmpPosX += (bmpPosX < bmpTargetX) ? difX : -difX;
                        bmpPosY += (bmpPosY < bmpTargetY) ? difY : -difY;
                        me.invalidate();
                        if (bmpPosX == bmpTargetX && bmpPosY == bmpTargetY) {
                            stopAnimation();
                        } else {
                            handler.postDelayed(this, 16);
                        }
                    }
                };
                handler.post(runnable);
            }
        }

        void stopAnimation() {
            Log.d("debug", "*** stop task");
            handler.removeCallbacks(runnable);
            runnable = null;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 背景
            canvas.drawColor(Color.argb(255, 125, 225, 225));

            // Canvas 中心点
            float xc = getWidth() / 2.0f;
            float yc = getHeight() / 2.0f;

            // 円
            paint.setColor(Color.argb(255, 125, 125, 255));
            paint.setStrokeWidth(StrokeWidth1);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            // (x1,y1,r,paint) 中心x1座標, 中心y1座標, r半径
            canvas.drawCircle(xc - 15 * dp, yc - 55 * dp, xc / 2, paint);

            // 矩形
            paint.setColor(Color.argb(255, 255, 0, 255));
            paint.setStyle(Paint.Style.STROKE);
            // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
            canvas.drawRect(xc - 30 * dp, yc - 50 * dp,
                    xc + 120 * dp, yc + 100 * dp, paint);

            // 線
            paint.setStrokeWidth(StrokeWidth1);
            paint.setColor(Color.argb(255, 0, 255, 0));
            // (x1,y1,x2,y2,paint) 始点の座標(x1,y1), 終点の座標(x2,y2)
            canvas.drawLine(xc + 20 * dp, yc - 30 * dp, xc - 70 * dp, yc + 70 * dp, paint);

            // 三角形を書く
            float tx1 = 230 * dp;
            float ty1 = 370 * dp;
            float tx2 = 100 * dp;
            float ty2 = 500 * dp;
            float tx3 = 350 * dp;
            float ty3 = 500 * dp;

            paint.setStrokeWidth(10);
            paint.setColor(Color.WHITE);
            path.moveTo(tx1, ty1);
            path.lineTo(tx2, ty2);
            path.lineTo(tx3, ty3);
            path.lineTo(tx1, ty1);
            canvas.drawPath(path, paint);

            // 円
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(StrokeWidth2);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            // (x,y,r,paint) x座標, y座標, r半径
            canvas.drawCircle(220 * dp, 130 * dp, 40 * dp, paint);

            // Bitmap 画像を表示
            canvas.drawBitmap(bmp, bmpPosX, bmpPosY, paint);

            // Textの表示
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(5);
            paint.setTextSize(100);
            paint.setColor(Color.argb(255, 255, 255, 0));
            canvas.drawText("こんにちは。魑魅魍魎 hello !", 120, 600, paint);
        }
    }
}
