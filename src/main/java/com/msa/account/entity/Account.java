package com.msa.account.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.msa.account.dto.AccountDto;
import com.msa.account.model.Address;
import com.msa.account.model.Name;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Embedded
    private Name name;
    
    @NotBlank
    private String password;

    @Embedded
    private Address address;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date latest_login_at;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Account(String email, Name name, String password, Address address, String zip, Role role) {
    	this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public void updateAccount(AccountDto.UpdateAccountReq dto) {
        this.address = dto.getAddress();
    }
}
