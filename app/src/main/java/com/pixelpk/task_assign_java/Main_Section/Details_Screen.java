package com.pixelpk.task_assign_java.Main_Section;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pixelpk.task_assign_java.R;

public class Details_Screen extends AppCompatActivity
{

    //TextView to show details
    TextView date,title,caption,url;

    //Strings to get details from
    String detail_str,img_str,title_str,date_str,url_str;

    //back button and image for article
    ImageView img_view,back_btn;

    //Caption Layout
    LinearLayout layout_whole_caption;

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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              finish();
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

        if(detail_str.equals(""))
        {
            layout_whole_caption.setVisibility(View.GONE);
        }
        else
        {
            layout_whole_caption.setVisibility(View.VISIBLE);
        }

        date.setText(date_str);
        title.setText(title_str);
        caption.setText(detail_str);
        url.setText(url_str);

    }

    private void intialize_view()
    {
        //Getting Data from Activity
        detail_str = getIntent().getStringExtra("intent_article_details");
        img_str    = getIntent().getStringExtra("intent_article_img");
        title_str  = getIntent().getStringExtra("intent_article_title");
        date_str   = getIntent().getStringExtra("intent_article_date");
        url_str    = getIntent().getStringExtra("intent_article_url");

        //Initializing the Textviews and Imageviews
        date = findViewById(R.id.date_published_detail);
        title = findViewById(R.id.title_detail);
        caption = findViewById(R.id.caption_detail);
        url = findViewById(R.id.url_detail);
        img_view = findViewById(R.id.img_article);
        back_btn = findViewById(R.id.back_btn);
        layout_whole_caption = findViewById(R.id.layout_whole_caption);
    }
}