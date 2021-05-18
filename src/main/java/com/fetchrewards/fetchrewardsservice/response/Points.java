package com.fetchrewards.fetchrewardsservice.response;

import lombok.Getter;
import lombok.Setter;

public class Points {
    @Getter
    @Setter
    private int points;

    public Points() {
    }

    public Points(int points) {
        this.points = points;
    }
}
