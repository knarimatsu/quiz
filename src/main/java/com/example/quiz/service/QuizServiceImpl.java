package com.example.quiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;

public class QuizServiceImpl implements QuizService{
    
    @Autowired
    QuizRepository repository;
    @Override
    public Iterable<Quiz> selectAll() {
        return repository.findAll();
    }

    @Override
    /** 選択したidの値を表示 */
    public Optional<Quiz> selectOneById(Integer id) {
        return repository.findById(id);
    }

    @Override
    /** ランダムに表示 */
    public Optional<Quiz> selectOneRandomQuiz() {
        Integer randId = repository.getRandomId();
        if (randId == null) {
            return Optional.empty();
        }
        return repository.findById(randId);
    }

    @Override
    /** 正誤判定 */
    public Boolean checkQuiz(Integer id, Boolean myAnswer) {
        Boolean check = false;
        Optional<Quiz> optQuiz = repository.findById(id);
        if (optQuiz.isPresent()) {
            Quiz quiz = optQuiz.get();
            if (quiz.getAnswer().equals(myAnswer)) {
                check = true;
            }
        }
        return check;
    }

    @Override
    public void insertQuiz(Quiz quiz) {
        repository.save(quiz);
    }

    @Override
    public void updateQuiz(Quiz quiz) {
        repository.save(quiz);
    }

    @Override
    public void deleteQuiz(Integer id) {
        repository.deleteById(id);
    }

}
