package model;

import lombok.Data;

import java.io.Serializable;

/**
 * The CopyFilm class represents a copy of a film in the system.
 * This class contains information about a specific film copy, including its condition, support type,
 * and references to the associated film and user.
 * It uses Lombok's {@code @Data} annotation to automatically generate boilerplate code like getters,
 * setters, {@code equals()}, {@code hashCode()}, and {@code toString()} methods.
 * Implements {@code Serializable} for potential use in serialization.
 */
@Data
public class CopyFilm implements Serializable {

    /**
     * The unique identifier of the copy.
     */
    private int id;

    /**
     * The unique identifier of the film associated with this copy.
     */
    private int FilmId;

    /**
     * The unique identifier of the user who owns or manages this copy.
     */
    private int UserId;

    /**
     * The condition of the film copy (e.g., "New", "Used", "Damaged").
     */
    private String Condition;

    /**
     * The support type of the film copy (e.g., "DVD", "Blu-ray", "Digital").
     */
    private String Support;

    /**
     * The user associated with this film copy. This is a reference to the {@link User} object.
     */
    private User user;

    /**
     * The film associated with this film copy. This is a reference to the {@link Film} object.
     */
    private Film film;
}

