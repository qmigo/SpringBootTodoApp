package com.ankur.myfirstwebproject.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("username")
public class TodoController {
	
	private TodoService todoService;
	
	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	
	private String getLoggedinUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@RequestMapping("list-todos")
	public String getTodos(ModelMap model) {
		
		String username = getLoggedinUsername();
		List<Todo> todos = todoService.getByUsername(username);
		model.put("todos", todos);
		return "listTodos";
	} 
	
	@RequestMapping(value="add-todo", method = RequestMethod.GET)
	public String goToAddTodoPage(ModelMap model) {
		Todo todo = new Todo(0,"","",LocalDate.now().plusDays(1),false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		
		if(result.hasErrors()) {
			return "todo";
		}

		String username = getLoggedinUsername();
		todoService.addTodo(username, todo.getDescription(), todo.getTargetDate());			
	
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="delete-todo", method = RequestMethod.GET) 
	public String deleteTodo(@RequestParam int id) {
		
		todoService.deleteTodoById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.GET) 
	public String goToUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Todo todo = todoService.findById(id);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.POST) 
	public String updateTodo( @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			System.out.println(todo);
			return "todo";
		}
		
		todoService.updateTodoById(todo.getId(), todo.getDescription(), todo.getTargetDate());
		return "redirect:list-todos";
	}
}
