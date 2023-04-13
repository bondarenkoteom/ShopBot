package com.marketplace.database.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChartData {

    private List<Integer> data;

    public ChartData(List<Integer> data) {
        this.data = data;
    }

}
