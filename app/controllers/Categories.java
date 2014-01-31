package controllers;

import controllers.actions.SaveContext;
import io.sphere.client.ProductSort;
import io.sphere.client.facets.Facet;
import io.sphere.client.facets.Facets;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.Filter;
import io.sphere.client.filters.Filters;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.filters.expressions.FilterExpressions;
import io.sphere.client.model.SearchResult;
import io.sphere.client.shop.model.Category;
import io.sphere.client.shop.model.Product;
import play.mvc.Result;
import play.mvc.With;
import sphere.ShopController;
import sphere.SearchRequest;
import views.html.products;
import views.html.productSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categories extends ShopController {

    public static String QUERY_NAME = "q";
    public static String QUERY_COLOR = "color";
    public static String QUERY_PRICE = "price";

    @With(SaveContext.class)
    public static Result select(String categorySlug, int page, int show, String sort, String list) {
        Category category = sphere().categories().getBySlug(categorySlug);
        if (category == null) {
            return notFound("Category not found: " + categorySlug);
        }
        FilterExpression categoryFilter = new FilterExpressions.CategoriesOrSubcategories(Collections.singletonList(category));
        SearchRequest <Product> searchRequest = sphere().products().filter(lang().toLocale(), categoryFilter);
        searchRequest = filterBy(searchRequest);
        searchRequest = sortBy(searchRequest, sort);
        searchRequest = paging(searchRequest, page, show);
        SearchResult<Product> searchResult = searchRequest.fetch();
        if (searchResult.getCount() < 1) {
            flash("product-list-info", "No products found");
        }
        return ok(products.render(category, searchResult, list.isEmpty()));
    }

    @With(SaveContext.class)
    public static Result search(int page, int show, String sort, String list) {
        SearchRequest <Product> searchRequest = sphere().products().all(lang().toLocale());
        searchRequest = filterBy(searchRequest);
        searchRequest = sortBy(searchRequest, sort);
        searchRequest = paging(searchRequest, page, show);
        SearchResult<Product> searchResult = searchRequest.fetch();
        if (searchResult.getCount() < 1) {
            flash("product-list-info", "No products found");
        }
        return ok(productSearch.render(searchResult, list.isEmpty()));
    }

    protected static SearchRequest<Product> filterBy(SearchRequest<Product> searchRequest) {
        // Filters
        List<Filter> filterList = new ArrayList<Filter>();
        // By price
        Filters.Price.DynamicRange filterPrice = new Filters.Price.DynamicRange().setQueryParam(QUERY_PRICE);
        filterList.add(filterPrice);
        // By name
        Filters.Fulltext filterName = new Filters.Fulltext().setQueryParam(QUERY_NAME);
        filterList.add(filterName);
        // Build request
        List<FilterExpression> filterExp = bindFiltersFromRequest(filterList);
        searchRequest = searchRequest.filter(filterExp);

        // Facets
        List<Facet> facetList = new ArrayList<Facet>();
        // By color
        Facets.StringAttribute.Terms facetColor = new Facets.StringAttribute.Terms("variants.attributes.color").setQueryParam(QUERY_COLOR);
        facetList.add(facetColor);
        // Build request
        List<FacetExpression> facetExp = bindFacetsFromRequest(facetList);
        searchRequest = searchRequest.facet(facetExp);

        return searchRequest;
    }

    protected static SearchRequest<Product> sortBy(SearchRequest<Product> searchRequest, String sort) {
        if (sort.equals("desc")) {
            searchRequest = searchRequest.sort(ProductSort.price.desc);
        } else if (sort.equals("asc")) {
            searchRequest = searchRequest.sort(ProductSort.price.asc);
        } else {
            searchRequest = searchRequest.sort(ProductSort.name.asc);
        }
        return searchRequest;
    }

    protected static SearchRequest<Product> paging(SearchRequest<Product> searchRequest, int currentPage, int pageSize) {
        if (currentPage < 1) currentPage = 1;
        if (pageSize != 24) pageSize = 12;
        // Convert page from 1..N to 0..N-1
        currentPage--;
        return searchRequest.page(currentPage).pageSize(pageSize);
    }


}
