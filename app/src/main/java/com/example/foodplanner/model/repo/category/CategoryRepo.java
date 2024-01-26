package com.example.foodplanner.model.repo.category;

import com.example.foodplanner.api.CategoryCallback;

public interface CategoryRepo {
    void getCategories(CategoryCallback categoryCallback);

}
