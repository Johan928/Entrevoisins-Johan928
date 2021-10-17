package com.openclassrooms.entrevoisins.events;

import android.content.Intent;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Event fired when a user deletes a Neighbour
 */
public class UserChangedTab {

    /**
     * Neighbour to delete
     */

    public Integer selectedTab;

    /**
     * Constructor.
     * @param selectedTab
     */
    public UserChangedTab(Integer selectedTab) {

        this.selectedTab = selectedTab;
    }
}
