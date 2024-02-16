package uk.gov.defra.stw.mapping.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmittedBy implements Serializable {

  @Serial
  private final static long serialVersionUID = -758018030035221648L;
  private String displayName;
  private String userId;
}
