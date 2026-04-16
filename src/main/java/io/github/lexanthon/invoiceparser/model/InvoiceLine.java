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
public class InvoiceLine implements Serializable{	
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
    private String lineId;
 
    @Column(columnDefinition = "TEXT")
    private String note;
    @Column(precision = 15, scale = 2)
    private BigDecimal quantity;
 
    private String unitCode;
 
    @Column(precision = 15, scale = 2)
    private BigDecimal netAmount;
 
    private String netAmountCurrencyId;
    private String referenceId;
    private String lineReferenceId;
    private String objectId;
    private String objectSchemeId;
    private String typeCode;
    
	public InvoiceLine(Long invoiceId, String lineId, String note, BigDecimal quantity, String unitCode,
			BigDecimal netAmount, String netAmountCurrencyId, String referenceId, String lineReferenceId, String objectId,
			String objectSchemeId, String typeCode) {
		super();
		
		this.invoiceId = invoiceId;
		this.lineId = lineId;
		this.note = note;
		this.quantity = quantity;
		this.unitCode = unitCode;
		this.netAmount = netAmount;
		this.netAmountCurrencyId = netAmountCurrencyId;
		this.referenceId = referenceId;
		this.lineReferenceId = lineReferenceId;
		this.objectId = objectId;
		this.objectSchemeId = objectSchemeId;
		this.typeCode = typeCode;
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
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getLineReferenceId() {
		return lineReferenceId;
	}
	public void setLineReferenceId(String lineReferenceId) {
		this.lineReferenceId = lineReferenceId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getObjectSchemeId() {
		return objectSchemeId;
	}
	public void setObjectSchemeId(String objectSchemeId) {
		this.objectSchemeId = objectSchemeId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "InvoiceLine [rowId=" + rowId + ", invoiceId=" + invoiceId + ", lineId=" + lineId + ", note=" + note
				+ ", quantity=" + quantity + ", unitCode=" + unitCode + ", netAmount=" + netAmount
				+ ", netAmountCurrencyId=" + netAmountCurrencyId + ", referenceId=" + referenceId + ", lineReferenceId="
				+ lineReferenceId + ", objectId=" + objectId + ", objectSchemeId=" + objectSchemeId + ", typeCode="
				+ typeCode + "]";
	}

}
