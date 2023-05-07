package com.objectify.datastore;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.objectify.datastore.enums.CategoryDataStore;
import com.objectify.datastore.enums.UserDataStore;
import com.objectify.datastore.enums.ProductDataStore;
import com.objectify.datastore.enums.TransactionsDataStore;
import com.objectify.datastore.interfaces.BillCalculator;
import com.objectify.datastore.interfaces.DataStore;
import com.objectify.datastore.interfaces.InputControl;
import com.objectify.models.entities.UserManager;
import com.objectify.models.items.CategoryManager;
import com.objectify.models.items.StorageManager;
import com.objectify.models.transactions.TransactionManager;

public class Settings {
    private final String settingsPath = "ObjectifyMainApp/src/resources/settings/";
    private Map<String, Object> additionalProperties;
    private DataStore<StorageManager> productDataStore;
    private DataStore<CategoryManager> categoryDataStore;
    private DataStore<TransactionManager> transactionDataStore;
    private DataStore<UserManager> userDataStore;

    @JsonIgnore
    private final HashMap<String, BillCalculator> calculators;
    
    @JsonIgnore
    private final List<InputControl> components;


    public Settings(){
        this.additionalProperties = new HashMap<>();
        this.components = new ArrayList<>();
        this.calculators = new HashMap<>();
        this.calculators.put("Default", value -> value);
    }

    public void setProductDataStore(String productDataStore){
        Optional<ProductDataStore> optionalDataStore = ProductDataStore.fromName(productDataStore);
        if (optionalDataStore.isPresent()) {
            this.productDataStore = optionalDataStore.get().getDataStore();
        } else {
            throw new IllegalArgumentException("Invalid product data store type: " + productDataStore);
        }
    }

    public void setUserDataStore(String userDataStore){
        Optional<UserDataStore> optionalDataStore = UserDataStore.fromName(userDataStore);
        if (optionalDataStore.isPresent()) {
            this.userDataStore = optionalDataStore.get().getDataStore();
        } else {
            throw new IllegalArgumentException("Invalid product data store type: " + userDataStore);
        }
    }

    public void setCategoryDataStore(String categoryDataStore){
        Optional<CategoryDataStore> optionalDataStore = CategoryDataStore.fromName(categoryDataStore);
        if (optionalDataStore.isPresent()) {
            this.categoryDataStore = optionalDataStore.get().getDataStore();
        } else {
            throw new IllegalArgumentException("Invalid category data store type: " + categoryDataStore);
        }
    }

    public void setTransactionDataStore(String transactionDataStore){
        Optional<TransactionsDataStore> optionalDataStore = TransactionsDataStore.fromName(transactionDataStore);
        if (optionalDataStore.isPresent()) {
            this.transactionDataStore = optionalDataStore.get().getDataStore();
        } else {
            throw new IllegalArgumentException("Invalid transaction data store type: " + transactionDataStore);
        }
    }

    public DataStore<StorageManager> getProductDataStore() {
        return productDataStore;
    }

    @JsonIgnore

    public BillCalculator getCalculator() {
        BillCalculator calculator = calculators.get("Default");
        for (final Map.Entry<String, BillCalculator> entry : calculators.entrySet()) {
            final BillCalculator temp = calculator;
            calculator = value -> entry.getValue().calculate(temp.calculate(value));
        }
        return calculator;
    }

    public HashMap<String, BillCalculator> getCalculators(){
        return this.calculators;
    }

    public void addCalculator(String s, BillCalculator bc){
        this.calculators.put(s, bc);
    }

    public void removeCalculator(String s){
        this.calculators.remove(s);
    }

    public String getSettingsPath(){
        return this.settingsPath;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonIgnore
    public List<InputControl> getComponents() {
        return components;
    }

    public void initialiseDataStores(String type){
        this.setProductDataStore(type);
        this.setUserDataStore(type);
        this.setCategoryDataStore(type);
        this.setTransactionDataStore(type);
    }

    public void loadAllDataStore(){
        StorageManager storageManager = this.productDataStore.read().orElse(SystemPointOfSales.getInstance().getStorageManager());
        SystemPointOfSales.getInstance().setStorageManager(storageManager);

        UserManager userManager = this.userDataStore.read().orElse(SystemPointOfSales.getInstance().getUserManager());
        SystemPointOfSales.getInstance().setUserManager(userManager);

        CategoryManager categoryManager = this.categoryDataStore.read().orElse(SystemPointOfSales.getInstance().getCategoryManager());
        SystemPointOfSales.getInstance().setCategoryManager(categoryManager);

        TransactionManager transactionManager = this.transactionDataStore.read().orElse(SystemPointOfSales.getInstance().getTransactionManager());
        SystemPointOfSales.getInstance().setTransactionManager(transactionManager);
    }


    public void saveAllDataStore(){
        this.productDataStore.write(SystemPointOfSales.getInstance().getStorageManager());
        this.userDataStore.write(SystemPointOfSales.getInstance().getUserManager());
        this.categoryDataStore.write(SystemPointOfSales.getInstance().getCategoryManager());
        this.transactionDataStore.write(SystemPointOfSales.getInstance().getTransactionManager());
    }

}
