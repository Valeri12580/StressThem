package com.stressthem.app.domain.models.view.weather;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Main {

    private double temp;

    @SerializedName(value = "feels_like")
    private double feelsLike;

    @SerializedName(value = "temp_min")
    private double tempMin;

    @SerializedName(value = "temp_max")
    private double tempMax;


}
