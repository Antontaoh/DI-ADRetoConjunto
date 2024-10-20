package model;

import lombok.Data;

import java.io.Serializable;

/**
 * The Film class represents a film in the system.
 * This class contains information about a specific film, including its title, genre, year of release,
 * description, and director.
 * It uses Lombok's {@code @Data} annotation to automatically generate boilerplate code like getters,
 * setters, {@code equals()}, {@code hashCode()}, and {@code toString()} methods.
 * Implements {@code Serializable} for potential use in serialization.
 */
@Data
public class Film implements Serializable {

    /**
     * The unique identifier of the film.
     */
    private int id;

    /**
     * The title of the film.
     */
    private String title;

    /**
     * The genre of the film (e.g., "Action", "Comedy", "Drama").
     */
    private String genre;

    /**
     * The year the film was released.
     */
    private int year;

    /**
     * A brief description of the film's plot or content.
     */
    private String description;

    /**
     * The name of the director of the film.
     */
    private String director;
}

