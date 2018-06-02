package lab.controller;


import lab.dao.interfaces.TasksDAO;
import lab.entities.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping("/")
public class TasksManagingController {
    
    @Autowired
    private TasksDAO tasksDAO;
    
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createMeeting(@RequestBody TaskEntity taskEntity) {
        tasksDAO.create(taskEntity);
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public TaskEntity getMeeting(@PathVariable("id") int id) {
        return tasksDAO.read(id);
    }
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<TaskEntity> getMeetings() throws Exception {
        return tasksDAO.readAll(0, 50);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public void updateMeeting(@RequestBody TaskEntity taskEntity) {
        tasksDAO.update(taskEntity);
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteMeeting(@PathVariable("id") int id) {
        TaskEntity taskEntity = tasksDAO.read(id);
        tasksDAO.delete(taskEntity);
    }
    
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.GONE);
    }
}
