package com.desarrollogj.exampleapi.api.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @Column(value = "id")
  private Long id;

  @Column(value = "reference")
  private String reference;

  @Column(value = "first_name")
  private String firstName;

  @Column(value = "last_name")
  private String lastName;

  @Column(value = "email")
  private String email;

  @Column(value = "is_active")
  private Boolean active;

  @CreatedDate
  @Column(value = "created")
  private ZonedDateTime created;

  @LastModifiedDate
  @Column(value = "updated")
  private ZonedDateTime updated;

  private Integer version;
}
