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
public class TaxSubtotal  implements Serializable{	
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
	
	private Long totalId;
	 
    @Column(precision = 15, scale = 2)
    private BigDecimal taxableAmount;
 
    private String taxableAmountCurrencyId;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal taxAmount;
 
    private String taxAmountCurrencyId;
    private String categoryCode;
 
    @Column(precision = 7, scale = 4)
    private BigDecimal categoryRate;
 
    @Column(columnDefinition = "TEXT")
    private String exemptionReasonCode;
 
    @Column(columnDefinition = "TEXT")
    private String exemptionReasonText;
 
    private String taxSchemeId;

	public TaxSubtotal(Long totalId, BigDecimal taxableAmount, String taxableAmountCurrencyId, BigDecimal taxAmount,
			String taxAmountCurrencyId, String categoryCode, BigDecimal categoryRate, String exemptionReasonCode,
			String exemptionReasonText, String taxSchemeId) {
		super();
		this.totalId = totalId;
		this.taxableAmount = taxableAmount;
		this.taxableAmountCurrencyId = taxableAmountCurrencyId;
		this.taxAmount = taxAmount;
		this.taxAmountCurrencyId = taxAmountCurrencyId;
		this.categoryCode = categoryCode;
		this.categoryRate = categoryRate;
		this.exemptionReasonCode = exemptionReasonCode;
		this.exemptionReasonText = exemptionReasonText;
		this.taxSchemeId = taxSchemeId;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getTotalId() {
		return totalId;
	}

	public void setTotalId(Long totalId) {
		this.totalId = totalId;
	}

	public BigDecimal getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(BigDecimal taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public String getTaxableAmountCurrencyId() {
		return taxableAmountCurrencyId;
	}

	public void setTaxableAmountCurrencyId(String taxableAmountCurrencyId) {
		this.taxableAmountCurrencyId = taxableAmountCurrencyId;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getTaxAmountCurrencyId() {
		return taxAmountCurrencyId;
	}

	public void setTaxAmountCurrencyId(String taxAmountCurrencyId) {
		this.taxAmountCurrencyId = taxAmountCurrencyId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public BigDecimal getCategoryRate() {
		return categoryRate;
	}

	public void setCategoryRate(BigDecimal categoryRate) {
		this.categoryRate = categoryRate;
	}

	public String getExemptionReasonCode() {
		return exemptionReasonCode;
	}

	public void setExemptionReasonCode(String exemptionReasonCode) {
		this.exemptionReasonCode = exemptionReasonCode;
	}

	public String getExemptionReasonText() {
		return exemptionReasonText;
	}

	public void setExemptionReasonText(String exemptionReasonText) {
		this.exemptionReasonText = exemptionReasonText;
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
		return "TaxSubtotal [rowId=" + rowId + ", totalId=" + totalId + ", taxableAmount=" + taxableAmount
				+ ", taxableAmountCurrencyId=" + taxableAmountCurrencyId + ", taxAmount=" + taxAmount
				+ ", taxAmountCurrencyId=" + taxAmountCurrencyId + ", categoryCode=" + categoryCode + ", categoryRate="
				+ categoryRate + ", exemptionReasonCode=" + exemptionReasonCode + ", exemptionReasonText="
				+ exemptionReasonText + ", taxSchemeId=" + taxSchemeId + "]";
	}

	
    

}
