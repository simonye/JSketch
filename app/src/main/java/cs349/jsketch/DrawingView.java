package cs349.jsketch;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.graphics.*;

import java.io.IOException;
import java.util.*;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;
import android.graphics.drawable.shapes.Shape;
import 	android.graphics.Rect;
import android.widget.TextView;
import android.content.*;
/**
 * Created by simon on 2016-07-06.
 */

class NamedShape {

    public int color;
    public int fillcolor;
    public int type;
    public float x;
    public float y;
    public float width;
    public float height;
    public float size;
    public boolean selected;
    public boolean filled;

    public NamedShape( int color,int type,float x,float y,float width,float height,float size){
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.size = size;
        selected = false;
        filled = false;

    }
};

public class DrawingView extends View {

    private Paint drawPaint,canvasPaint;
    //initial color
    private int paintColor = 0xFF660000;
    //canvas
    private Canvas drawCanvas;
    //canvas bitmap
    private Bitmap canvasBitmap;

    int type;
    int shape_num = 0;
    boolean select = false;
    boolean fill =false;

    float initialX,initialY;
    float finalX,finalY;
    private float brushSize, lastBrushSize;

    int selectedShap = 0;
    TextView tv;

    ArrayList <NamedShape> shapes = new ArrayList<>();

    public void setTV(TextView tv){
        this.tv = tv;
    }
    public DrawingView(Context context, AttributeSet attrs) {
        super(context,attrs);

        setupDrawing();
    }

    public void addShape(int color,int type,float x,float y,float width,float height){
        shapes.add(new NamedShape(color, type, x, y, width, height, brushSize));
    }

    public void changeSelect(){
        select = true;
    }
    public void changeFill(){
        fill = !fill;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//view given size
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //draw view
       // canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
       // canvas.drawCircle(150,150,200,drawPaint);
        for(NamedShape s : shapes){
            if(s.type==1){
                drawPaint.setColor(s.color);
                drawPaint.setStrokeWidth(s.size);
                canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
                canvas.drawLine(s.x,s.y,s.width,s.height,drawPaint);
            }
            else if(s.type==2){
                if(s.filled==true){
                   drawPaint.setStyle(Paint.Style.FILL);
                    drawPaint.setColor(s.fillcolor);
                    canvas.drawCircle(s.x, s.y, (s.width - s.x) / 2, drawPaint);
                }

                drawPaint.setStyle(Paint.Style.STROKE);
                drawPaint.setColor(s.color);
                drawPaint.setStrokeWidth(s.size);
                if(s.selected == true) {
                    Log.d("dududu", "circle selected");
                    drawPaint.setStrokeWidth(5);

                }
                //drawPaint.setColor(s.color);
                //drawPaint.setStrokeWidth(s.size);
                canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
                //canvas.drawLine(s.x,s.y,s.width,s.height,drawPaint);
                canvas.drawCircle(s.x,s.y,(s.width-s.x)/2,drawPaint);

            }
            else if(s.type==3){
                if(s.filled==true){
                    drawPaint.setStyle(Paint.Style.FILL);
                    drawPaint.setColor(s.fillcolor);
                    canvas.drawRect(s.x, s.y, (s.width), (s.height), drawPaint);
                }
                //drawPaint.setStyle(Paint.Style.STROKE);
                drawPaint.setColor(s.color);
                drawPaint.setStrokeWidth(s.size);
                if(s.selected == true){
                    Log.d("dududu", "rectangle selected");
                    drawPaint.setStrokeWidth(5);
                }
                drawPaint.setStyle(Paint.Style.STROKE);
                //drawPaint.setColor(s.color);
                //drawPaint.setStrokeWidth(s.size);
                //canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
                //canvas.drawLine(s.x,s.y,s.width,s.height,drawPaint);
                canvas.drawRect(s.x,s.y,(s.width),(s.height),drawPaint);
            }
        }
    }
    public void resetSelection(){
        if(shape_num > 0) {
            NamedShape s = shapes.get(selectedShap);
            s.selected = false;
            shapes.set(selectedShap, s);
        }
    }
    public void stopSelection(){
        select = false;
    }
    public void erase(){
        shapes.remove(selectedShap);
        shape_num--;
        selectedShap = 0;
    }
    public Bitmap getBitmap(){
        return canvasBitmap;
    }
    /*
    public String saveToInternalStorage(Activity main) throws IOException {
        ContextWrapper cw = new ContextWrapper(main.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        java.io.File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        java.io.File mypath=new java.io.File(directory,"profile.jpg");

        java.io.FileOutputStream fos = null;
        try {
            fos = new java.io.FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            canvasBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert fos != null;
            assert fos != null;
            fos.close();
        }
        return directory.getAbsolutePath();
    }
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //detect user touch

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                initialY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                finalX = event.getX();
                finalY = event.getY();
                if(select==false) {
                    addShape(paintColor, type, initialX, initialY, finalX, finalY);
                    shape_num++;
                }
                else{
                    for(int i = shape_num-1; i>=0;i--){
                        NamedShape s = shapes.get(i);
                        if(s.type ==2) {
                            if (((s.x-initialX)*(s.x-initialX)+(s.y-initialY)*(s.y-initialY))<=(s.width-s.x)/2*(s.width-s.x)/2) {
                                Log.d("circle selected", "onTouchEvent: ");
                                resetSelection();
                                selectedShap = i;
                                tv.setTextColor(s.color);
                                s.selected = true;
                                if(fill==true){
                                    //s.selected = true;
                                    s.fillcolor = paintColor;
                                    s.filled = true;
                                    shapes.set(i,s);
                                }
                                if(fill==false){
                                    s.width = finalX + (s.width - s.x);
                                    s.height = finalY + (s.height - s.y);
                                    s.x = finalX;
                                    s.y = finalY;
                                    shapes.set(i,s);
                                }
                                //s.selected = true;
                                break;
                            }
                        }
                        else if(s.type ==3) {
                            if (initialX >= s.x && initialX <= s.width && initialY >= s.y && initialY <= s.height) {
                                Log.d("rect selected", "onTouchEvent: ");
                                resetSelection();
                                selectedShap = i;
                                tv.setTextColor(s.color);
                                s.selected = true;
                                if(fill==true){
                                    //s.selected = true;
                                    s.fillcolor = paintColor;
                                    s.filled = true;
                                    shapes.set(i,s);
                                }
                                if(fill==false){
                                    s.width = finalX + (s.width - s.x);
                                    s.height = finalY + (s.height - s.y);
                                    s.x = finalX;
                                    s.y = finalY;
                                    shapes.set(i,s);
                                }
                                //s.selected = true;
                                break;
                            }

                        }
                    }
                }
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
    public void setType(int newType){
        type = newType;
    }

    public void setBrushSize(float newSize){
    //update size
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;

        //drawPaint.setStrokeWidth(brushSize);
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize=lastSize;
    }
    public float getLastBrushSize(){
        return lastBrushSize;
    }


    private void setupDrawing(){

        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        //drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;
        drawPaint.setStrokeWidth(brushSize);
//get drawing area setup for interaction
    }
    public void setColor(String newColor){
//set color
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }


}
