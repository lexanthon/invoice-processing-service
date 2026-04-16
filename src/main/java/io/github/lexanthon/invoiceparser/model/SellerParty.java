package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.GenericGenerator;


@Entity
public class SellerParty implements Serializable{	
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
		private String partyId;
		@Column
		private String schemeId;
		
		
		public Long getRowId() {
			return rowId;
		}
		public void setRowId(Long rowId) {
			this.rowId = rowId;
		}
		public long getSellerId() {
			return sellerId;
		}
		public void setSellerId(long sellerId) {
			this.sellerId = sellerId;
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
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		@Override
		public String toString() {
			return "SellerParty [rowId=" + rowId + ", sellerId=" + sellerId + ", partyId=" + partyId + ", schemeId="
					+ schemeId + "]";
		}
		public SellerParty(Long sellerId, String partyId, String schemeId) {
			super();
			this.sellerId = sellerId;
			this.partyId = partyId;
			this.schemeId = schemeId;
		}
		
		

}
