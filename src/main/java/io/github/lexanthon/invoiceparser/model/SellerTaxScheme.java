package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class SellerTaxScheme implements Serializable{	
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
		private Long sellerId;
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
		public Long getSellerId() {
			return sellerId;
		}
		public void setSellerId(Long sellerId) {
			this.sellerId = sellerId;
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
			return "SellerTaxScheme [rowId=" + rowId + ", sellerId=" + sellerId + ", taxRegistrationId="
					+ taxRegistrationId + ", taxSchemeId=" + taxSchemeId + "]";
		}
		
		public SellerTaxScheme(Long sellerId, String taxRegistrationId, String taxSchemeId) {
			super();
			this.sellerId = sellerId;
			this.taxRegistrationId = taxRegistrationId;
			this.taxSchemeId = taxSchemeId;
		}
		
		

}
