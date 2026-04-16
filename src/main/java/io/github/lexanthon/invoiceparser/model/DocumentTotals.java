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
public class DocumentTotals implements Serializable{	
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
		
	private Long invoiceId;
	 
    @Column(precision = 15, scale = 2)
    private BigDecimal netAmount;
 
    private String netAmountCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal amountWithoutVat;
 
    private String amountWithoutVatCurrencyId;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal amountWithVat;
 
    private String amountWithVatCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal allowancesTotal;
 
    private String allowancesTotalCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal chargesTotal;
 
    private String chargesTotalCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal prepaidAmount;
 
    private String prepaidAmountCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal roundingAmount;
 
    private String roundingAmountCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal payableAmount;
 
    private String payableAmountCurrencyId;

	

	public DocumentTotals(Long invoiceId, BigDecimal netAmount, String netAmountCurrencyId, BigDecimal amountWithoutVat,
			String amountWithoutVatCurrencyId, BigDecimal amountWithVat, String amountWithVatCurrencyId,
			BigDecimal allowancesTotal, String allowancesTotalCurrencyId, BigDecimal chargesTotal,
			String chargesTotalCurrencyId, BigDecimal prepaidAmount, String prepaidAmountCurrencyId, BigDecimal roundingAmount,
			String roundingAmountCurrencyId, BigDecimal payableAmount, String payableAmountCurrencyId) {
		super();
		this.invoiceId = invoiceId;
		this.netAmount = netAmount;
		this.netAmountCurrencyId = netAmountCurrencyId;
		this.amountWithoutVat = amountWithoutVat;
		this.amountWithoutVatCurrencyId = amountWithoutVatCurrencyId;
		this.amountWithVat = amountWithVat;
		this.amountWithVatCurrencyId = amountWithVatCurrencyId;
		this.allowancesTotal = allowancesTotal;
		this.allowancesTotalCurrencyId = allowancesTotalCurrencyId;
		this.chargesTotal = chargesTotal;
		this.chargesTotalCurrencyId = chargesTotalCurrencyId;
		this.prepaidAmount = prepaidAmount;
		this.prepaidAmountCurrencyId = prepaidAmountCurrencyId;
		this.roundingAmount = roundingAmount;
		this.roundingAmountCurrencyId = roundingAmountCurrencyId;
		this.payableAmount = payableAmount;
		this.payableAmountCurrencyId = payableAmountCurrencyId;
	}



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



	public BigDecimal getNetAmount() {
		return netAmount;
	}



	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}



	public String getNetAmountCurrencyId() {
		return netAmountCurrencyId;
	}



	public void setNetAmountCurrencyId(String netAmountCurrencyId) {
		this.netAmountCurrencyId = netAmountCurrencyId;
	}



	public BigDecimal getAmountWithoutVat() {
		return amountWithoutVat;
	}



	public void setAmountWithoutVat(BigDecimal amountWithoutVat) {
		this.amountWithoutVat = amountWithoutVat;
	}



	public String getAmountWithoutVatCurrencyId() {
		return amountWithoutVatCurrencyId;
	}



	public void setAmountWithoutVatCurrencyId(String amountWithoutVatCurrencyId) {
		this.amountWithoutVatCurrencyId = amountWithoutVatCurrencyId;
	}



	public BigDecimal getAmountWithVat() {
		return amountWithVat;
	}



	public void setAmountWithVat(BigDecimal amountWithVat) {
		this.amountWithVat = amountWithVat;
	}



	public String getAmountWithVatCurrencyId() {
		return amountWithVatCurrencyId;
	}



	public void setAmountWithVatCurrencyId(String amountWithVatCurrencyId) {
		this.amountWithVatCurrencyId = amountWithVatCurrencyId;
	}



	public BigDecimal getAllowancesTotal() {
		return allowancesTotal;
	}



	public void setAllowancesTotal(BigDecimal allowancesTotal) {
		this.allowancesTotal = allowancesTotal;
	}



	public String getAllowancesTotalCurrencyId() {
		return allowancesTotalCurrencyId;
	}



	public void setAllowancesTotalCurrencyId(String allowancesTotalCurrencyId) {
		this.allowancesTotalCurrencyId = allowancesTotalCurrencyId;
	}



	public BigDecimal getChargesTotal() {
		return chargesTotal;
	}



	public void setChargesTotal(BigDecimal chargesTotal) {
		this.chargesTotal = chargesTotal;
	}



	public String getChargesTotalCurrencyId() {
		return chargesTotalCurrencyId;
	}



	public void setChargesTotalCurrencyId(String chargesTotalCurrencyId) {
		this.chargesTotalCurrencyId = chargesTotalCurrencyId;
	}



	public BigDecimal getPrepaidAmount() {
		return prepaidAmount;
	}



	public void setPrepaidAmount(BigDecimal prepaidAmount) {
		this.prepaidAmount = prepaidAmount;
	}



	public String getPrepaidAmountCurrencyId() {
		return prepaidAmountCurrencyId;
	}



	public void setPrepaidAmountCurrencyId(String prepaidAmountCurrencyId) {
		this.prepaidAmountCurrencyId = prepaidAmountCurrencyId;
	}



	public BigDecimal getRoundingAmount() {
		return roundingAmount;
	}



	public void setRoundingAmount(BigDecimal roundingAmount) {
		this.roundingAmount = roundingAmount;
	}



	public String getRoundingAmountCurrencyId() {
		return roundingAmountCurrencyId;
	}



	public void setRoundingAmountCurrencyId(String roundingAmountCurrencyId) {
		this.roundingAmountCurrencyId = roundingAmountCurrencyId;
	}



	public BigDecimal getPayableAmount() {
		return payableAmount;
	}



	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}



	public String getPayableAmountCurrencyId() {
		return payableAmountCurrencyId;
	}



	public void setPayableAmountCurrencyId(String payableAmountCurrencyId) {
		this.payableAmountCurrencyId = payableAmountCurrencyId;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "DocumentTotals [rowId=" + rowId + ", invoiceId=" + invoiceId + ", netAmount=" + netAmount
				+ ", netAmountCurrencyId=" + netAmountCurrencyId + ", amountWithoutVat=" + amountWithoutVat
				+ ", amountWithoutVatCurrencyId=" + amountWithoutVatCurrencyId + ", amountWithVat=" + amountWithVat
				+ ", amountWithVatCurrencyId=" + amountWithVatCurrencyId + ", allowancesTotal=" + allowancesTotal
				+ ", allowancesTotalCurrencyId=" + allowancesTotalCurrencyId + ", chargesTotal=" + chargesTotal
				+ ", chargesTotalCurrencyId=" + chargesTotalCurrencyId + ", prepaidAmount=" + prepaidAmount
				+ ", prepaidAmountCurrencyId=" + prepaidAmountCurrencyId + ", roundingAmount=" + roundingAmount
				+ ", roundingAmountCurrencyId=" + roundingAmountCurrencyId + ", payableAmount=" + payableAmount
				+ ", payableAmountCurrencyId=" + payableAmountCurrencyId + "]";
	}

	

}
