package com.mjc.school.service.filtering;

import com.mjc.school.service.dto.filter.FilterReqDTO;
import com.mjc.school.service.exception.BadRequestException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilterService<T> {

    public Specification<T> getSearchSpecification(List<FilterReqDTO> filterReqs) {
        return (root, query, criteriaBuilder) -> {

            var orPredicates = new ArrayList<Predicate>();
            var andPredicates = new ArrayList<Predicate>();

            for (var filterReq : filterReqs) {
                var subPredicate = criteriaBuilder.conjunction();
                if (filterReq.getSubFilters() != null)
                    subPredicate = getSearchSpecification(filterReq.getSubFilters())
                            .toPredicate(root, query, criteriaBuilder);

                switch (filterReq.getOperation()) {
                    case EQUAL -> {
                        Predicate equal = criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get(filterReq.getColumn())),
                                filterReq.getValue().toLowerCase()
                        );
                        if(filterReq.getLogicalOperation().equals(FilterReqDTO.LogicalOperation.AND))
                            andPredicates.add(equal);
                        else
                            orPredicates.add(equal);
                    }
                    case LIKE -> {
                        Predicate like = criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(filterReq.getColumn())),
                                filterReq.getValue().toLowerCase()
                        );
                        if(filterReq.getLogicalOperation().equals(FilterReqDTO.LogicalOperation.AND))
                            andPredicates.add(like);
                        else
                            orPredicates.add(like);
                    }
                    case JOIN -> {
                        Predicate join =
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(
                                                root.join(filterReq.getJoinTableName()).get(filterReq.getColumn())),
                                        filterReq.getValue().toLowerCase()
                                );
                        if(filterReq.getLogicalOperation().equals(FilterReqDTO.LogicalOperation.AND))
                            andPredicates.add(join);
                        else
                            orPredicates.add(join);
                    }
                    case COMBINE -> {

                    }
                    default -> throw new BadRequestException("Unknown filter operation: " + filterReq.getOperation());
                }

                if (filterReq.getSubFilters() != null)
                    if(filterReq.getLogicalOperation().equals(FilterReqDTO.LogicalOperation.AND))
                        andPredicates.add(subPredicate);
                    else
                        orPredicates.add(subPredicate);
            }

            if (orPredicates.isEmpty() && !andPredicates.isEmpty())
                return criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
            if (!orPredicates.isEmpty() && andPredicates.isEmpty())
                return criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
            if (orPredicates.isEmpty())
                return null;
            var orCombined = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
            var andCombined = criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
            return criteriaBuilder.and(orCombined, andCombined);
        };
    }
}
