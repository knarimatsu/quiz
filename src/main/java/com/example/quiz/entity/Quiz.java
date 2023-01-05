package com.example.quiz.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    
    /** 識別ID (主キー) */
    @Id // <- 主キーの場合このアノテーションを入れる
    private Integer id;

    /** クイズの内容 */
    private String question;

    /** クイズの回答 */
    private Boolean answer;

    /** 作成者 */
    private String author;
}
