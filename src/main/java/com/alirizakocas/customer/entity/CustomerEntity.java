package com.alirizakocas.customer.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@Table(schema = "qnbeyond", name = "customer")
@ApiModel(value = "CustomerEntity model documentation", description = "Entity")
public class CustomerEntity extends BaseEntity implements UserDetails, Serializable {

    private static final long serialVersionUID = 8961428926740674164L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "Unique id field of CustomerEntity object")
    private Integer id;

    @NotNull
    @Size(
            min = 4,
            max = 45,
            message = "The username '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(name = "username", nullable = false, unique = true)
    @ApiModelProperty(value = "username field of CustomerEntity object")
    private String username;

    @NotNull
    @Size(
            min = 8,
            max = 60,
            message = "The password '${validatedValue}' must be between {min} and {max} characters long"
    )
    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "password field of CustomerEntity object")
    private String password;

    @Column(name = "contactNumber")
    @ApiModelProperty(value = "contactNumber field of CustomerEntity object")
    private String contactNumber;

    @Column(name = "name", nullable = false)
    @ApiModelProperty(value = "name field of CustomerEntity object")
    private String name;

    @Column(name = "lastname", nullable = false)
    @ApiModelProperty(value = "lastname field of CustomerEntity object")
    private String lastname;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } //false olursa Customer account has expired

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //false olursa Customer account is locked hatası alıyoruz.

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // false olursa Customer credentials have expired hatası alıyoruz.

    @Override
    public boolean isEnabled() {
        return true;
    } // false olursa Customer is disabled hatası dönüyor.
}
