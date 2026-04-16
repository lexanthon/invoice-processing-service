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
public class InvoicePeriod implements Serializable{	
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
		private long invoiceId;
		@Column(columnDefinition="DATE")
		private Date startDate;
		@Column(columnDefinition="DATE")
		private Date endDate;
		@Column
		private String taxPointDateCode;
		
		
		public Long getRow_id() {
			return rowId;
		}
		public void setRow_id(Long row_id) {
			this.rowId = row_id;
		}
		public long getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(long invoiceId) {
			this.invoiceId = invoiceId;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public String getTaxPointDateCode() {
			return taxPointDateCode;
		}
		public void setTaxPointDateCode(String taxPointDateCode) {
			this.taxPointDateCode = taxPointDateCode;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public InvoicePeriod(long invoiceId, Date startDate, Date endDate, String taxPointDateCode) {
			super();
			this.invoiceId = invoiceId;
			this.startDate = startDate;
			this.endDate = endDate;
			this.taxPointDateCode = taxPointDateCode;
		}
		@Override
		public String toString() {
			return "InvoicePeriod [rowId=" + rowId + ", invoiceId=" + invoiceId + ", startDate=" + startDate
					+ ", endDate=" + endDate + ", taxPointDateCode=" + taxPointDateCode + "]";
		}
		
		
		
}
