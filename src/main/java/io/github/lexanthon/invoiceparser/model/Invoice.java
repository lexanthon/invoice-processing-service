package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
	uniqueConstraints = @UniqueConstraint(
		name = "uk_invoice_invoice_file_id",
		columnNames = "invoice_file_id"
	)
)
public class Invoice implements Serializable{	
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
		@Column(name = "invoice_file_id", nullable = false)
		private Long invoiceFileId;
		@Column(columnDefinition = "MEDIUMTEXT")
		private String xml;
		@Column(length=25, columnDefinition="DATETIME")
		private Date dateReceived;
		@Column
		private String invoiceNumber;
		@Column(columnDefinition="DATE")
		private Date issueDate;
		@Column(columnDefinition="DATE")
		private Date dueDate;
		@Column
		private String typeCode;
		@Column(columnDefinition="TEXT")
		private String note;
		@Column(columnDefinition="DATE")
		private Date taxPointDate;
		@Column
		private String currencyCode;
		@Column
		private String taxCurrencyCode;
		@Column
		private String buyerAccountingReference;
		@Column
		private String buyerReference;
		@Column
		private String purchaseOrderReference;
		@Column
		private String salesOrderReference;
		@Column
		private String despatchAdviceReferenceId;
		@Column
		private String receivingDocumentReferenceId;
		@Column
		private String tenderLotReferenceId;
		@Column
		private String contractReferenceId;
		@Column
		private String projectReferenceId;
		@Column
		private String paymentTermsNote;

		
		
		
		public String getXml() {
			return xml;
		}



		public void setXml(String xml) {
			this.xml = xml;
		}



		public Date getDateReceived() {
			return dateReceived;
		}



		public void setDateReceived(Date dateReceived) {
			this.dateReceived = dateReceived;
		}



		public Long getRow_id() {
			return rowId;
		}



		public void setRow_id(Long row_id) {
			this.rowId = row_id;
		}



