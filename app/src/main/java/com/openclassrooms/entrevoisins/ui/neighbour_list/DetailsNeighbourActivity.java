package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.detailsImageViewAvatar)
    public ImageView mDetailsImageViewAvatar;
    @BindView(R.id.detailsTextViewUserNameWhite)
    public TextView mDetailsTextViewUserNameWhite;
    @BindView(R.id.detailsTextViewUserNameBlack)
    public TextView mDetailsTextViewUserNameBlack;
    @BindView(R.id.detailsTextViewLocation)
    public TextView mDetailsTextViewLocation;
    @BindView(R.id.detailsTextViewTel)
    public TextView mDetailsTextViewTel;
    @BindView(R.id.detailsTextViewWeb)
    public TextView mDetailsTextViewWeb;
    @BindView(R.id.detailsTextViewAboutMe)
    public TextView mDetailsTextViewAboutMe;
    @BindView(R.id.toolbardetails)
    Toolbar mToolbarDetails;
    private Neighbour neighbour;
    private List<Neighbour> mNeighbours;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);

        ButterKnife.bind(this);

        // setting the toolbar
        setSupportActionBar(mToolbarDetails);
        if (!(getSupportActionBar() == null)) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }



        neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");

        NeighbourApiService apiService = DI.getNeighbourApiService();

        mNeighbours = apiService.getNeighbours();

        Glide.with(mDetailsImageViewAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .into(mDetailsImageViewAvatar);

        mDetailsTextViewUserNameWhite.setText(neighbour.getName());
        mDetailsTextViewUserNameBlack.setText(neighbour.getName());
        mDetailsTextViewLocation.setText(neighbour.getAddress());
        mDetailsTextViewTel.setText(neighbour.getPhoneNumber());
        mDetailsTextViewWeb.setText("www.facebook.com/".concat(neighbour.getName().toLowerCase()));
        mDetailsTextViewAboutMe.setText(neighbour.getAboutMe());
    }

    @OnClick(R.id.floatingActionButtonAddToFavorite)
    void addToFavorite() {
        for (Neighbour nbr : mNeighbours) {
            if (nbr.getId() == neighbour.getId()) {
                if (nbr.getIsFavorite()) {
                    Toast.makeText(this, "Ce neighbour est déjà favori", Toast.LENGTH_SHORT).show();
                } else {
                    DI.getNeighbourApiService().addOrDeleteFavorite(neighbour, true);
                    Toast.makeText(this, "Ce neighbour a été ajouté aux favoris", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }
}