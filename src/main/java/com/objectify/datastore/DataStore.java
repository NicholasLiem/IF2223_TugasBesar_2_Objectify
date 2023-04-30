package com.objectify.datastore;

import java.io.IOException;
import java.util.Optional;

/**
 * Data store khusus manager. Bisa dipakai untuk kelas lain, tetapi
 * mungkin tidak berfungsi untuk collections
 * 
 * @param <T> Manager class
 */
public abstract class DataStore<T> {

    /**
     * Data ini akan dipakai untuk method write
     * sehingga diharapkan berisi objek yang alamatnya
     * tidak akan berubah
     */
    protected T data;

    abstract public void write();
    
    abstract public Optional<T> read();
    
    abstract public void delete();

    /**
     * Alamat variabel data memang diharapkan tidak berubah,
     * tetapi jika suatu saat data tersebut harus berubah alamat
     * maka method ini harus dipanggil untuk mengupdate alamat variabel tersebut
     * 
     * @param data variabel baru yang akan dipakai untuk operasi write
     */
    public void setData(T data) {
        this.data = data;
    }
}
