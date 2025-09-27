package com.obscuria.aquamirae.common.entities;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = TYPE)
public @interface ShipGraveyardEntity {
}
