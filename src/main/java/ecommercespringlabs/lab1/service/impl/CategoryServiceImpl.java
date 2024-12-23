package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.Category;
import ecommercespringlabs.lab1.dto.category.CategoryRequestDto;
import ecommercespringlabs.lab1.repository.CategoryRepository;
import ecommercespringlabs.lab1.repository.entity.CategoryEntity;
import ecommercespringlabs.lab1.service.CategoryService;
import ecommercespringlabs.lab1.service.exception.CategoryNotFoundException;
import ecommercespringlabs.lab1.service.mapper.CategoryMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    @Transactional
    public Category addCategory(CategoryRequestDto categoryRequestDto) {
        CategoryEntity newCategory = CategoryEntity.builder().title(categoryRequestDto.getTitle()).category_reference(UUID.randomUUID()).build();
        try {
            return categoryMapper.toCategory(categoryRepository.save(newCategory));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryMapper.toCategoryList(categoryRepository.findAll());
    }


    @Override
    @Transactional
    public Category findCategoryById(UUID id) {
        CategoryEntity category = categoryRepository.findByNaturalId(id)
                .orElseThrow(() -> new CategoryNotFoundException(id.toString()));
        return categoryMapper.toCategory(category);
    }


    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        findCategoryById(id);
        try {
            categoryRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Category updateCategory(CategoryRequestDto categoryRequestDto,UUID id) {
        CategoryEntity category = categoryRepository.findByNaturalId(id).orElseThrow(() -> new CategoryNotFoundException(id.toString()));
        category.setCategory_reference(id);

        category.setTitle(categoryRequestDto.getTitle());

        try {
            return categoryMapper.toCategory(categoryRepository.save(category));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }


}
