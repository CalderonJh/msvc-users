package org.calderon.users.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
  private String id;
  private String name;
  private String description;
  private String instructorName;
  private Integer studentsAmount;
}