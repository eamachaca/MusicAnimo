package com.example.deit0.musicanimo;

/**
 * Created by DeIt0 on 18/07/2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by echessa on 8/31/15.
 */
public class CustomView extends View {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the bitmap background and the associated face detections.
     */
    void setContent(Bitmap bitmap, SparseArray<Face> faces) {
        mBitmap = bitmap;
        mFaces = faces;
        invalidate();
    }

    /**
     * Draws the bitmap background and the associated face landmarks.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
            //drawFaceRectangle(canvas, scale); cambiada #1
            //drawFaceAnnotations(canvas, scale); cambiada #2
            detectFaceCharacteristics(canvas, scale);
        }
    }

    /**
     * Draws the bitmap background, scaled to the device size.  Returns the scale for future use in
     * positioning the facial landmark graphics.
     */
    private double drawBitmap(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect destBounds = new Rect(0, 0, (int)(imageWidth * scale), (int)(imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

    /**
     * Draws a rectangle around each detected face
     */
    private void drawFaceRectangle(Canvas canvas, double scale) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (int i = 0; i < mFaces.size(); ++i) {
            Face face = mFaces.valueAt(i);
            canvas.drawRect((float)(face.getPosition().x * scale),
                    (float)(face.getPosition().y * scale),
                    (float)((face.getPosition().x + face.getWidth()) * scale),
                    (float)((face.getPosition().y + face.getHeight()) * scale),
                    paint);
        }
    }
        /*
        * Añadiendo una nueva funcion al proyecto xD
        * */
        private void drawFaceAnnotations(Canvas canvas, double scale) {
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);

            for (int i = 0; i < mFaces.size(); ++i) {
                Face face = mFaces.valueAt(i);
                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x * scale);
                    int cy = (int) (landmark.getPosition().y * scale);
                    canvas.drawCircle(cx, cy, 10, paint);
                }
            }

        }

    /*
    Añadiendo otra nueva funcion al proyecto xD
     */
        private void detectFaceCharacteristics(Canvas canvas, double scale) {
            Paint paint = new Paint();
            paint.setColor(Color.DKGRAY);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(1);
            paint.setTextSize(25.0f);

            for (int i = 0; i < mFaces.size(); ++i) {
                Face face = mFaces.valueAt(i);
                float cx = (float)(face.getPosition().x * scale);
                float cy = (float) (face.getPosition().y * scale);
                float ixx=face.getIsSmilingProbability();
                canvas.drawText(String.valueOf(ixx), cx, cy + 10.0f, paint);
            }
        }
}