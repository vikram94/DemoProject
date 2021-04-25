package com.beehyv.DemoProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("mostused/{id}")
    public ResponseEntity<String> findUserMostUsedById(@PathVariable(value = "id") long id) {
    	return ResponseEntity.ok().body(userService.findUserMostUsedById(id));
    }
    
    @PostMapping("evaluate/{id}")
    public String calculateAndUpdateUser(@PathVariable(value = "id") long id,  @RequestBody Expression expression) {
        // Implement
    	return userService.calculateAndUpdateUser(id, expression);
    }
}