package codesquad.bows.member.dto;

public record UserIdAvailableResponse(
        String userId,
        boolean isAvailable
) {
}