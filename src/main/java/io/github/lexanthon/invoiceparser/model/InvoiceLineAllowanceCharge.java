package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class InvoiceLineAllowanceCharge implements Serializable{	
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
	
		private Long lineId;
		
	    private Boolean chargeIndicator;
	    private String reasonCode;
	 
	    @Column(columnDefinition = "TEXT")
	    private String reason;
	 
	    @Column(precision = 7, scale = 4)
	    private BigDecimal percentage;
	 
	    @Column(precision = 15, scale = 2)
	    private BigDecimal amount;
	 
	    private String amountCurrencyId;

	    @Column(precision = 15, scale = 2)
	    private BigDecimal baseAmount;
	    private String baseAmountCurrencyId;
	   

		public Long getRowId() {
			return rowId;
		}

		public void setRowId(Long rowId) {
			this.rowId = rowId;
		}

		public Long getLineId() {
			return lineId;
		}

		public void setLineId(Long lineId) {
			this.lineId = lineId;
		}

		public Boolean isChargeIndicator() {
			return chargeIndicator;
		}

		public void setChargeIndicator(Boolean chargeIndicator) {
			this.chargeIndicator = chargeIndicator;
		}

		public String getReasonCode() {
			return reasonCode;
		}

		public void setReasonCode(String reasonCode) {
			this.reasonCode = reasonCode;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public BigDecimal getPercentage() {
			return percentage;
		}

		public void setPercentage(BigDecimal percentage) {
			this.percentage = percentage;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getAmountCurrencyId() {
			return amountCurrencyId;
		}

		public void setAmountCurrencyId(String amountCurrencyId) {
			this.amountCurrencyId = amountCurrencyId;
		}

		public String getBaseAmountCurrencyId() {
			return baseAmountCurrencyId;
		}

		public void setBaseAmountCurrencyId(String baseAmountCurrencyId) {
			this.baseAmountCurrencyId = baseAmountCurrencyId;
		}

		public BigDecimal getBaseAmount() {
			return baseAmount;
		}

		public void setBaseAmount(BigDecimal baseAmount) {
			this.baseAmount = baseAmount;
		}

		

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	

		public InvoiceLineAllowanceCharge(Long lineId, Boolean chargeIndicator, String reasonCode, String reason,
				BigDecimal percentage, BigDecimal amount, String amountCurrencyId, BigDecimal baseAmount,
				String baseAmountCurrencyId) {
			super();
			this.lineId = lineId;
			this.chargeIndicator = chargeIndicator;
			this.reasonCode = reasonCode;
			this.reason = reason;
			this.percentage = percentage;
			this.amount = amount;
			this.amountCurrencyId = amountCurrencyId;
			this.baseAmount = baseAmount;
			this.baseAmountCurrencyId = baseAmountCurrencyId;
		}

		

	


	    
	    
	    

}
