package com.mjc.school.service.filtering;

import com.mjc.school.service.exception.BadRequestException;
import com.mjc.school.service.dto.filter.FilterReqDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterService<T> {

    public Specification<T> getSearchSpecification(List<FilterReqDTO> filterReqs) {
        return (root, query, criteriaBuilder) -> {

            var predicates = new ArrayList<Predicate>();

            for (var filterReq : filterReqs) {
                switch (filterReq.getOperation()) {
                    case EQUAL -> {
                        Predicate equal = criteriaBuilder.equal(root.get(filterReq.getColumn()), filterReq.getValue());
                        predicates.add(equal);
                    }
                    case LIKE -> {
                        Predicate like = criteriaBuilder.like(root.get(filterReq.getColumn()), filterReq.getValue());
                        predicates.add(like);
                    }
                    case JOIN -> {
                        Predicate join =
                                criteriaBuilder.equal(
                                        root.join(filterReq.getJoinTableName()).get(filterReq.getColumn()),
                                        filterReq.getValue()
                                );
                        predicates.add(join);
                    }
                    default -> throw new BadRequestException("Unknown filter operation: " + filterReq.getOperation());
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
