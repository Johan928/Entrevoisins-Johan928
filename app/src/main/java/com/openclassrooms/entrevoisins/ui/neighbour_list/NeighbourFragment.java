package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.*;
import com.openclassrooms.entrevoisins.events.OpenDetailsEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;




public class NeighbourFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private List<Neighbour> mFavoriteNeighboursList = new ArrayList<>();
    private Integer mSelectedTab = 0;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance() {
        NeighbourFragment fragment = new NeighbourFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Init the List of neighbours
     */
    private void initList(Integer selectedTab) {

        switch (selectedTab){
            case 1 :
               listFavorite();
                mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavoriteNeighboursList));
                break;
            default :
                mNeighbours = mApiService.getNeighbours();
                mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
        }
    }

    public void listFavorite() {

        mFavoriteNeighboursList.clear();

        for (Neighbour neighbour : mNeighbours) {
            if (neighbour.getIsFavorite() == true) {
                mFavoriteNeighboursList.add(neighbour);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initList(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        /** We verify which tabitem is selected **/
        switch (mSelectedTab) {

            case 1: /** tabitem FAVORITES is selected**/

                /** we set favorite to false for the concerned neighbour **/
               for (Neighbour neighbour : mNeighbours){
             if (neighbour==event.neighbour){
                 neighbour.setFavorite(false);
             }
            }
                break;
            default : /** means the selected tab is not favorites, we use the apiservice method to delete a neighbour **/
                mApiService.deleteNeighbour(event.neighbour);
        }
        /** we update the recyclerview depending on which tabitem is selected **/
                initList(mSelectedTab);
    }
    @Subscribe
    public void onListFavoriteSelected(UserChangedTab event){
             mSelectedTab = event.selectedTab;
              initList(event.selectedTab);
    }
    @Subscribe
    public void onOpenDetailsEvent(OpenDetailsEvent event) {
         Intent intent = new Intent(this.getContext(),DetailsNeighbourActivity.class).putExtra("neighbour",event.Neighbour);
        startActivity(intent);
    }


}
