package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(String id);
    Category addCategory(CategoryRequestDto categoryRequestDto);
    String deleteCategory(String id);
    Category updateCategory(CategoryRequestDto categoryRequestDto, String id);
}
