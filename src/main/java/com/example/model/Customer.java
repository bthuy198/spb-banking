package com.example.model;

import com.example.model.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors (chain = true)
@Entity
@Table(name = "customers")
//@SQLDelete(sql = "UPDATE banking.customers SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
//@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "full_name", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", message = "Full name is not valid")
    private String fullName;
    @Column(name = "email", nullable = false)
    @Pattern(regexp="^[\\w]+@([\\w-]+\\.)+[\\w-]{2,6}$", message = "Email address is not valid")
    private String email;
    @Column(name = "phone", nullable = false)
//    @Pattern(regexp = "^0[3798][0-9]{8}", message = "Phone number is not valid")
    private String phone;
    @Column(precision = 10, scale = 0, nullable = false, columnDefinition = "decimal(12,0) default 0")
    private BigDecimal balance;

    @OneToMany(targetEntity = Deposit.class)
    private List<Deposit> deposits;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> senders;

    @OneToMany(targetEntity = Transfer.class)
    private List<Transfer> recipients;

    @ManyToOne()
    @JoinColumn(name="location_region_id", referencedColumnName = "id", nullable = false)
    private LocationRegion locationRegion;

    public CustomerDTO toCustomerDTO(){
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setPhone(phone)
                .setEmail(email)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }
}
