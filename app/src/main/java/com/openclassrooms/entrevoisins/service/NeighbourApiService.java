package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     *

     */
    void deleteNeighbour(Neighbour neighbour);


    /**
     * Create a neighbour
     *

     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Set a neighbour favorite attribute to true or false
     */
    void addOrDeleteFavorite(Neighbour neighbour,Boolean favorite);

}
