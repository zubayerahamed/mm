package com.zayaanit.mm.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Zubayer Ahamed
 * @since Nov 15, 2023
 */
@MappedSuperclass
@Data
public class BaseEntity {

	@NotNull
	@Column(name = "created_by", nullable = false, length = 20)
	private String createdBy;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@NotNull
	@Column(name = "updated_by", nullable = false, length = 20)
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable = true)
	private User user;
}
