package com.wmt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * Created by: Ganbat Bayar
 * On: 6/10/2019
 * Project: avrotest
 */
@Table("person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @PrimaryKey(value = "name")
    String name;
    @Column("favorite_number")
    int favorite_number;
    @Column("favorite_color")
    String favorite_color;
}
