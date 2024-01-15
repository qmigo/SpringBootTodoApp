package com.ankur.myfirstwebproject.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
	private static List<Todo> todos = new ArrayList<Todo>();
	private static int id = 0;
	static {
		todos.add(new Todo(++id,"admin","Get a sexy body 1", LocalDate.now().plusMonths(2), false));
		todos.add(new Todo(++id,"admin","Run 5 marathon 1", LocalDate.now().plusMonths(5), false));
		todos.add(new Todo(++id,"admin","learn Bike 1", LocalDate.now().plusMonths(1), false));
	}
	
	public List<Todo> getByUsername(String username) {		
		Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	
	public void addTodo(String username, String description, LocalDate targetDate) {
		todos.add(new Todo(++id, username, description, targetDate, false));
	} 

	public void deleteTodoById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate);
	}

	public void updateTodoById(int id, String description, LocalDate targetDate) {
		
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		todos.stream().filter(predicate).findFirst().ifPresent(
				todo -> {
					todo.setDescription(description);
					todo.setTargetDate(targetDate);
				});	
	}

	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo-> todo.getId() == id;
		return todos.stream().filter(predicate).findFirst().get();
	}
	
}
