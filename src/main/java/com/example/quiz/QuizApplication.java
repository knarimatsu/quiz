package com.example.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.lang.model.element.QualifiedNameable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizRepository;
import com.example.quiz.service.QuizService;

@SpringBootApplication
public class QuizApplication {

	/**
	 * 起動メソッド
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
			// .getBean(QuizApplication.class)
			// .execute();

	}

	/** 注入 */
	@Autowired
	QuizService service;

	/** 実行メソッド */
	private void execute() {
		// 保存
		// setup();
		// 全権取得
		// showList();
		// 1件取得
		// showOne(1);
		// 更新
		// updateQuiz(2);
		// 削除
		// deleteQuiz(2);
		// クイズを実行する
		// doQuiz();
	}

	private void deleteQuiz(Integer id) {
		System.out.println("削除開始");
		service.deleteQuiz(id);
		System.out.println("削除完了");
	}

	/** データの更新処理 */
	private void updateQuiz(Integer id) {
		System.out.println("データ更新開始");
		Quiz quiz1 = new Quiz(
			id,
		"Springはフレームワークでしょうか",
		true,
		"変更太郎"
		);
		service.updateQuiz(quiz1);
		System.out.println("変更されたデータは" + quiz1 + "です");
		System.out.println("変更しました");
	}

	/** 1件取得 */
	private void showOne(Integer id) {
		System.out.println("データ取得開始");
		// データ取得
		Optional<Quiz> quizOpt = service.selectOneById(id);
		
		// データ存在チェック
		if (quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		} else {
			System.out.println("データが存在しません");
		}
		System.out.println("データ取得終了");
	}

	/** === 全件取得 === */
	private void showList() {
		System.out.println("全件取得開始");
		Iterable<Quiz> quizzes = service.selectAll();
		for (Quiz quiz : quizzes) {
			System.out.println(quiz);
		}
		System.out.println("全権取得完了");
	}

	/** === データ登録 === */
	private void setup() {
		System.out.println("登録処理開始");
		// quiz1のエンティティ(データ1の内容)作成
		Quiz quiz1 = new Quiz(
			null,
			"Springはフレームワークですか",
			true,
			"登録太郎"
		);

		// quiz2のエンティティ(データ2の内容)作成
		Quiz quiz2 = new Quiz(
			null,
			"Spring MVCはバッチ処理を提供しますか?",
			false,
			"登録太郎"
		);

		Quiz quiz3 = new Quiz(
			null,
			"3つ目のデータ",
			false, 
			"登録太郎"
		);

		Quiz quiz4 = new Quiz(
			null,
			"4つ目のデータ",
			true,
			"登録太郎"
		);

		Quiz quiz5 = new Quiz(
			null,
			"5つ目のデータ",
			false,
			"登録太郎"
		);

		List<Quiz> quizList = new ArrayList<>();
		Collections.addAll(quizList, quiz1, quiz2, quiz3, quiz4, quiz5);

		for (Quiz quiz : quizList) {
			service.insertQuiz(quiz);
		}
		System.out.println("登録完了しました");
	}

	/** ランダムでクイズを取得して、クイズの正解不正解を判定 */
	private void doQuiz() {
		System.out.println("クイズ1件取得開始");
		// リポジトリを使って1件取得を実施、結果を取得
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		// 値存在チェック
		if (quizOpt.isPresent()) {
			System.out.println(quizOpt.get());
		} else {
			System.out.println("問題が存在しません");
		}
		System.out.println("クイズ1件取得完了");
		// 解答を実施
		Boolean myAnswer = false;
		Integer id = quizOpt.get().getId();
		if (service.checkQuiz(id, myAnswer)) {
			System.out.println("正解です");
		} else {
			System.out.println("不正解です");
		}
	}
}
