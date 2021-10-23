package com.openclassrooms.entrevoisins.service;

import android.widget.Toast;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.sql.Struct;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private final List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }


    /**
     * {@inheritDoc}
     *
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void addOrDeleteFavorite(Neighbour neighbour,Boolean favorite) {
        for (Neighbour nbr : neighbours) {
            if (nbr.getId() == neighbour.getId()) {

               nbr.setFavorite(favorite);

                }

            }
        }

}




