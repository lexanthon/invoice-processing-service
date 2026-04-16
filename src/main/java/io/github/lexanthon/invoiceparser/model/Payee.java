package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Payee implements Serializable{	
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
		private String partyId;
		@Column
		private String schemeId;
		@Column(columnDefinition="TEXT")
		private String name;
		@Column
		private String legalRegistrationId;
		@Column
		private String legalRegistrationSchemeId;
		
		
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "Payee [rowId=" + rowId + ", invoiceId=" + invoiceId + ", partyId=" + partyId + ", schemeId="
					+ schemeId + ", name=" + name + ", legalRegistrationId=" + legalRegistrationId
					+ ", legalRegistrationSchemeId=" + legalRegistrationSchemeId + "]";
		}
		public Payee(Long invoiceId, String partyId, String schemeId, String name, String legalRegistrationId,
				String legalRegistrationSchemeId) {
			super();
			this.invoiceId = invoiceId;
			this.partyId = partyId;
			this.schemeId = schemeId;
			this.name = name;
			this.legalRegistrationId = legalRegistrationId;
			this.legalRegistrationSchemeId = legalRegistrationSchemeId;
		}
		
		
		
		
}
