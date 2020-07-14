package deposit.dto.utils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageDto {
    private final int currentPage;
    private final int totalPages;
    private final Object items;
}
