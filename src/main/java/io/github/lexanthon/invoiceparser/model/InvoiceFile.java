package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="invoice_file")
public class InvoiceFile implements Serializable{	
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
		@Column(name = "id")
		private Long id;
		@Column(length=25, columnDefinition="DATETIME")
		private Date dateReceived;
		@Column(name="fileName" , nullable=false , length=1000)
		private String fileName;
		@Column(length=10)
		private String invoiceType;
		@Column(length=255)
		private String messageId;
		@Column(columnDefinition = "MEDIUMTEXT")
		private String xml;
		@Column
		private int status;
		@Column(name="retries_count")
		private Integer retriesCount;
		@Column(name="failure_reason")
		private String failureReason;
		
		
		
		
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
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getInvoiceType() {
			return invoiceType;
		}
		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}
		public String getMessageId() {
			return messageId;
		}
		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}	
		
		public Integer getRetriesCount() {
			return retriesCount;
		}
		public void setRetriesCount(Integer retriesCount) {
			this.retriesCount = retriesCount;
		}
		public String getFailureReason() {
			return failureReason;
		}
		public void setFailureReason(String failureReason) {
			this.failureReason = failureReason;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
}
