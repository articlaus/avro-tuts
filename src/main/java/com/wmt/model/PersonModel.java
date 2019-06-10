package com.wmt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: Ganbat Bayar
 * On: 6/7/2019
 * Project: avrotest
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonModel {
    String name;
    int favorite_number;
    String favorite_color;
}
