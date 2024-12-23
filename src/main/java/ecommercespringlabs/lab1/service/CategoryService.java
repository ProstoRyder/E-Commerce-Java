package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(UUID id);
    Category addCategory(CategoryRequestDto categoryRequestDto);
    void deleteCategory(UUID id);
    Category updateCategory(CategoryRequestDto categoryRequestDto, UUID id);
}
