package com.example.quiz.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;
import com.example.quiz.service.QuizService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    /** DI対象 */
    @Autowired
    QuizService service;

    /** form-backing bean の初期化 */
    @ModelAttribute
    public QuizForm setUpForm() {
        QuizForm form = new QuizForm();
        // ラジオボタンのデフォルト値設定
        form.setAnswer(true);
        return form;
    }

    /** Quizの一覧を表示します */
    @GetMapping
    public String showQuizList(QuizForm quizForm, Model model) {
        quizForm.setNewQuiz(true);
        Iterable<Quiz> list = service.selectAll();
        model.addAttribute("list", list);
        model.addAttribute("title", "登録用フォーム");
        return "crud";
    }

    @PostMapping("/insert")
    public String insert(
        @Validated QuizForm quizForm,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
        ) {
            Quiz quiz = new Quiz();
            quiz.setQuestion(quizForm.getQuestion());
            quiz.setAnswer(quizForm.getAnswer());
            quiz.setAuthor(quizForm.getAuthor());

            if (!bindingResult.hasErrors()) {
                service.insertQuiz(quiz);
                redirectAttributes.addFlashAttribute(
                    "complete",
                    "登録が完了しました"
                );
                return "redirect:/quiz";
            } else {
                return showQuizList(quizForm, model);
            }
    }
    @GetMapping("/{id}")
    public String showUpdate(
        QuizForm quizForm,
        @PathVariable Integer id,
        Model model
    ) {
        Optional<Quiz> quizOpt = service.selectOneById(id);

        Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
        if (quizFormOpt.isPresent()) {
            quizForm = quizFormOpt.get();
        }
        makeUpdateModel(quizForm, model);
        return "crud";
    }

    @PostMapping("/update")
    public String update(
        @Validated QuizForm quizForm,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        Quiz quiz = makeQuiz(quizForm);
        if (!bindingResult.hasErrors()) {
            service.updateQuiz(quiz);
            return "redirect:/quiz" + quiz.getId();
        } else {
            makeUpdateModel(quizForm, model);
            return "crud";
        }
    }

    @PostMapping("/delete")
    public String delete(
        @RequestParam("id") String id,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        service.deleteQuiz(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("delcomplete", "削除か完了しました");
        return "redirect:/quiz";
    }

    @GetMapping("/play")
    public String showQuiz(
        QuizForm quizForm,
        Model model
    ) {
        Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
        if (quizOpt.isPresent()) {
            Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
            quizForm = quizFormOpt.get();
        } else {
            model.addAttribute("msg", "問題がありません");
            return "play";
        }
        model.addAttribute("quizForm", quizForm);
        return "play";
    }

    @PostMapping("/check")
    public String checkQuiz(
        QuizForm quizForm,
        @RequestParam Boolean answer,
        Model model
    ) {
        if (service.checkQuiz(quizForm.getId(), answer)) {
            model.addAttribute("msg", "正解です");
        } else {
            model.addAttribute("msg", "不正解です");
        }
        return "answer";
    }

    private void makeUpdateModel(QuizForm quizForm, Model model) {
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizFrom", quizForm);
        model.addAttribute("title", "更新用フォーム");
    }

    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm quizForm = new QuizForm();
        quizForm.setId(quiz.getId());
        quizForm.setQuestion(quiz.getQuestion());
        quizForm.setAnswer(quiz.getAnswer());
        quizForm.setAuthor(quiz.getAuthor());
        return quizForm;
    }



}
