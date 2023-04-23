package com.miu.onlinemarketplace.service.domain.search;

import com.miu.onlinemarketplace.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<Product> advanceSearch(String name, String categoryName, Double minPrice, Double maxPrice, String sortedPrice, Pageable pageable) {
        double minPriceValue = minPrice != null ? minPrice : 0.0;
        double maxPriceValue = maxPrice != null ? maxPrice : 0.0;

        String queryString = "SELECT p FROM Product p JOIN p.productCategory c";
        boolean whereClauseAdded = false;
        if (name != null && !name.isEmpty()) {
            queryString += " WHERE p.name LIKE CONCAT('%', :name, '%')";
            whereClauseAdded = true;
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            if (whereClauseAdded) {
                queryString += " AND c.category LIKE CONCAT('%', :categoryName, '%')";
            } else {
                queryString += " WHERE c.category LIKE CONCAT('%', :categoryName, '%')";
                whereClauseAdded = true;
            }
        }
        if (minPriceValue > 0 && maxPriceValue > 0) {
            if (whereClauseAdded) {
                queryString += " AND p.price BETWEEN :minPriceValue AND :maxPriceValue";
            } else {
                queryString += " WHERE p.price BETWEEN :minPriceValue AND :maxPriceValue";
            }
        } else if (minPriceValue > 0) {
            if (whereClauseAdded) {
                queryString += " AND p.price >= :minPriceValue";
            } else {
                queryString += " WHERE p.price >= :minPriceValue";
            }
        } else if (maxPriceValue > 0) {
            if (whereClauseAdded) {
                queryString += " AND p.price <= :maxPriceValue";
            } else {
                queryString += " WHERE p.price <= :maxPriceValue";
            }
        }
        if (sortedPrice != null && !sortedPrice.isEmpty()) {
            if (sortedPrice.equals("ASC")) {
                queryString += " ORDER BY p.price ASC";
            } else {
                queryString += " ORDER BY p.price DESC";
            }

        }

        TypedQuery<Product> query = entityManager.createQuery(queryString, Product.class);
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", name);
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            query.setParameter("categoryName", categoryName);
        }
        if (minPriceValue > 0 && maxPriceValue > 0) {
            query.setParameter("minPriceValue", minPriceValue);
            query.setParameter("maxPriceValue", maxPriceValue);
        } else if (minPriceValue > 0) {
            query.setParameter("minPriceValue", minPriceValue);
        } else if (maxPriceValue > 0) {
            query.setParameter("maxPriceValue", maxPriceValue);
        }
//        if (sortedPrice != null && !sortedPrice.isEmpty()) {
//            query.setParameter("sortedPrice", sortedPrice);
//        }
        int totalCount = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Product> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, totalCount);
    }
}
