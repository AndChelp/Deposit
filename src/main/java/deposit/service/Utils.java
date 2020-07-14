package deposit.service;

import deposit.security.jwt.JwtUser;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс.
 *
 * Позволяет получить список Order для сортировки запроса, а также получить Id текущего пользователя.
 */

public class Utils {
    static List<Order> getOrderList(String[] sort, CriteriaBuilder criteriaBuilder, Root<?> table) {
        List<Order> orderList = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                if (_sort[1].equals("asc"))
                    orderList.add(criteriaBuilder.asc(table.get((_sort[0]))));
                else
                    orderList.add(criteriaBuilder.desc(table.get((_sort[0]))));
            }
        } else {
            if (sort[1].equals("asc"))
                orderList.add(criteriaBuilder.asc(table.get((sort[0]))));
            else
                orderList.add(criteriaBuilder.desc(table.get((sort[0]))));
        }
        return orderList;
    }

    public static int getCurrentUserId() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.getId();
    }
}
