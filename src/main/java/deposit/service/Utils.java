package deposit.service;

import deposit.security.jwt.JwtUser;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc"))
            return Sort.Direction.ASC;
        else
            return Sort.Direction.DESC;
    }

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

    static int getCurrentUserId() {
        return ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
