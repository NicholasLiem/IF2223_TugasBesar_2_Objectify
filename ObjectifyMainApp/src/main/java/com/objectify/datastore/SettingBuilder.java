package com.objectify.datastore;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.objectify.models.entities.Customer;
import com.objectify.models.entities.Member;
import com.objectify.models.entities.VIP;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Member.class, name = "Member"),
        @JsonSubTypes.Type(value = VIP.class, name = "VIP"),
        @JsonSubTypes.Type(value = Customer.class, name = "Customer")
})
public abstract class SettingBuilder<T> {

    protected String name;
    protected String label;

    public SettingBuilder(String name, String label) {
        this.name = name;
        this.label = label;
    }
    
    public abstract T build();

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }
    
    // TODO: event handler
}
