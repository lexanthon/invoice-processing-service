package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class InvoiceLinePrice implements Serializable{	
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
	@Column(precision = 15, scale = 2)
    private BigDecimal netPrice;
	private String netPriceCurrencyId;
	@Column(precision = 15, scale = 2)
	private BigDecimal baseQuantity;
	private String baseQuantityUnitCode;
	@Nullable
	private Boolean chargeIndicator;
  
    
    @Column(precision = 15, scale = 2)
    private BigDecimal priceDiscount;
    private String priceDiscountCurrencyId;
    @Column(precision = 15, scale = 2)
    private BigDecimal grossPrice;
    private String grossPriceCurrencyId;
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
	public BigDecimal getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}
	public String getNetPriceCurrencyId() {
		return netPriceCurrencyId;
	}
	public void setNetPriceCurrencyId(String netPriceCurrencyId) {
		this.netPriceCurrencyId = netPriceCurrencyId;
	}
	public BigDecimal getBaseQuantity() {
		return baseQuantity;
	}
	public void setBaseQuantity(BigDecimal baseQuantity) {
		this.baseQuantity = baseQuantity;
	}
	public String getBaseQuantityUnitCode() {
		return baseQuantityUnitCode;
	}
	public void setBaseQuantityUnitCode(String baseQuantityUnitCode) {
		this.baseQuantityUnitCode = baseQuantityUnitCode;
	}
	public Boolean isChargeIndicator() {
		return chargeIndicator;
	}
	public void setChargeIndicator(Boolean chargeIndicator) {
		this.chargeIndicator = chargeIndicator;
	}
	public BigDecimal getPriceDiscount() {
		return priceDiscount;
	}
	public void setPriceDiscount(BigDecimal priceDiscount) {
		this.priceDiscount = priceDiscount;
	}
	public String getPriceDiscountCurrencyId() {
		return priceDiscountCurrencyId;
	}
	public void setPriceDiscountCurrencyId(String priceDiscountCurrencyId) {
		this.priceDiscountCurrencyId = priceDiscountCurrencyId;
	}
	public BigDecimal getGrossPrice() {
		return grossPrice;
	}
	public void setGrossPrice(BigDecimal grossPrice) {
		this.grossPrice = grossPrice;
	}
	public String getGrossPriceCurrencyId() {
		return grossPriceCurrencyId;
	}
	public void setGrossPriceCurrencyId(String grossPriceCurrencyId) {
		this.grossPriceCurrencyId = grossPriceCurrencyId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "InvoiceLinePrice [rowId=" + rowId + ", lineId=" + lineId + ", netPrice=" + netPrice
				+ ", netPriceCurrencyId=" + netPriceCurrencyId + ", baseQuantity=" + baseQuantity
				+ ", baseQuantityUnitCode=" + baseQuantityUnitCode + ", chargeIndicator=" + chargeIndicator
				+ ", priceDiscount=" + priceDiscount + ", priceDiscountCurrencyId=" + priceDiscountCurrencyId
				+ ", grossPrice=" + grossPrice + ", grossPriceCurrencyId=" + grossPriceCurrencyId + "]";
	}
	public InvoiceLinePrice(Long lineId, BigDecimal netPrice, String netPriceCurrencyId, BigDecimal baseQuantity,
			String baseQuantityUnitCode, Boolean chargeIndicator, BigDecimal priceDiscount,
			String priceDiscountCurrencyId, BigDecimal grossPrice, String grossPriceCurrencyId) {
		super();
		this.lineId = lineId;
		this.netPrice = netPrice;
		this.netPriceCurrencyId = netPriceCurrencyId;
		this.baseQuantity = baseQuantity;
		this.baseQuantityUnitCode = baseQuantityUnitCode;
		this.chargeIndicator = chargeIndicator;
		this.priceDiscount = priceDiscount;
		this.priceDiscountCurrencyId = priceDiscountCurrencyId;
		this.grossPrice = grossPrice;
		this.grossPriceCurrencyId = grossPriceCurrencyId;
	}
   
}