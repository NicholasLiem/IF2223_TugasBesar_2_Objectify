package com.objectify.datastore.interfaces;

import java.util.Optional;

/**
 * Data store khusus manager. Bisa dipakai untuk kelas lain, tetapi
 * mungkin tidak berfungsi untuk collections
 * 
 * @param <T> non-collection class
 */
public interface DataStore<T> {

    void write(T data);
    
    Optional<T> read();
    
    void delete();
}
