package com.amju.quizapp.controller;

import com.amju.quizapp.model.Question;
import com.amju.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allquestion")
    public ResponseEntity<List<Question>> getAllQuestions(){
    return questionService.getAllQuestions();

    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category)
    {
        return questionService.getQuestionsByCategoey(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addquestion(question);
    }

}
