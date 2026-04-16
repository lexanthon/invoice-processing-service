package io.github.lexanthon.invoiceparser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ItemClassification  implements Serializable{	
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
	
		private Long itemId;
	    private String classificationId;
	    private String schemeId;
	    private String scheme_version_id;
		public Long getRowId() {
			return rowId;
		}
		public void setRowId(Long rowId) {
			this.rowId = rowId;
		}
		public Long getItemId() {
			return itemId;
		}
		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}
		public String getClassificationId() {
			return classificationId;
		}
		public void setClassificationId(String classificationId) {
			this.classificationId = classificationId;
		}
		public String getSchemeId() {
			return schemeId;
		}
		public void setSchemeId(String schemeId) {
			this.schemeId = schemeId;
		}
		public String getScheme_version_id() {
			return scheme_version_id;
		}
		public void setScheme_version_id(String scheme_version_id) {
			this.scheme_version_id = scheme_version_id;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "ItemClassification [rowId=" + rowId + ", itemId=" + itemId + ", classificationId="
					+ classificationId + ", schemeId=" + schemeId + ", scheme_version_id=" + scheme_version_id + "]";
		}
		public ItemClassification(Long itemId, String classificationId, String schemeId, String scheme_version_id) {
			super();
			this.itemId = itemId;
			this.classificationId = classificationId;
			this.schemeId = schemeId;
			this.scheme_version_id = scheme_version_id;
		}

}
