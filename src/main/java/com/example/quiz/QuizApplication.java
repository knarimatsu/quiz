package com.example.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;

@SpringBootApplication
public class QuizApplication {

	/**
	 * 起動メソッド
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(
			QuizApplication.class, args)			.getBean(QuizApplication.class)
			.execute();

	}

	/**
	 * 注入
	*/
	@Autowired
	QuizRepository repository;
	/**
	 * 実行メソッド
	 */
	private void execute() {
		// setup();
		// showList();
	}

	/**
	 * === 全件取得 ===
	 */
	private void showList() {
		System.out.println("全件取得開始");
		Iterable<Quiz> quizzes = repository.findAll();
		for (Quiz quiz : quizzes) {
			System.out.println(quiz);
		}
		System.out.println("全権取得完了");
	}

	/** データ登録 */
	private void setup() {
		// quiz1のエンティティ(データ1の内容)作成
		Quiz quiz1 = new Quiz(
			null,
			"Springはフレームワークですか",
			true,
			"登録太郎"
		);

		// 作成したエンティティを保存する
		quiz1 = repository.save(quiz1);

		// 保存確認
		System.out.println("登録したデータは、" + quiz1 + "でず。");

		// quiz2のエンティティ(データ2の内容)作成
		Quiz quiz2 = new Quiz(
			null,
			"Spring MVCはバッチ処理を提供しますか?",
			false,
			"登録太郎"
		);
		
		// 保存
		quiz2 = repository.save(quiz2);
		
		// 登録確認
		System.out.println("登録したデータは" + quiz2 + "です");
	}
}
