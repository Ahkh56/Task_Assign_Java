package com.pixelpk.task_assign_java.Main_Section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pixelpk.task_assign_java.R;

public class Details_Screen extends AppCompatActivity
{

    TextView date,title,caption,url;
    String detail_str,img_str,title_str,date_str,url_str;
    ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);

        intialize_view();

        Load_data();


        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_str));
                startActivity(browserIntent);
            }
        });

    }

    private void Load_data()
    {
        Glide.with(Details_Screen.this)
                .load(img_str)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(img_view);

        date.setText(date_str);
        title.setText(title_str);
        caption.setText(detail_str);
        url.setText(url_str);

    }

    private void intialize_view()
    {
        detail_str = getIntent().getStringExtra("intent_article_details");
        img_str    = getIntent().getStringExtra("intent_article_img");
        title_str  = getIntent().getStringExtra("intent_article_title");
        date_str   = getIntent().getStringExtra("intent_article_date");
        url_str   = getIntent().getStringExtra("intent_article_url");

        date = findViewById(R.id.date_published_detail);
        title = findViewById(R.id.title_detail);
        caption = findViewById(R.id.caption_detail);
        url = findViewById(R.id.url_detail);
        img_view = findViewById(R.id.img_article);
    }
}