package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Delivery implements Serializable{	
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
		@Column(columnDefinition="DATE")
		private Date actualDeliveryDate;
		@Column
		private String locationId;
		@Column
		private String locationSchemeId;
		@Column(columnDefinition="TEXT")
		private String locationCity;
		@Column(columnDefinition="TEXT")
		private String addressLine1;
		@Column(columnDefinition="TEXT")
		private String addressLine2;
		@Column(columnDefinition="TEXT")
		private String addressLine3;
		@Column(columnDefinition="TEXT")
		private String locationPostCode;
		@Column(columnDefinition="TEXT")
		private String locationCountrySubdivion;
		@Column(columnDefinition="TEXT")
		private String locationCountryCode;
		@Column(columnDefinition="TEXT")
		private String deliverToParty;
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
		public Date getActualDeliveryDate() {
			return actualDeliveryDate;
		}
		public void setActualDeliveryDate(Date actualDeliveryDate) {
			this.actualDeliveryDate = actualDeliveryDate;
		}
		public String getLocationId() {
			return locationId;
		}
		public void setLocationId(String locationId) {
			this.locationId = locationId;
		}
		public String getLocationSchemeId() {
			return locationSchemeId;
		}
		public void setLocationSchemeId(String locationSchemeId) {
			this.locationSchemeId = locationSchemeId;
		}
		public String getLocationCity() {
			return locationCity;
		}
		public void setLocationCity(String locationCity) {
			this.locationCity = locationCity;
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
		public String getLocationPostCode() {
			return locationPostCode;
		}
		public void setLocationPostCode(String locationPostCode) {
			this.locationPostCode = locationPostCode;
		}
		public String getLocationCountrySubdivion() {
			return locationCountrySubdivion;
		}
		public void setLocationCountrySubdivion(String locationCountrySubdivion) {
			this.locationCountrySubdivion = locationCountrySubdivion;
		}
		public String getLocationCountryCode() {
			return locationCountryCode;
		}
		public void setLocationCountryCode(String locationCountryCode) {
			this.locationCountryCode = locationCountryCode;
		}
		public String getDeliverToParty() {
			return deliverToParty;
		}
		public void setDeliverToParty(String deliverToParty) {
			this.deliverToParty = deliverToParty;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "Delivery [rowId=" + rowId + ", invoiceId=" + invoiceId + ", actualDeliveryDate="
					+ actualDeliveryDate + ", locationId=" + locationId + ", locationSchemeId=" + locationSchemeId
					+ ", locationCity=" + locationCity + ", addressLine1=" + addressLine1 + ", addressLine2="
					+ addressLine2 + ", addressLine3=" + addressLine3 + ", locationPostCode=" + locationPostCode
					+ ", locationCountrySubdivion=" + locationCountrySubdivion + ", locationCountryCode="
					+ locationCountryCode + ", deliverToParty=" + deliverToParty + "]";
		}
		public Delivery(Long invoiceId, Date actualDeliveryDate, String locationId, String locationSchemeId,
				String locationCity, String addressLine1, String addressLine2, String addressLine3,
				String locationPostCode, String locationCountrySubdivion, String locationCountryCode,
				String deliverToParty) {
			super();
			this.invoiceId = invoiceId;
			this.actualDeliveryDate = actualDeliveryDate;
			this.locationId = locationId;
			this.locationSchemeId = locationSchemeId;
			this.locationCity = locationCity;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.addressLine3 = addressLine3;
			this.locationPostCode = locationPostCode;
			this.locationCountrySubdivion = locationCountrySubdivion;
			this.locationCountryCode = locationCountryCode;
			this.deliverToParty = deliverToParty;
		}
		

}
