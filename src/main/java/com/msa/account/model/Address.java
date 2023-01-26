package com.msa.account.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	@NotEmpty
	@Column(name = "address1", nullable = false)
	private String address1;
	
	@NotEmpty
	@Column(name = "zip", nullable = false)
	private String zip;
	
	@Builder
	public Address(String address1, String zip) {
		this.address1 = address1;
		this.zip = zip;
	}
}
