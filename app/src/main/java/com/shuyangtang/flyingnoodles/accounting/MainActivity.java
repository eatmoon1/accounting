package com.shuyangtang.flyingnoodles.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup category;
    private RadioButton clothing;
    private RadioButton food;
    private RadioButton housing;
    private RadioButton transportation;
    private Button save;
    private Button delete;
    private Button check;
    private EditText name;
    private EditText amount;
    private String scategory;
    private MyDBHandler dbHandler;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        dbHandler = new MyDBHandler(this);
    }

    private void initListener() {
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scategory = "food";
                //Toast.makeText(MainActivity.this,scategory, Toast.LENGTH_SHORT).show();
            }
        });
        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scategory = "clothing";
                //Toast.makeText(MainActivity.this,scategory, Toast.LENGTH_SHORT).show();
            }
        });
        housing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scategory = "housing";
                //Toast.makeText(MainActivity.this,scategory, Toast.LENGTH_SHORT).show();
            }
        });
        transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scategory = "transportation";
                //Toast.makeText(MainActivity.this,scategory, Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = name.getText().toString();
                if(TextUtils.isEmpty(sname)){
                Toast.makeText(MainActivity.this,"Oops, you forgot the name.", Toast.LENGTH_SHORT).show();
                return;
                }
                String samount = amount.getText().toString().trim();
                if(TextUtils.isEmpty(samount)){
                    Toast.makeText(MainActivity.this,"Oops, you forgot the name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //To do: save the data to database
                Transaction transaction = new Transaction(scategory, sname, Double.parseDouble(samount));
                dbHandler.addProduct(transaction);
                Intent intent = new Intent(MainActivity.this, PresentActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                amount.setText("");
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PresentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        category = (RadioGroup)this.findViewById(R.id.RadioGroup);
        clothing = (RadioButton)this.findViewById(R.id.radioButton);
        food = (RadioButton)this.findViewById(R.id.radioButton2);
        housing = (RadioButton)this.findViewById(R.id.radioButton3);
        transportation = (RadioButton)this.findViewById(R.id.radioButton4);
        name = (EditText)this.findViewById(R.id.name);
        amount = (EditText)this.findViewById(R.id.amount);
        save = (Button)this.findViewById(R.id.savebtn);
        delete = (Button)this.findViewById(R.id.deletebtn);
        check = (Button)this.findViewById(R.id.check);
        imageView = (ImageView)this.findViewById(R.id.imageView2);
    }
}
