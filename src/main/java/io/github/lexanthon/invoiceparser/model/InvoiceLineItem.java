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
public class InvoiceLineItem implements Serializable{	
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
	 
	    @Column(columnDefinition = "TEXT")
	    private String description;
	 
	    @Column(columnDefinition = "TEXT")
	    private String name;
	 
	    private String buyersId;
	    private String sellersId;
	    private String standardId;
	    private String standardSchemeId;
	    private String originCountry;
	    private String vatCategoryCode;
	    @Column(precision = 7, scale = 4)
	    private BigDecimal vatRate;
	    private String tax_scheme_id;
	    
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
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getBuyersId() {
			return buyersId;
		}
		public void setBuyersId(String buyersId) {
			this.buyersId = buyersId;
		}
		public String getSellersId() {
			return sellersId;
		}
		public void setSellersId(String sellersId) {
			this.sellersId = sellersId;
		}
		public String getStandardId() {
			return standardId;
		}
		public void setStandardId(String standardId) {
			this.standardId = standardId;
		}
		public String getStandardSchemeId() {
			return standardSchemeId;
		}
		public void setStandardSchemeId(String standardSchemeId) {
			this.standardSchemeId = standardSchemeId;
		}
		public String getOriginCountry() {
			return originCountry;
		}
		public void setOriginCountry(String originCountry) {
			this.originCountry = originCountry;
		}
		public String getVatCategoryCode() {
			return vatCategoryCode;
		}
		public void setVatCategoryCode(String vatCategoryCode) {
			this.vatCategoryCode = vatCategoryCode;
		}
		public BigDecimal getVatRate() {
			return vatRate;
		}
		public void setVatRate(BigDecimal vatRate) {
			this.vatRate = vatRate;
		}
		public String getTax_scheme_id() {
			return tax_scheme_id;
		}
		public void setTax_scheme_id(String tax_scheme_id) {
			this.tax_scheme_id = tax_scheme_id;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public InvoiceLineItem(Long lineId, String description, String name, String buyersId,
				String sellersId, String standardId, String standardSchemeId, String originCountry,
				String vatCategoryCode, BigDecimal vatRate, String tax_scheme_id) {
			super();
			
			this.lineId = lineId;
			this.description = description;
			this.name = name;
			this.buyersId = buyersId;
			this.sellersId = sellersId;
			this.standardId = standardId;
			this.standardSchemeId = standardSchemeId;
			this.originCountry = originCountry;
			this.vatCategoryCode = vatCategoryCode;
			this.vatRate = vatRate;
			this.tax_scheme_id = tax_scheme_id;
		}
		@Override
		public String toString() {
			return "InvoiceLineItem [rowId=" + rowId + ", lineId=" + lineId + ", description=" + description + ", name="
					+ name + ", buyersId=" + buyersId + ", sellersId=" + sellersId + ", standardId=" + standardId
					+ ", standardSchemeId=" + standardSchemeId + ", originCountry=" + originCountry
					+ ", vatCategoryCode=" + vatCategoryCode + ", vatRate=" + vatRate + ", tax_scheme_id="
					+ tax_scheme_id + "]";
		}
	    
		
	    
	    

}
