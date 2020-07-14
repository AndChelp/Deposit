package deposit.dto.utils;

import lombok.Getter;

@Getter
public class JwtDto {
    private final String token;

    public JwtDto(String token) {
        this.token = token;
    }
}
