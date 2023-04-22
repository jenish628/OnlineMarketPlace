package com.miu.onlinemarketplace.service.domain.search;

import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.repository.SearchRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<Product> advanceSearch(String name, String categoryName, Double minPrice, Double maxPrice,String sortedPrice, Pageable pageable) {
        String queryString = "SELECT p FROM Product p INNER JOIN p.productCategory c";
        boolean whereClauseAdded = false;
        if (name != null && !name.isEmpty()) {
            queryString += " WHERE p.name LIKE CONCAT('%', :name, '%')";
            whereClauseAdded = true;
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            if (whereClauseAdded) {
                queryString += " OR c.category LIKE CONCAT('%', :categoryName, '%')";
            } else {
                queryString += " WHERE c.category LIKE CONCAT('%', :categoryName, '%')";
                whereClauseAdded = true;
            }
        }
        if (minPrice > 0 && maxPrice > 0) {
            if (whereClauseAdded) {
                queryString += " OR p.price BETWEEN :minPrice AND :maxPrice";
            } else {
                queryString += " WHERE p.price BETWEEN :minPrice AND :maxPrice";
            }
        } else if (minPrice > 0) {
            if (whereClauseAdded) {
                queryString += " OR p.price >= :minPrice";
            } else {
                queryString += " WHERE p.price >= :minPrice";
            }
        } else if (maxPrice > 0) {
            if (whereClauseAdded) {
                queryString += " OR p.price <= :maxPrice";
            } else {
                queryString += " WHERE p.price <= :maxPrice";
            }
        }

        TypedQuery<Product> query = entityManager.createQuery(queryString, Product.class);
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", name);
        }
        if (categoryName != null && !categoryName.isEmpty()) {
            query.setParameter("categoryName", categoryName);
        }
        if (minPrice > 0 && maxPrice > 0) {
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
        } else if (minPrice > 0) {
            query.setParameter("minPrice", minPrice);
        } else if (maxPrice > 0) {
            query.setParameter("maxPrice", maxPrice);
        }
        int totalCount = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        List<Product> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, totalCount);
    }
}
