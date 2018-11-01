package com.example.shoppinglist;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //irebase
    FirebaseDatabase database;
    DatabaseReference myRef;

    //服
    private ImageView c1;
    private ImageView c2;
    private ImageView c3;
    private ImageView c4;
    private ImageView c5;
    private ImageView c6;

    //食べ物
    private ImageView f1;
    private ImageView f2;
    private ImageView f3;
    private ImageView f4;
    private ImageView f5;
    private ImageView f6;

    //家具
    private ImageView fu1;
    private ImageView fu2;
    private ImageView fu3;
    private ImageView fu4;
    private ImageView fu5;
    private ImageView fu6;

    //家電製品
    private ImageView a1;
    private ImageView a2;
    private ImageView a3;
    private ImageView a4;
    private ImageView a5;
    private ImageView a6;

    //バスルームの物
    private ImageView b1;
    private ImageView b2;
    private ImageView b3;
    private ImageView b4;
    private ImageView b5;
    private ImageView b6;

    ArrayList<ShoppingList> sList= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFindViewById();

        setImageLongClickListener();

        //初期にすべてのイメージを見せないようにしておく
        setImageInvisible();


        findViewById(R.id.toplinear).setOnDragListener(new DragListener());
        findViewById(R.id.bottomlinear).setOnDragListener(new DragListener());

        //アクションバーのタイトル変更
        getSupportActionBar().setTitle("");
        //アクションバーの背景の色を変更
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFF5A9A9));
        //ホームボタンを表示
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        int i = (int) (Math.random() * 10) + 1;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shoppinglist"+i);

    }

    private final class LongClickListener implements OnLongClickListener {

        public boolean onLongClick(View view) {

            // タグを作る
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

            String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(data,
                    shadowBuilder,
                    view,
                    0
            );

            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    class DragListener implements OnDragListener {

        public boolean onDrag(View v, DragEvent event) {

            // イベントを始まる
            switch (event.getAction()) {

                // イメージのドラッグが始まる場合
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                    break;

                // ドラッグしたイメージが入る場合
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("DragClickListener", "ACTION_DRAG_ENTERED");

                    break;

                // ドラッグしたイメージが外れた場合
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("DragClickListener", "ACTION_DRAG_EXITED");

                    break;

                // イメージをドラッグしてドロップする場合
                case DragEvent.ACTION_DROP:
                    Log.d("DragClickListener", "ACTION_DROP");

                    if (v == findViewById(R.id.bottomlinear)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);

                        Toast.makeText(MainActivity.this,"담겼습니다",Toast.LENGTH_SHORT).show();

                        LinearLayout containView = (LinearLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);


                    }else if (v == findViewById(R.id.toplinear)) {
                        View view = (View) event.getLocalState();
                        ViewGroup viewgroup = (ViewGroup) view.getParent();
                        viewgroup.removeView(view);

                        LinearLayout containView = (LinearLayout) v;
                        containView.addView(view);
                        view.setVisibility(View.VISIBLE);

                    }else {
                        View view = (View) event.getLocalState();
                        view.setVisibility(View.VISIBLE);
                        Context context = getApplicationContext();
                        Toast.makeText(context,
                                "이미지를 다른 지역에 드랍할수 없습니다.",
                                Toast.LENGTH_LONG).show();
                        break;
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:

                    Log.d("DragClickListener", "ACTION_DRAG_ENDED");

                    View view = (View) event.getLocalState();
                    ViewGroup viewgroup = (ViewGroup) view.getParent();
                    viewgroup.removeView(view);

                    LinearLayout containView = (LinearLayout) v;
                    containView.addView(view);
                    view.setVisibility(View.VISIBLE);

                    ImageView iv = (ImageView) view;

                    String product_name=iv.getTag().toString().substring(3,6);
                    String product_type=iv.getTag().toString().substring(0,1);

                    ShoppingList sl = new ShoppingList(
                    1,product_name,product_type,1);

                    sList.add(sl);

                default:
                    break;
            }
            return true;
        }
    }

    public void setFindViewById(){
        //服
        c1 = (ImageView) findViewById(R.id.c1);
        c2 = (ImageView) findViewById(R.id.c2);
        c3 = (ImageView) findViewById(R.id.c3);
        c4 = (ImageView) findViewById(R.id.c4);
        c5 = (ImageView) findViewById(R.id.c5);
        c6 = (ImageView) findViewById(R.id.c6);

        //食べ物
        f1 = (ImageView) findViewById(R.id.f1);
        f2 = (ImageView) findViewById(R.id.f2);
        f3 = (ImageView) findViewById(R.id.f3);
        f4 = (ImageView) findViewById(R.id.f4);
        f5 = (ImageView) findViewById(R.id.f5);
        f6 = (ImageView) findViewById(R.id.f6);

        //家具
        fu1 = (ImageView) findViewById(R.id.fu1);
        fu2 = (ImageView) findViewById(R.id.fu2);
        fu3 = (ImageView) findViewById(R.id.fu3);
        fu4 = (ImageView) findViewById(R.id.fu4);
        fu5 = (ImageView) findViewById(R.id.fu5);
        fu6 = (ImageView) findViewById(R.id.fu6);

        //家電製品
        a1 = (ImageView) findViewById(R.id.a1);
        a2 = (ImageView) findViewById(R.id.a2);
        a3 = (ImageView) findViewById(R.id.a3);
        a4 = (ImageView) findViewById(R.id.a4);
        a5 = (ImageView) findViewById(R.id.a5);
        a6 = (ImageView) findViewById(R.id.a6);

        //バスルームの物
        b1 = (ImageView) findViewById(R.id.b1);
        b2 = (ImageView) findViewById(R.id.b2);
        b3 = (ImageView) findViewById(R.id.b3);
        b4 = (ImageView) findViewById(R.id.b4);
        b5 = (ImageView) findViewById(R.id.b5);
        b6 = (ImageView) findViewById(R.id.b6);
    }

    public void setImageLongClickListener(){
        c1.setOnLongClickListener(new LongClickListener());
        c2.setOnLongClickListener(new LongClickListener());
        c3.setOnLongClickListener(new LongClickListener());
        c4.setOnLongClickListener(new LongClickListener());
        c5.setOnLongClickListener(new LongClickListener());
        c6.setOnLongClickListener(new LongClickListener());

        f1.setOnLongClickListener(new LongClickListener());
        f2.setOnLongClickListener(new LongClickListener());
        f3.setOnLongClickListener(new LongClickListener());
        f4.setOnLongClickListener(new LongClickListener());
        f5.setOnLongClickListener(new LongClickListener());
        f6.setOnLongClickListener(new LongClickListener());

        fu1.setOnLongClickListener(new LongClickListener());
        fu2.setOnLongClickListener(new LongClickListener());
        fu3.setOnLongClickListener(new LongClickListener());
        fu4.setOnLongClickListener(new LongClickListener());
        fu5.setOnLongClickListener(new LongClickListener());
        fu6.setOnLongClickListener(new LongClickListener());

        a1.setOnLongClickListener(new LongClickListener());
        a2.setOnLongClickListener(new LongClickListener());
        a3.setOnLongClickListener(new LongClickListener());
        a4.setOnLongClickListener(new LongClickListener());
        a5.setOnLongClickListener(new LongClickListener());
        a6.setOnLongClickListener(new LongClickListener());

        b1.setOnLongClickListener(new LongClickListener());
        b2.setOnLongClickListener(new LongClickListener());
        b3.setOnLongClickListener(new LongClickListener());
        b4.setOnLongClickListener(new LongClickListener());
        b5.setOnLongClickListener(new LongClickListener());
        b6.setOnLongClickListener(new LongClickListener());
    }

    public void getDrawableImage(){

        c1.setImageResource(R.drawable.c1);
        c2.setImageResource(R.drawable.c2);
        c3.setImageResource(R.drawable.c3);
        c4.setImageResource(R.drawable.c4);
        c5.setImageResource(R.drawable.c5);
        c6.setImageResource(R.drawable.c6);
        f1.setImageResource(R.drawable.f1);
        f2.setImageResource(R.drawable.f2);
        f3.setImageResource(R.drawable.f3);
        f4.setImageResource(R.drawable.f4);
        f5.setImageResource(R.drawable.f5);
        f6.setImageResource(R.drawable.f6);
        fu1.setImageResource(R.drawable.fu1);
        fu2.setImageResource(R.drawable.fu2);
        fu3.setImageResource(R.drawable.fu3);
        fu4.setImageResource(R.drawable.fu4);
        fu5.setImageResource(R.drawable.fu5);
        fu6.setImageResource(R.drawable.fu6);
        a1.setImageResource(R.drawable.a1);
        a2.setImageResource(R.drawable.a2);
        a3.setImageResource(R.drawable.a3);
        a4.setImageResource(R.drawable.a4);
        a5.setImageResource(R.drawable.a5);
        a6.setImageResource(R.drawable.a6);
        b1.setImageResource(R.drawable.b1);
        b2.setImageResource(R.drawable.b2);
        b3.setImageResource(R.drawable.b3);
        b4.setImageResource(R.drawable.b4);
        b5.setImageResource(R.drawable.b5);
        b6.setImageResource(R.drawable.b6);
    }

    public void setImageInvisible(){
        c1.setVisibility(View.INVISIBLE);
        c2.setVisibility(View.INVISIBLE);
        c3.setVisibility(View.INVISIBLE);
        c4.setVisibility(View.INVISIBLE);
        c5.setVisibility(View.INVISIBLE);
        c6.setVisibility(View.INVISIBLE);

        f1.setVisibility(View.INVISIBLE);
        f2.setVisibility(View.INVISIBLE);
        f3.setVisibility(View.INVISIBLE);
        f4.setVisibility(View.INVISIBLE);
        f5.setVisibility(View.INVISIBLE);
        f6.setVisibility(View.INVISIBLE);

        fu1.setVisibility(View.INVISIBLE);
        fu2.setVisibility(View.INVISIBLE);
        fu3.setVisibility(View.INVISIBLE);
        fu4.setVisibility(View.INVISIBLE);
        fu5.setVisibility(View.INVISIBLE);
        fu6.setVisibility(View.INVISIBLE);

        a1.setVisibility(View.INVISIBLE);
        a2.setVisibility(View.INVISIBLE);
        a3.setVisibility(View.INVISIBLE);
        a4.setVisibility(View.INVISIBLE);
        a5.setVisibility(View.INVISIBLE);
        a6.setVisibility(View.INVISIBLE);

        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        b5.setVisibility(View.INVISIBLE);
        b6.setVisibility(View.INVISIBLE);
    }

    //アクションボタンメニューをアクションバーに入れる
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    //アクションボタンをクリックするとカテゴリ別のイメージを見れる
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //drawableのイメージを持って来る
        getDrawableImage();

        int id = item.getItemId();

        if(id == R.id.save){

            if(sList.isEmpty()){
                AlertDialog.Builder d2 = new AlertDialog.Builder(this);

                d2.setMessage("장바구니에 담아주세요!");
                d2.setCancelable(false);
                d2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                d2.show();
                return true;
            }

            //リストに重複値があれば一つを削除
            for(int i=0; i<sList.size(); i++){
                for(int j=0; j<sList.size(); j++) {
                    if (sList.get(i).getProduct_name().equals(sList.get(j).getProduct_name())){
                        sList.remove(i);
                    }
                }
            }

            myRef.setValue(sList);

            AlertDialog.Builder d2 = new AlertDialog.Builder(this);
            d2.setMessage("장바구니담기 성공!");
            d2.setCancelable(false);
            d2.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //アクティビティを転換
                    Intent it = new Intent(MainActivity.this, ShoppingListActivity.class);
                    startActivity(it);
                }
            });
            d2.show();

            return true;
        }

        //服のカテゴリ
        if (id == R.id.clothes) {

            c1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c4.setVisibility(View.VISIBLE);
            c5.setVisibility(View.VISIBLE);
            c6.setVisibility(View.VISIBLE);

            f1.setImageResource(android.R.color.transparent);
            f2.setImageResource(android.R.color.transparent);
            f3.setImageResource(android.R.color.transparent);
            f4.setImageResource(android.R.color.transparent);
            f5.setImageResource(android.R.color.transparent);
            f6.setImageResource(android.R.color.transparent);

            fu1.setImageResource(android.R.color.transparent);
            fu2.setImageResource(android.R.color.transparent);
            fu3.setImageResource(android.R.color.transparent);
            fu4.setImageResource(android.R.color.transparent);
            fu5.setImageResource(android.R.color.transparent);
            fu6.setImageResource(android.R.color.transparent);

            a1.setImageResource(android.R.color.transparent);
            a2.setImageResource(android.R.color.transparent);
            a3.setImageResource(android.R.color.transparent);
            a4.setImageResource(android.R.color.transparent);
            a5.setImageResource(android.R.color.transparent);
            a6.setImageResource(android.R.color.transparent);

            b1.setImageResource(android.R.color.transparent);
            b2.setImageResource(android.R.color.transparent);
            b3.setImageResource(android.R.color.transparent);
            b4.setImageResource(android.R.color.transparent);
            b5.setImageResource(android.R.color.transparent);
            b6.setImageResource(android.R.color.transparent);

            return true;
        }

        //食べ物のカテゴリ
        if (id == R.id.food) {

            fu1.setImageResource(android.R.color.transparent);
            fu2.setImageResource(android.R.color.transparent);
            fu3.setImageResource(android.R.color.transparent);
            fu4.setImageResource(android.R.color.transparent);
            fu5.setImageResource(android.R.color.transparent);
            fu6.setImageResource(android.R.color.transparent);

            a1.setImageResource(android.R.color.transparent);
            a2.setImageResource(android.R.color.transparent);
            a3.setImageResource(android.R.color.transparent);
            a4.setImageResource(android.R.color.transparent);
            a5.setImageResource(android.R.color.transparent);
            a6.setImageResource(android.R.color.transparent);

            b1.setImageResource(android.R.color.transparent);
            b2.setImageResource(android.R.color.transparent);
            b3.setImageResource(android.R.color.transparent);
            b4.setImageResource(android.R.color.transparent);
            b5.setImageResource(android.R.color.transparent);
            b6.setImageResource(android.R.color.transparent);

            c1.setImageResource(android.R.color.transparent);
            c2.setImageResource(android.R.color.transparent);
            c3.setImageResource(android.R.color.transparent);
            c4.setImageResource(android.R.color.transparent);
            c5.setImageResource(android.R.color.transparent);
            c6.setImageResource(android.R.color.transparent);

            f1.setVisibility(View.VISIBLE);
            f2.setVisibility(View.VISIBLE);
            f3.setVisibility(View.VISIBLE);
            f4.setVisibility(View.VISIBLE);
            f5.setVisibility(View.VISIBLE);
            f6.setVisibility(View.VISIBLE);

            return true;
        }

        //家具のカテゴリ
        if (id == R.id.furniture){
            fu1.setVisibility(View.VISIBLE);
            fu2.setVisibility(View.VISIBLE);
            fu3.setVisibility(View.VISIBLE);
            fu4.setVisibility(View.VISIBLE);
            fu5.setVisibility(View.VISIBLE);
            fu6.setVisibility(View.VISIBLE);

            a1.setImageResource(android.R.color.transparent);
            a2.setImageResource(android.R.color.transparent);
            a3.setImageResource(android.R.color.transparent);
            a4.setImageResource(android.R.color.transparent);
            a5.setImageResource(android.R.color.transparent);
            a6.setImageResource(android.R.color.transparent);

            b1.setImageResource(android.R.color.transparent);
            b2.setImageResource(android.R.color.transparent);
            b3.setImageResource(android.R.color.transparent);
            b4.setImageResource(android.R.color.transparent);
            b5.setImageResource(android.R.color.transparent);
            b6.setImageResource(android.R.color.transparent);

            c1.setImageResource(android.R.color.transparent);
            c2.setImageResource(android.R.color.transparent);
            c3.setImageResource(android.R.color.transparent);
            c4.setImageResource(android.R.color.transparent);
            c5.setImageResource(android.R.color.transparent);
            c6.setImageResource(android.R.color.transparent);

            f1.setImageResource(android.R.color.transparent);
            f2.setImageResource(android.R.color.transparent);
            f3.setImageResource(android.R.color.transparent);
            f4.setImageResource(android.R.color.transparent);
            f5.setImageResource(android.R.color.transparent);
            f6.setImageResource(android.R.color.transparent);

            return true;
        }

        //家電製品のカテゴリ
        if (id == R.id.appliance){
            a1.setVisibility(View.VISIBLE);
            a2.setVisibility(View.VISIBLE);
            a3.setVisibility(View.VISIBLE);
            a4.setVisibility(View.VISIBLE);
            a5.setVisibility(View.VISIBLE);
            a6.setVisibility(View.VISIBLE);

            fu1.setImageResource(android.R.color.transparent);
            fu2.setImageResource(android.R.color.transparent);
            fu3.setImageResource(android.R.color.transparent);
            fu4.setImageResource(android.R.color.transparent);
            fu5.setImageResource(android.R.color.transparent);
            fu6.setImageResource(android.R.color.transparent);

            b1.setImageResource(android.R.color.transparent);
            b2.setImageResource(android.R.color.transparent);
            b3.setImageResource(android.R.color.transparent);
            b4.setImageResource(android.R.color.transparent);
            b5.setImageResource(android.R.color.transparent);
            b6.setImageResource(android.R.color.transparent);

            c1.setImageResource(android.R.color.transparent);
            c2.setImageResource(android.R.color.transparent);
            c3.setImageResource(android.R.color.transparent);
            c4.setImageResource(android.R.color.transparent);
            c5.setImageResource(android.R.color.transparent);
            c6.setImageResource(android.R.color.transparent);

            f1.setImageResource(android.R.color.transparent);
            f2.setImageResource(android.R.color.transparent);
            f3.setImageResource(android.R.color.transparent);
            f4.setImageResource(android.R.color.transparent);
            f5.setImageResource(android.R.color.transparent);
            f6.setImageResource(android.R.color.transparent);

            return true;
        }

        //バスルームの物のカテゴリ
        if (id == R.id.bath){
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);
            b4.setVisibility(View.VISIBLE);
            b5.setVisibility(View.VISIBLE);
            b6.setVisibility(View.VISIBLE);

            fu1.setImageResource(android.R.color.transparent);
            fu2.setImageResource(android.R.color.transparent);
            fu3.setImageResource(android.R.color.transparent);
            fu4.setImageResource(android.R.color.transparent);
            fu5.setImageResource(android.R.color.transparent);
            fu6.setImageResource(android.R.color.transparent);

            a1.setImageResource(android.R.color.transparent);
            a2.setImageResource(android.R.color.transparent);
            a3.setImageResource(android.R.color.transparent);
            a4.setImageResource(android.R.color.transparent);
            a5.setImageResource(android.R.color.transparent);
            a6.setImageResource(android.R.color.transparent);

            c1.setImageResource(android.R.color.transparent);
            c2.setImageResource(android.R.color.transparent);
            c3.setImageResource(android.R.color.transparent);
            c4.setImageResource(android.R.color.transparent);
            c5.setImageResource(android.R.color.transparent);
            c6.setImageResource(android.R.color.transparent);

            f1.setImageResource(android.R.color.transparent);
            f2.setImageResource(android.R.color.transparent);
            f3.setImageResource(android.R.color.transparent);
            f4.setImageResource(android.R.color.transparent);
            f5.setImageResource(android.R.color.transparent);
            f6.setImageResource(android.R.color.transparent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
