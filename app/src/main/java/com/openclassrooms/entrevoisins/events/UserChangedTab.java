package com.openclassrooms.entrevoisins.events;

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
     *
     */
    public UserChangedTab(Integer selectedTab) {

        this.selectedTab = selectedTab;
    }
}
