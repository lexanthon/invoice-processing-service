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
public class InvoiceLinePeriod implements Serializable{	
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
	 
    @Column(columnDefinition = "DATE")
    private Date startDate;
 
    @Column(columnDefinition = "DATE")
    private Date endDate;
 
    private String taxPointDateCode;

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

	@Override
	public String toString() {
		return "InvoiceLinePeriod [rowId=" + rowId + ", lineId=" + lineId + ", startDate=" + startDate + ", endDate="
				+ endDate + ", taxPointDateCode=" + taxPointDateCode + "]";
	}

	public InvoiceLinePeriod(Long lineId, Date startDate, Date endDate, String taxPointDateCode) {
		super();
		
		this.lineId = lineId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.taxPointDateCode = taxPointDateCode;
	}
 
}
