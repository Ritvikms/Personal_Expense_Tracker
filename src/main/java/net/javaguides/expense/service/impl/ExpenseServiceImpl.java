package net.javaguides.expense.service.impl;

import net.javaguides.expense.dto.ExpenseDto;
import net.javaguides.expense.entity.Category;
import net.javaguides.expense.entity.Expense;
import net.javaguides.expense.exception.ResourceNotFoundException;
import net.javaguides.expense.mapper.ExpenseMapper;
import net.javaguides.expense.repository.CategoryRepository;
import net.javaguides.expense.repository.ExpenseRepository;
import net.javaguides.expense.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;



    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {

        // Convert expense Dto to expense entity
        Expense expense = ExpenseMapper.mapToExpense(expenseDto);

        //save entity to database
        Expense savedExpense = expenseRepository.save(expense);

        //convert saved expense entity into ExpenseDto

        return ExpenseMapper.mapToExpenseDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long expenseId) {

        //get expense entity using expense id
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+ expenseId));

        //convert expense entity to expenseDto
        return ExpenseMapper.mapToExpenseDto(expense);

    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();

        return expenses.stream()
                .map((expense) -> ExpenseMapper.mapToExpenseDto(expense))
                .collect(Collectors.toList());

    }

    @Override
    public ExpenseDto updateExpense(Long expenseId, ExpenseDto expenseDto) {

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id :"+ expenseId));

        //update expense amount
        expense.setAmount(expenseDto.amount());

        //update expense date
        expense.setExpenseDate(expenseDto.expenseDate());

        //update category
        if(expenseDto.categoryDto()!=null){
            //get the category entity by Id
            Category category = categoryRepository.findById(expenseDto.categoryDto().id())
                    .orElseThrow(() ->  new ResourceNotFoundException("Category not found with id : "+expenseDto.categoryDto().id()));
            expense.setCategory(category);
        }

        //update expense entity
        Expense updatedExpense = expenseRepository.save(expense);

        // convert expense entity into ExpenseDto
        return ExpenseMapper.mapToExpenseDto(updatedExpense);
    }

    @Override
    public void deleteExpense(Long expenseId) {
        //get the expense from the database by expense id if it does not exists then trhow exception
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: "+ expenseId));

        expenseRepository.delete(expense);
    }
}
