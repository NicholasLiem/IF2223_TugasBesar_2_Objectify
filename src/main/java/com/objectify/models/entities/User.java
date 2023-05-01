package com.objectify.models.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.objectify.models.transactions.TransactionHistory;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Member.class, name = "Member"),
        @JsonSubTypes.Type(value = VIP.class, name = "VIP"),
        @JsonSubTypes.Type(value = Customer.class, name = "Customer")
})
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Member.class, VIP.class, Customer.class})
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class User implements Serializable {
    private static final long serialVersionUID = 7695914912555037086L;
    
    @Getter
    protected int userID;
    @Getter @Setter
    protected boolean activationStatus;
    @XmlElement(name = "UserTransactionHistory")
    @Getter @Setter
    protected TransactionHistory transactionHistory;
}
