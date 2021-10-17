package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class OpenDetailsEvent {
    public Neighbour Neighbour;

    public OpenDetailsEvent(Neighbour neighbour) {
        this.Neighbour = neighbour;
    }
}
