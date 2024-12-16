package net.javaguides.expense.controller;

import net.javaguides.expense.dto.ExpenseDto;
import net.javaguides.expense.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    //inject the ExpenseServivce using constructor based DI
    private ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    //build create expense REST API
    @PostMapping
    public ResponseEntity<ExpenseDto>  createExpense(@RequestBody ExpenseDto expenseDto){
        ExpenseDto savedExpense = expenseService.createExpense(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    //Build get expensebyid REST API
    @GetMapping("{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long expenseId){
        ExpenseDto expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    //build get all expenses Rest Api
    @GetMapping
    public ResponseEntity<List<ExpenseDto>>  getAllExpenses(){
        List<ExpenseDto> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }

    //build update expense REST API
    @PutMapping("{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") Long expenseId,@RequestBody ExpenseDto expenseDto){
        ExpenseDto updatedExpense = expenseService.updateExpense(expenseId, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    //build delete REST API

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense deleted successfully");
    }
}
