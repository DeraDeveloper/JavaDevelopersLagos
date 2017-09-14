package com.example.seamfix.javadeveloperslagos.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.seamfix.javadeveloperslagos.R;
import com.squareup.picasso.Picasso;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Url;

/**
 * Created by SEAMFIX on 9/12/2017.
 */

public class ProfileActivity extends AppCompatActivity{
    TextView Link, Username;
    Toolbar mActionBarToolbar;
    CircleImageView imageView;

    //sets the profile image, link and username gottten from api into the Profile Activity
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (CircleImageView) findViewById(R.id.userimageheader);
        Username = (TextView) findViewById(R.id.username);
        Link = (TextView) findViewById(R.id.link);
        String username = getIntent().getExtras().getString("login");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        String link  =  getIntent().getExtras().getString("html_url");

        Link.setText(link);
        Linkify.addLinks(Link, Linkify.WEB_URLS);

        Username.setText(username);

        Picasso.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.loading)
                .into(imageView);

        getSupportActionBar().setTitle("Profile Activity");
    }

    //intent for sharing the user's details
    private Intent createShareForcstIntent(){
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("html_url");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer @ " + username + ", " + Uri.parse(link))
                .getIntent();
        return shareIntent;
    }

    //inflating the menu item and calling the share intent
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.profile, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForcstIntent());
        return true;
    }
}
