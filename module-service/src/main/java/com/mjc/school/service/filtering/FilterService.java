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

            List<Predicate> orPredicates = new ArrayList<>();
            List<Predicate> andPredicates = new ArrayList<>();

            for (var filterReq : filterReqs) {
                Predicate subPredicate = null;
                if (filterReq.getSubFilters() != null)
                    subPredicate = getSearchSpecification(filterReq.getSubFilters())
                            .toPredicate(root, query, criteriaBuilder);

                Predicate predicate = null;

                switch (filterReq.getOperation()) {
                    case EQUAL ->
                        predicate = criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get(filterReq.getColumn())),
                                filterReq.getValue().toLowerCase()
                        );
                    case LIKE ->
                        predicate = criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(filterReq.getColumn())),
                                filterReq.getValue().toLowerCase()
                        );
                    case JOIN ->
                        predicate =
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(
                                                root.join(filterReq.getJoinTableName()).get(filterReq.getColumn())),
                                        filterReq.getValue().toLowerCase()
                                );
                    case COMBINE -> {
                        if (filterReq.getSubFilters() == null || filterReq.getSubFilters().isEmpty())
                            throw new BadRequestException("Unable to combine if sub-filters are empty");
                        if (filterReq.getColumn() != null || filterReq.getValue() != null)
                            throw new BadRequestException(
                                    "Column and value should not be provided when combining sub-filters");
                    }
                    default -> throw new BadRequestException("Unknown filter operation: " + filterReq.getOperation());
                }
                if (subPredicate != null)
                    predicate = subPredicate;

                if (filterReqs.size() <= 1) {
                    return predicate;
                }

                if (filterReq.getLogicalOperation().equals(FilterReqDTO.LogicalOperation.AND))
                    andPredicates.add(predicate);
                else
                    orPredicates.add(predicate);
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
