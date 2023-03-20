package com.example.model;

import com.example.model.dto.LocationRegionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="location_region")
public class LocationRegion {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="province_id")
    private String provinceId;
    @Column(name="province_name")
    private String provinceName;
    @Column(name="district_id")
    private String districtId;
    @Column(name="district_name")
    private String districtName;
    @Column(name="ward_id")
    private String wardId;
    @Column(name="ward_name")
    private String wardName;
    @Column(name="address")
    private  String address;

//    @OneToMany
//    private List<Customer> customers;

    public LocationRegionDTO toLocationRegionDTO(){
        return new LocationRegionDTO()
                .setId(id)
                .setProvinceId(provinceId)
                .setProvinceName(provinceName)
                .setDistrictId(districtId)
                .setDistrictName(districtName)
                .setWardId(wardId)
                .setWardName(wardName)
                .setAddress(address)
        ;
    }
}
