package model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user in the system.
 * This class contains information about a specific user, including their username, password,
 * and a list of film copies associated with the user.
 * It uses Lombok's {@code @Data} annotation to automatically generate boilerplate code like getters,
 * setters, {@code equals()}, {@code hashCode()}, and {@code toString()} methods.
 * Implements {@code Serializable} for potential use in serialization.
 */
@Data
public class User implements Serializable {

    /**
     * The unique identifier of the user.
     */
    private int id;

    /**
     * The username of the user.
     */
    private String userName;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * A list of film copies associated with the user. This is a collection of {@link CopyFilm} objects
     * that the user either owns or manages.
     */
    private List<CopyFilm> films = new ArrayList<CopyFilm>();
}
