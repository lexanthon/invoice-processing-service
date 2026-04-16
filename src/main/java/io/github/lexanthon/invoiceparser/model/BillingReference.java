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
public class BillingReference implements Serializable{	
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
		private String precedingInvoiceNumber;
		@Column(columnDefinition="DATE")
		private Date precedingInvoiceIssueDate;
		
		
		
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
		public String getPrecedingInvoiceNumber() {
			return precedingInvoiceNumber;
		}
		public void setPrecedingInvoiceNumber(String precedingInvoiceNumber) {
			this.precedingInvoiceNumber = precedingInvoiceNumber;
		}
		public Date getPrecedingInvoiceIssueDate() {
			return precedingInvoiceIssueDate;
		}
		public void setPrecedingInvoiceIssueDate(Date precedingInvoiceIssueDate) {
			this.precedingInvoiceIssueDate = precedingInvoiceIssueDate;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public BillingReference(Long invoiceId, String precedingInvoiceNumber, Date precedingInvoiceIssueDate) {
			super();
			this.invoiceId = invoiceId;
			this.precedingInvoiceNumber = precedingInvoiceNumber;
			this.precedingInvoiceIssueDate = precedingInvoiceIssueDate;
		}
		@Override
		public String toString() {
			return "BillingReference [rowId=" + rowId + ", invoiceId=" + invoiceId + ", precedingInvoiceNumber="
					+ precedingInvoiceNumber + ", precedingInvoiceIssueDate=" + precedingInvoiceIssueDate + "]";
		}
		
		
		
}
