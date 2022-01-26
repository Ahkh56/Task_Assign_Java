package com.pixelpk.task_assign_java.Main_Section;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.pixelpk.task_assign_java.Adapter.Adapter_Article;
import com.pixelpk.task_assign_java.Model.Model_Article;
import com.pixelpk.task_assign_java.R;
import com.pixelpk.task_assign_java.Urls.Main_Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    //Search for Popular Article

    TextInputLayout textInputLayout_search;

    //Recycler for Popular Article
    RecyclerView recyclerView_popular_article;

    //Model Classes (Array List)
    ArrayList<Model_Article> model_articles;
    ArrayList<Model_Article> searched_data_arraylist;


    //Adapter Popular Article
    Adapter_Article adapter_article;

    ProgressDialog progressDialog;

    //Putting Data in String
    String title_str,details_str,img_str,date_str,url_str;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_view();

        Load_data();


        textInputLayout_search.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {

                int iterator = 0;
                searched_data_arraylist.clear();

                if(!model_articles.isEmpty())
                {
                    String searched_data = s.toString();

                    for (Model_Article object: model_articles)
                    {
                        String type = object.getTitle();

                        if(type.contains(searched_data))
                        {
                            searched_data_arraylist.add(model_articles.get(iterator));
                        }

                        iterator++;

                    }

                    adapter_article = new Adapter_Article(searched_data_arraylist, MainActivity.this);
                    adapter_article.notifyDataSetChanged();
                    recyclerView_popular_article.setHasFixedSize(true);
                    recyclerView_popular_article.smoothScrollToPosition(0);
                    recyclerView_popular_article.setAdapter(adapter_article);

                }

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if(s.toString().equals(""))
                {
                    Load_data();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    private void Load_data()
    {
        progressDialog.setMessage("Please Wait while the Articles are being Loaded");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Main_Url.get_popular_article,
                new Response.Listener<String>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(String response)
                    {
                        JSONObject jObject = null;
                        try
                        {
                            jObject = new JSONObject(response);
                            String message     = jObject.getString("status");

                            if(message.equals("OK"))
                            {
                                progressDialog.dismiss();
                                JSONArray array_articles = jObject.getJSONArray("results");

                                for(int i=0; i<array_articles.length(); i++)
                                {
                                    JSONObject jsonObject = array_articles.getJSONObject(i);

                                    title_str = jsonObject.getString("title");
                                    date_str  = jsonObject.getString("published_date");
                                    url_str   = jsonObject.getString("url");

                                    JSONArray array_media = jsonObject.getJSONArray("media");

                                    for(int j=0; j<array_media.length(); j++)
                                    {
                                        JSONObject jsonObject_media = array_media.getJSONObject(j);

                                        details_str   = jsonObject_media.getString("caption");

//                                        Log.e("tag_details",details_str);


                                        JSONArray  array_media_meta = jsonObject_media.getJSONArray("media-metadata");

                                        for(int k=0; k<array_media_meta.length(); k++)
                                        {
                                            JSONObject jsonObject_media_meta = array_media_meta.getJSONObject(k);

                                            String val_format = jsonObject_media_meta.getString("format");


                                            if(val_format.equals("mediumThreeByTwo210"))
                                            {
                                                img_str = jsonObject_media_meta.getString("url");
                                            }

                                        }
                                    }

                                    Model_Article model_article = new Model_Article(img_str,title_str,details_str,date_str,url_str);
                                    model_articles.add(model_article);

//                                    Log.e("tag_detail_title",title_str);
//                                    Log.e("tag_detail_title",img_str);

                                }

                                adapter_article = new Adapter_Article(model_articles, MainActivity.this);
                                adapter_article.notifyDataSetChanged();
                                recyclerView_popular_article.setHasFixedSize(true);
                                recyclerView_popular_article.smoothScrollToPosition(0);
                                recyclerView_popular_article.setAdapter(adapter_article);

                            }
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("tag_details",e.toString());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();

                        Log.e("tag_details_error",error.toString());

                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        {
                            Toast.makeText(getApplicationContext(), "Network Error, Please check internet connection and try again later", Toast.LENGTH_SHORT).show();

                        }
                        else if (error instanceof AuthFailureError)
                        {
                            //TODO
                            Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            //TODO
                            Toast.makeText(getApplicationContext(), "Server Under Maintainence, Please try again later", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError)
                        {
                            //TODO
                            Log.e("tag_details_error",error.toString());


                            Toast.makeText(getApplicationContext(), "Network Error, Please check internet connection and try again", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            //TODO
                            Toast.makeText(getApplicationContext(), "Incorrect Data", Toast.LENGTH_SHORT).show();
                        } }
                }) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();


                return parameters;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void initialize_view()
    {
        progressDialog = new ProgressDialog(this);

        textInputLayout_search = findViewById(R.id.search_article_txtinput);
        recyclerView_popular_article = findViewById(R.id.popular_article_recycler);
        model_articles = new ArrayList<>();
        searched_data_arraylist = new ArrayList<>();
    }

}