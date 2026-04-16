package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class PaymentMeans implements Serializable{	
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
		private String typeCode;
		@Column(columnDefinition="TEXT")
		private String typeCodeName;
		@Column(columnDefinition="TEXT")
		private String paymentInfo;
		@Column(columnDefinition="TEXT")
		private String cardAccountNumber;
		@Column(columnDefinition="TEXT")
		private String cardNetworkId;
		@Column(columnDefinition="TEXT")
		private String cardHolderName;
		@Column(columnDefinition="TEXT")
		private String creditAccountName;
		@Column
		private String creditAccountId;
		@Column
		private String creditServiceProviderId;
		@Column
		private String mandateReferenceId;
		@Column
		private String debitedAccountId;
		
		
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
		public String getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(String typeCode) {
			this.typeCode = typeCode;
		}
		public String getTypeCodeName() {
			return typeCodeName;
		}
		public void setTypeCodeName(String typeCodeName) {
			this.typeCodeName = typeCodeName;
		}
		public String getPaymentInfo() {
			return paymentInfo;
		}
		public void setPaymentInfo(String paymentInfo) {
			this.paymentInfo = paymentInfo;
		}
		public String getCardAccountNumber() {
			return cardAccountNumber;
		}
		public void setCardAccountNumber(String cardAccountNumber) {
			this.cardAccountNumber = cardAccountNumber;
		}
		public String getCardNetworkId() {
			return cardNetworkId;
		}
		public void setCardNetworkId(String cardNetworkId) {
			this.cardNetworkId = cardNetworkId;
		}
		public String getCardHolderName() {
			return cardHolderName;
		}
		public void setCardHolderName(String cardHolderName) {
			this.cardHolderName = cardHolderName;
		}
		public String getCreditAccountName() {
			return creditAccountName;
		}
		public void setCreditAccountName(String creditAccountName) {
			this.creditAccountName = creditAccountName;
		}
		public String getCreditAccountId() {
			return creditAccountId;
		}
		public void setCreditAccountId(String creditAccountId) {
			this.creditAccountId = creditAccountId;
		}
		public String getCreditServiceProviderId() {
			return creditServiceProviderId;
		}
		public void setCreditServiceProviderId(String creditServiceProviderId) {
			this.creditServiceProviderId = creditServiceProviderId;
		}
		public String getMandateReferenceId() {
			return mandateReferenceId;
		}
		public void setMandateReferenceId(String mandateReferenceId) {
			this.mandateReferenceId = mandateReferenceId;
		}
		public String getDebitedAccountId() {
			return debitedAccountId;
		}
		public void setDebitedAccountId(String debitedAccountId) {
			this.debitedAccountId = debitedAccountId;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "PaymentMeans [rowId=" + rowId + ", invoiceId=" + invoiceId + ", typeCode=" + typeCode
					+ ", typeCodeName=" + typeCodeName + ", paymentInfo=" + paymentInfo + ", cardAccountNumber="
					+ cardAccountNumber + ", cardNetworkId=" + cardNetworkId + ", cardHolderName=" + cardHolderName
					+ ", creditAccountName=" + creditAccountName + ", creditAccountId=" + creditAccountId
					+ ", creditServiceProviderId=" + creditServiceProviderId + ", mandateReferenceId="
					+ mandateReferenceId + ", debitedAccountId=" + debitedAccountId + "]";
		}
		public PaymentMeans(Long invoiceId, String typeCode, String typeCodeName, String paymentInfo,
				String cardAccountNumber, String cardNetworkId, String cardHolderName, String creditAccountName,
				String creditAccountId, String creditServiceProviderId, String mandateReferenceId,
				String debitedAccountId) {
			super();
			this.invoiceId = invoiceId;
			this.typeCode = typeCode;
			this.typeCodeName = typeCodeName;
			this.paymentInfo = paymentInfo;
			this.cardAccountNumber = cardAccountNumber;
			this.cardNetworkId = cardNetworkId;
			this.cardHolderName = cardHolderName;
			this.creditAccountName = creditAccountName;
			this.creditAccountId = creditAccountId;
			this.creditServiceProviderId = creditServiceProviderId;
			this.mandateReferenceId = mandateReferenceId;
			this.debitedAccountId = debitedAccountId;
		}
		

}