		public String getInvoiceNumber() {
			return invoiceNumber;
		}



		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}



		public Date getIssueDate() {
			return issueDate;
		}



		public void setIssueDate(Date issueDate) {
			this.issueDate = issueDate;
		}



		public Date getDueDate() {
			return dueDate;
		}



		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}



		public String getTypeCode() {
			return typeCode;
		}



		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}



		public String getNote() {
			return note;
		}



		public void setNote(String note) {
			this.note = note;
		}



		public Date getTaxPointDate() {
			return taxPointDate;
		}



		public void setTaxPointDate(Date taxPointDate) {
			this.taxPointDate = taxPointDate;
		}



		public String getCurrencyCode() {
			return currencyCode;
		}



		public void setCurrencyCode(String currencyCode) {
			this.currencyCode = currencyCode;
		}



		public String getTaxCurrencyCode() {
			return taxCurrencyCode;
		}



		public void setTaxCurrencyCode(String taxtCurrencyCode) {
			this.taxCurrencyCode = taxtCurrencyCode;
		}



		public String getBuyerAccountingReference() {
			return buyerAccountingReference;
		}



		public void setBuyerAccountingReference(String buyerAccountingReference) {
			this.buyerAccountingReference = buyerAccountingReference;
		}



		public String getBuyerReference() {
			return buyerReference;
		}



		public void setBuyerReference(String buyerReference) {
			this.buyerReference = buyerReference;
		}

		
		public String getPurchaseOrderReference() {
			return purchaseOrderReference;
		}



		public void setPurchaseOrderReference(String purchaseOrderReference) {
			this.purchaseOrderReference = purchaseOrderReference;
		}
		

		public String getSalesOrderReference() {
			return salesOrderReference;
		}



		public void setSalesOrderReference(String salesOrderReference) {
			this.salesOrderReference = salesOrderReference;
		}



		public String getDespatchAdviceReferenceId() {
			return despatchAdviceReferenceId;
		}



		public void setDespatchAdviceReferenceId(String despatchAdviceReferenceId) {
			this.despatchAdviceReferenceId = despatchAdviceReferenceId;
		}



		public String getReceivingDocumentReferenceId() {
			return receivingDocumentReferenceId;
		}



		public void setReceivingDocumentReferenceId(String receivingDocumentReferenceId) {
			this.receivingDocumentReferenceId = receivingDocumentReferenceId;
		}



		public String getTenderLotReferenceId() {
			return tenderLotReferenceId;
		}



		public void setTenderLotReferenceId(String tenderLotReferenceId) {
			this.tenderLotReferenceId = tenderLotReferenceId;
		}



		public String getContractReferenceId() {
			return contractReferenceId;
		}



		public void setContractReferenceId(String contractReferenceId) {
			this.contractReferenceId = contractReferenceId;
		}



		public String getProjectReferenceId() {
			return projectReferenceId;
		}



		public void setProjectReferenceId(String projectReferenceId) {
			this.projectReferenceId = projectReferenceId;
		}



		public String getPaymentTermsNote() {
			return paymentTermsNote;
		}



		public void setPaymentTermsNote(String paymentTermsNote) {
			this.paymentTermsNote = paymentTermsNote;
		}



		public static long getSerialversionuid() {
			return serialVersionUID;
		}



		public Long getRowId() {
			return rowId;
		}



		public void setRowId(Long rowId) {
			this.rowId = rowId;
		}



		public Long getInvoiceFileId() {
			return invoiceFileId;
		}



		public void setInvoiceFileId(Long invoiceFileId) {
			this.invoiceFileId = invoiceFileId;
		}



		public Invoice(Long invoiceFileId, String xml, Date dateReceived, String invoiceNumber, Date issueDate,
				Date dueDate, String typeCode, String note, Date taxPointDate, String currencyCode,
				String taxCurrencyCode, String buyerAccountingReference, String buyerReference,
				String purchaseOrderReference, String salesOrderReference, String despatchAdviceReferenceId,
				String receivingDocumentReferenceId, String tenderLotReferenceId, String contractReferenceId,
				String projectReferenceId, String paymentTermsNote) {
			super();
			this.invoiceFileId = invoiceFileId;
			this.xml = xml;
			this.dateReceived = dateReceived;
			this.invoiceNumber = invoiceNumber;
			this.issueDate = issueDate;
			this.dueDate = dueDate;
			this.typeCode = typeCode;
			this.note = note;
			this.taxPointDate = taxPointDate;
			this.currencyCode = currencyCode;
			this.taxCurrencyCode = taxCurrencyCode;
			this.buyerAccountingReference = buyerAccountingReference;
			this.buyerReference = buyerReference;
			this.purchaseOrderReference = purchaseOrderReference;
			this.salesOrderReference = salesOrderReference;
			this.despatchAdviceReferenceId = despatchAdviceReferenceId;
			this.receivingDocumentReferenceId = receivingDocumentReferenceId;
			this.tenderLotReferenceId = tenderLotReferenceId;
			this.contractReferenceId = contractReferenceId;
			this.projectReferenceId = projectReferenceId;
			this.paymentTermsNote = paymentTermsNote;
		}



		@Override
		public String toString() {
			return "Invoice [rowId=" + rowId + ", invoiceFileId=" + invoiceFileId + ", dateReceived=" + dateReceived
					+ ", invoiceNumber=" + invoiceNumber + ", issueDate=" + issueDate + ", dueDate=" + dueDate
					+ ", typeCode=" + typeCode + ", note=" + note + ", taxPointDate=" + taxPointDate + ", currencyCode="
					+ currencyCode + ", taxCurrencyCode=" + taxCurrencyCode + ", buyerAccountingReference="
					+ buyerAccountingReference + ", buyerReference=" + buyerReference + ", purchaseOrderReference="
					+ purchaseOrderReference + ", salesOrderReference=" + salesOrderReference
					+ ", despatchAdviceReferenceId=" + despatchAdviceReferenceId + ", receivingDocumentReferenceId="
					+ receivingDocumentReferenceId + ", tenderLotReferenceId=" + tenderLotReferenceId
					+ ", contractReferenceId=" + contractReferenceId + ", projectReferenceId=" + projectReferenceId
					+ ", paymentTermsNote=" + paymentTermsNote + "]";
		}



		



		
		
}
