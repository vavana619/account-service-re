package com.msa.account.entity;
//package com.example.myapp.entity;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import com.example.myapp.dto.AccountDto;
//
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "account")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Account_origin {
//
//    @Id
//    @GeneratedValue
//    private long id;
//
//    @Column(name = "email", nullable = false, unique = true)
//    private String email;
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Column(name = "address1", nullable = false)
//    private String address1;
//
//    @Column(name = "zip", nullable = false)
//    private String zip;
//
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//    
//    @CreationTimestamp
//    @Column(name = "latest_login_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date latest_login_at;
//
//    @Builder
//    public Account_origin(long id, String email, String firstName, String lastName, String password, String address1, String zip) {
//        this.id = id;
//    	this.email = email;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//        this.address1 = address1;
//        this.zip = zip;
//    }
//
//    public void updateMyAccount(AccountDto.MyAccountReq dto) {
//        this.address1 = dto.getAddress1();
//        this.zip = dto.getZip();
//    }
//}
