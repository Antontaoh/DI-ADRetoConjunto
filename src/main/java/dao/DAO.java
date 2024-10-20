package dao;

import java.util.List;

/**
 * DAO (Data Access Object) interface providing CRUD operations for any generic entity type.
 * This interface defines methods for interacting with a data source, allowing
 * the retrieval, insertion, updating, and deletion of objects.
 *
 * @param <T> the type of entity the DAO will manage
 */
public interface DAO<T> {

    /**
     * Retrieves all records of type T from the data source.
     *
     * @return a List of all objects of type T available in the data source
     */
    public List<T> getAll();

    /**
     * Retrieves a specific object of type T by its ID.
     *
     * @param id the ID of the object to retrieve
     * @return the object of type T that matches the provided ID
     */
    public T getById(int id);

    /**
     * Adds a new object of type T to the data source.
     *
     * @param t the object to be added to the data source
     */
    public void add(T t);

    /**
     * Updates an existing object of type T in the data source.
     *
     * @param t the object with updated data to be saved to the data source
     */
    public void update(T t);

    /**
     * Deletes an existing object of type T from the data source.
     *
     * @param t the object to be deleted from the data source
     */
    public void delete(T t);
}
