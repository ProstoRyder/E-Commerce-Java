package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.dto.category.CategoryResponseDto;
import ecommercespringlabs.lab1.service.CategoryService;
import ecommercespringlabs.lab1.service.mapper.CategoryMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
@Validated
public class CategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(categoryService.findCategoryById(categoryId)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDtoList(categoryService.findAllCategories()));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(categoryService.addCategory(categoryRequestDto)));
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto, @PathVariable UUID categoryId) {
        Category category = categoryService.updateCategory(categoryRequestDto, categoryId);
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDto(category));
    }
}