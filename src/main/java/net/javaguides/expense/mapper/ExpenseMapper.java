package net.javaguides.expense.mapper;

import net.javaguides.expense.dto.CategoryDto;
import net.javaguides.expense.dto.ExpenseDto;
import net.javaguides.expense.entity.Category;
import net.javaguides.expense.entity.Expense;

public class ExpenseMapper {
    //map Expense entity to expenseDto
    public static ExpenseDto mapToExpenseDto(Expense expense){
        return new ExpenseDto(
                expense.getId(),
                expense.getAmount(),
                expense.getExpenseDate(),
                new CategoryDto(
                        expense.getCategory().getId(),
                        expense.getCategory().getName()
                )
        );
    }

    // map expenseDto to expense entity
    public static Expense mapToExpense(ExpenseDto expenseDto){
        Category category = new Category();
        category.setId(expenseDto.categoryDto().id());

       return new  Expense(
                expenseDto.id(),
                expenseDto.amount(),
                expenseDto.expenseDate(),
                category
        );

    }
}
