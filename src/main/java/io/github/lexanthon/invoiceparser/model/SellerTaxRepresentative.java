package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.GenericGenerator;


@Entity
public class SellerTaxRepresentative implements Serializable{	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO, 
		    generator="native"
		)//for dont create the extra hibernate table for auto increments in db
		@GenericGenerator(
		    name = "native", 
		    strategy = "native"
		)
		@Column
		private Long rowId;
		@Column
		private Long invoiceId;
		@Column(columnDefinition="TEXT")
		private String name;
		@Column(columnDefinition="TEXT")
		private String addressLine1;
		@Column(columnDefinition="TEXT")
		private String addressLine2;
		@Column(columnDefinition="TEXT")
		private String addressLine3;
		@Column(columnDefinition="TEXT")
		private String city;
		@Column(columnDefinition="TEXT")
		private String postCode;
		@Column(columnDefinition="TEXT")
		private String countrySubdivion;
		@Column(columnDefinition="TEXT")
		private String countryCode;
		@Column
		private String taxRegistrationId;
		@Column
		private String taxSchemeId;
		
		
		public Long getRowId() {
			return rowId;
		}
		public void setRowId(Long rowId) {
			this.rowId = rowId;
		}
		public Long getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(Long invoiceId) {
			this.invoiceId = invoiceId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAddressLine1() {
			return addressLine1;
		}
		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}
		public String getAddressLine2() {
			return addressLine2;
		}
		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}
		public String getAddressLine3() {
			return addressLine3;
		}
		public void setAddressLine3(String addressLine3) {
			this.addressLine3 = addressLine3;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPostCode() {
			return postCode;
		}
		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		public String getCountrySubdivion() {
			return countrySubdivion;
		}
		public void setCountrySubdivion(String countrySubdivion) {
			this.countrySubdivion = countrySubdivion;
		}
		public String getCountryCode() {
			return countryCode;
		}
		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}
		public String getTaxRegistrationId() {
			return taxRegistrationId;
		}
		public void setTaxRegistrationId(String taxRegistrationId) {
			this.taxRegistrationId = taxRegistrationId;
		}
		public String getTaxSchemeId() {
			return taxSchemeId;
		}
		public void setTaxSchemeId(String taxSchemeId) {
			this.taxSchemeId = taxSchemeId;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "SellerTaxRepresentative [rowId=" + rowId + ", invoiceId=" + invoiceId + ", name=" + name
					+ ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3="
					+ addressLine3 + ", city=" + city + ", postCode=" + postCode + ", countrySubdivion="
					+ countrySubdivion + ", countryCode=" + countryCode + ", taxRegistrationId=" + taxRegistrationId
					+ ", taxSchemeId=" + taxSchemeId + "]";
		}
		public SellerTaxRepresentative(Long invoiceId, String name, String addressLine1, String addressLine2,
				String addressLine3, String city, String postCode, String countrySubdivion, String countryCode,
				String taxRegistrationId, String taxSchemeId) {
			super();
			this.invoiceId = invoiceId;
			this.name = name;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.addressLine3 = addressLine3;
			this.city = city;
			this.postCode = postCode;
			this.countrySubdivion = countrySubdivion;
			this.countryCode = countryCode;
			this.taxRegistrationId = taxRegistrationId;
			this.taxSchemeId = taxSchemeId;
		}
		
		

}
