package codesquad.bows.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMetaData {

    private String projectName;
    private String domain;
    private LocalDateTime createdAt;

}
