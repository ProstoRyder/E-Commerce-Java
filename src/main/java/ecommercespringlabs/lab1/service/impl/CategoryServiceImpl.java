package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.service.CategoryService;
import ecommercespringlabs.lab1.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    List<Category> categories = new ArrayList<>();

    public CategoryServiceImpl() {
        categories.add(Category.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).title("Galaxies").build());
        categories.add(Category.builder().id(UUID.fromString("123e4512-e89b-12d3-a456-426685390400")).title("Planets").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Stars").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Black Holes").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Asteroids").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Nebulae").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Space Missions").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Satellites").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Space Technology").build());
        categories.add(Category.builder().id(UUID.randomUUID()).title("Astronomy").build());
    }

    @Override
    public List<Category> findAllCategories() {
        return categories;
    }

    @Override
    public Category findCategoryById(String id) {
        return categories.stream().filter(category -> category.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category addCategory(CategoryRequestDto categoryRequestDto) {
        Category category = Category.builder()
                .id(UUID.randomUUID())
                .title(categoryRequestDto.getTitle())
                .build();
        categories.add(category);
        return category;
    }

    @Override
    public String deleteCategory(String id) {
        Category category = findCategoryById(id);
        categories.remove(category);
        return "Category with id " + id + " was deleted";
    }

    @Override
    public Category updateCategory(CategoryRequestDto categoryRequestDto, String id) {
        Category category = findCategoryById(id);
        category.setTitle(categoryRequestDto.getTitle());
        return category;
    }


}
