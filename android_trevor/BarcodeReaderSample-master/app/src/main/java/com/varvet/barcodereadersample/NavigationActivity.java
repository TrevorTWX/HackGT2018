package com.varvet.barcodereadersample;

import android.graphics.Bitmap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class NavigationActivity extends AppCompatActivity {

    private ImageView map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("nav_value");
        String[] new_splitted = message.split("/");
        String submap_name = new_splitted[0];
        int start_point = Integer.parseInt(new_splitted[1]);
        int end_point = Integer.parseInt(new_splitted[2]);
        String url = "http://35.237.145.71/highlightpath?map=" + submap_name + "&start=" + start_point + "&end=" + end_point + "&attach=true";
        map = findViewById(R.id.imageView);

        RequestQueue rq = Volley.newRequestQueue(this);
        ImageRequest ir = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        map.setImageBitmap(response);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error ["+error+"]");
                        // handle the error here
                    }
                });
        rq.add(ir);

        map.setOnTouchListener(new TouchListener());


    }
    private final class TouchListener implements OnTouchListener{
        private PointF startPoint= new PointF();//PointF(浮点对)
        private Matrix matrix=new Matrix();//矩阵对象
        private Matrix currentMatrix=new Matrix();//存放照片当前的矩阵
        private int mode=0;//确定是放大还是缩小
        private static final int DRAG=1;//拖拉模式
        private static final int ZOOM=2;//缩放模式
        private float startDis;//开始距离
        private PointF midPoint;//中心点


        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()&MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN://手指下压
                    mode=DRAG;
                    currentMatrix.set(map.getImageMatrix());//记录ImageView当前的移动位置
                    startPoint.set(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE://手指在屏幕移动，改事件会不断被调用
                    if(mode==DRAG){//拖拉模式
                        float dx=event.getX()-startPoint.x;//得到在x轴的移动距离
                        float dy=event.getY()-startPoint.y;//得到在y轴的移动距离
                        matrix.set(currentMatrix);//在没有进行移动之前的位置基础上进行移动
                        matrix.postTranslate(dx, dy);
                    }else if(mode==ZOOM){//缩放模式
                        float endDis=distance(event);//结束距离
                        if(endDis>10f){
                            float scale=endDis/startDis;
                            matrix.set(currentMatrix);
                            matrix.postScale(scale,scale,midPoint.x,midPoint.y);
                        }

                    }

                    break;
                case MotionEvent.ACTION_UP://手指离开屏幕
                case MotionEvent.ACTION_POINTER_UP://当屏幕上已经有手指离开屏幕，屏幕上还有一个手指，就会触发这个事件
                    mode=0;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN://当屏幕上已经有触点(手指)，再有一个手指按下屏幕，就会触发这个事件
                    mode=ZOOM;
                    startDis=distance(event);
                    if(startDis>10f){//防止不规则手指触碰
                        midPoint=mid(event);
                        currentMatrix.set(map.getImageMatrix());//记录ImageView当前的缩放倍数
                    }
                    break;

                default:
                    break;
            }
            //将imageView的矩阵位置改变
           map.setImageMatrix(matrix);
            return true;
        }

    }
    //计算两点之间的距离(勾股定理)
    public float distance(MotionEvent event) {
        float dx=event.getX(1)-event.getX(0);
        float dy=event.getY(1)-event.getY(0);
        return (float)Math.sqrt(dx*dx+dy*dy);
    }

    //计算两个点的中心点
    public static PointF mid(MotionEvent event){
        float midx=(event.getX(1)+event.getX(0))/2;
        float midy=(event.getY(1)+event.getY(0))/2;
        return new PointF(midx,midy);
    }
}


