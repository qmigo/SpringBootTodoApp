package com.ankur.myfirstwebproject.todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class TodoControllerJpa {
	
	private TodoRepository todoRepository;
	
	public TodoControllerJpa(TodoRepository todoRepository) {
		super();
		this.todoRepository = todoRepository;
	}
	
	private String getLoggedinUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@RequestMapping("list-todos")
	public String getTodos(ModelMap model) {
		
		String username = getLoggedinUsername();
		List<Todo> todos = todoRepository.findByUsername(username);
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
		todo.setUsername(username);
		todoRepository.save(todo);
	
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="delete-todo", method = RequestMethod.GET) 
	public String deleteTodo(@RequestParam int id) {
		
		todoRepository.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.GET) 
	public String goToUpdateTodoPage(@RequestParam int id, ModelMap model) {

		Todo todo = todoRepository.findById(id).orElse(null);
		model.put("todo", todo);
		return "todo";
	}
	
	@Transactional
	@RequestMapping(value="update-todo", method = RequestMethod.POST) 
	public String updateTodo( @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			System.out.println(todo);
			return "todo";
		}
		
		Todo previousTodo = todoRepository.findById(todo.getId()).orElse(null);
		previousTodo.setDescription(todo.getDescription());
		previousTodo.setTargetDate(todo.getTargetDate());

		return "redirect:list-todos";
	}
}
