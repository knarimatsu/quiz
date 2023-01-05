package com.example.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.quiz.entity.Quiz;

/**
 * Quizテーブル: RepositoryImpl 
 * リポジトリはインターフェースであり、データベースの操作だけをこの中に定義する
 * CrudRepositoryの定義
*/
public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    
}