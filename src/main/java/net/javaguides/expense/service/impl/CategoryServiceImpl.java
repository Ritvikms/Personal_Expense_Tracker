package net.javaguides.expense.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.expense.dto.CategoryDto;
import net.javaguides.expense.entity.Category;
import net.javaguides.expense.exception.ResourceNotFoundException;
import net.javaguides.expense.mapper.CategoryMapper;
import net.javaguides.expense.repository.CategoryRepository;
import net.javaguides.expense.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {


    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        //convert CategoryDto to Category entity
        Category category = CategoryMapper.mapToCategory(categoryDto);

        // save category object into database table - categories
        Category savedCategory = categoryRepository.save(category);

        // convert saveCategory to CategoryDto
            return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id : "+ categoryId));
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map((category) -> CategoryMapper.mapToCategoryDto(category))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {

        //get  category entity from the database by category id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id :" + categoryId));

        //update the category  entity object and save to database table
        category.setName(categoryDto.name());
        Category updatedCategory = categoryRepository.save(category);  // performs update operations
        return CategoryMapper.mapToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        //check if a category with given id exists in the database
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id :" + categoryId));

        categoryRepository.delete(category);

    }
}
