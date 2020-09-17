package com.example.shoppinglist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShoppingListActivity extends AppCompatActivity {

    //동적으로 TextView를 만들어 LinearLayout에 붙여 보자
    TextView []tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFF5A9A9));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final LinearLayout ll = (LinearLayout) findViewById(R.id.parent);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        for(int i=1; i<=10; i++) {

            DatabaseReference shoppinglistRef = rootRef.child("shoppinglist"+i);

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    tv = new TextView[(int) dataSnapshot.getChildrenCount()];

                    int i = 0;

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String product_name = ds.child("product_name").getValue(String.class);
                        String product_number = ds.child("product_number").getValue().toString();
                        String product_type = ds.child("product_type").getValue(String.class);

                        //메뉴 카테고리
                        if(product_type.equals("a")){
                            product_type = "가전제품";
                        }else if(product_type.equals("b")){
                            product_type = "욕실용품";
                        }else if(product_type.equals("c")){
                            product_type = "옷";
                        }else if(product_type.equals("f")){
                            product_type = "음식";
                        }else if(product_type.equals("u")){
                            product_type = "가구";
                        }

                        // LinearLayout 생성
                        LinearLayout lm = new LinearLayout(ShoppingListActivity.this);
                        lm.setOrientation(LinearLayout.HORIZONTAL);
                        lm.setGravity(Gravity.CENTER);

                        tv[i] = new TextView(ShoppingListActivity.this);
                        tv[i].setText(product_name + " / " + product_type);
                        tv[i].setTextSize(20);
                        tv[i].setTextColor(Color.DKGRAY);

                        lm.addView(tv[i]);

                        //체크박스
                        final CheckBox cb = new CheckBox(ShoppingListActivity.this);

                        cb.setId(i);
                        cb.setChecked(false);
                        cb.setLayoutParams(params);

                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            }
                        });

                        lm.addView(cb);
                        ll.addView(lm);

                        Log.d("TAG", product_name + " / " + product_number + " / " + product_type);
                        i++;
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };

            shoppinglistRef.addListenerForSingleValueEvent(eventListener);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shoppinglist,menu);
        return true;
    }

    //액션버튼을 클릭하면
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        //메뉴로 돌아감
        if(id == R.id.gomenu){
            Intent intent = new Intent(this, MenuChoiceActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }



}
