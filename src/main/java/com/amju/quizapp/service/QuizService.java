package com.amju.quizapp.service;

import com.amju.quizapp.dao.QuestionDao;
import com.amju.quizapp.dao.QuizDao;
import com.amju.quizapp.model.Question;
import com.amju.quizapp.model.QuestionWrapper;
import com.amju.quizapp.model.Quiz;
import com.amju.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);
        for (Question ques:questions)
        {
            System.out.println(ques.getQuestionTitle());
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB)
        {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> reponses) {
        System.out.println("scsc");
        Quiz quiz = quizDao.findById(id).get();

        List<Question> questions = quiz.getQuestions();
        Integer right = 0;
        int i = 0;

        for(Response response : reponses) {
            if (response.getResponse().equals(questions.get(i).getRight_answer()))
                right++;

            i++;
        }
        return new ResponseEntity<Integer>(right, HttpStatus.OK);
    }
}
