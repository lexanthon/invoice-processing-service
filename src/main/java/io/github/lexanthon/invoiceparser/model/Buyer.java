package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Buyer implements Serializable{	
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
		@Column
		private String electronicAddressId;
		@Column
		private String electronicAddressSchemeId;
		@Column
		private String partyId;
		@Column
		private String schemeId;
		@Column(columnDefinition="TEXT")
		private String tradingName;
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
		private String countrySubdivision;
		@Column(columnDefinition="TEXT")
		private String countryCode;
		@Column
		private String taxRegistrationId;
		@Column
		private String taxSchemeId;
		@Column(columnDefinition="TEXT")
		private String legalName;
		@Column
		private String legalRegistrationId;
		@Column
		private String legalRegistrationSchemeId;
		@Column(columnDefinition="TEXT")
		private String contactName;
		@Column(columnDefinition="TEXT")
		private String contactPhone;
		@Column(columnDefinition="TEXT")
		private String contactEmail;
		
		
		
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
		public String getElectronicAddressId() {
			return electronicAddressId;
		}
		public void setElectronicAddressId(String electronicAddressId) {
			this.electronicAddressId = electronicAddressId;
		}
		public String getElectronicAddressSchemeId() {
			return electronicAddressSchemeId;
		}
		public void setElectronicAddressSchemeId(String electronicAddressSchemeId) {
			this.electronicAddressSchemeId = electronicAddressSchemeId;
		}
		public String getPartyId() {
			return partyId;
		}
		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}
		public String getSchemeId() {
			return schemeId;
		}
		public void setSchemeId(String schemeId) {
			this.schemeId = schemeId;
		}
		public String getTradingName() {
			return tradingName;
		}
		public void setTradingName(String tradingName) {
			this.tradingName = tradingName;
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
		public String getCountrySubdivision() {
			return countrySubdivision;
		}
		public void setCountrySubdivision(String countrySubdivision) {
			this.countrySubdivision = countrySubdivision;
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
		public String getLegalName() {
			return legalName;
		}
		public void setLegalName(String legalName) {
			this.legalName = legalName;
		}
		public String getLegalRegistrationId() {
			return legalRegistrationId;
		}
		public void setLegalRegistrationId(String legalRegistrationId) {
			this.legalRegistrationId = legalRegistrationId;
		}
		public String getLegalRegistrationSchemeId() {
			return legalRegistrationSchemeId;
		}
		public void setLegalRegistrationSchemeId(String legalRegistrationSchemeId) {
			this.legalRegistrationSchemeId = legalRegistrationSchemeId;
		}
		public String getContactName() {
			return contactName;
		}
		public void setContactName(String contactName) {
			this.contactName = contactName;
		}
		public String getContactPhone() {
			return contactPhone;
		}
		public void setContactPhone(String contactPhone) {
			this.contactPhone = contactPhone;
		}
		public String getContactEmail() {
			return contactEmail;
		}
		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "Buyer [rowId=" + rowId + ", invoiceId=" + invoiceId + ", electronicAddressId=" + electronicAddressId
					+ ", electronicAddressSchemeId=" + electronicAddressSchemeId + ", partyId=" + partyId
					+ ", schemeId=" + schemeId + ", tradingName=" + tradingName + ", addressLine1=" + addressLine1
					+ ", addressLine2=" + addressLine2 + ", addressLine3=" + addressLine3 + ", city=" + city
					+ ", postCode=" + postCode + ", countrySubdivision=" + countrySubdivision + ", countryCode="
					+ countryCode + ", taxRegistrationId=" + taxRegistrationId + ", taxSchemeId=" + taxSchemeId
					+ ", legalName=" + legalName + ", legalRegistrationId=" + legalRegistrationId
					+ ", legalRegistrationSchemeId=" + legalRegistrationSchemeId + ", contactName=" + contactName
					+ ", contactPhone=" + contactPhone + ", contactEmail=" + contactEmail + "]";
		}
		public Buyer(Long invoiceId, String electronicAddressId, String electronicAddressSchemeId, String partyId,
				String schemeId, String tradingName, String addressLine1, String addressLine2, String addressLine3,
				String city, String postCode, String countrySubdivision, String countryCode, String taxRegistrationId,
				String taxSchemeId, String legalName, String legalRegistrationId, String legalRegistrationSchemeId,
				String contactName, String contactPhone, String contactEmail) {
			super();
			this.invoiceId = invoiceId;
			this.electronicAddressId = electronicAddressId;
			this.electronicAddressSchemeId = electronicAddressSchemeId;
			this.partyId = partyId;
			this.schemeId = schemeId;
			this.tradingName = tradingName;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.addressLine3 = addressLine3;
			this.city = city;
			this.postCode = postCode;
			this.countrySubdivision = countrySubdivision;
			this.countryCode = countryCode;
			this.taxRegistrationId = taxRegistrationId;
			this.taxSchemeId = taxSchemeId;
			this.legalName = legalName;
			this.legalRegistrationId = legalRegistrationId;
			this.legalRegistrationSchemeId = legalRegistrationSchemeId;
			this.contactName = contactName;
			this.contactPhone = contactPhone;
			this.contactEmail = contactEmail;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		

}
