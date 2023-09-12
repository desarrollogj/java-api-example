package com.desarrollogj.exampleapi.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private Long id;
  private String reference;
  private String firstName;
  private String lastName;
  private String email;
  private Boolean active;
  private ZonedDateTime created;
  private ZonedDateTime updated;
  private Integer version;
}
