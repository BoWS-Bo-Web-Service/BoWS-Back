package codesquad.bows.dto;

import java.time.LocalDateTime;

public interface ProjectMetaData {

    String getProjectName();
    String getDomain();
    LocalDateTime getCreatedAt();

}
