package com.example.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
public class CustomMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public void sendMail(String to, String msg) throws MessagingException, IOException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		// 메일 제목 설정
		helper.setSubject("[PickHotel]인증번호를 확인하세요!");
		// 수신자 설정
		helper.setTo(to);
		// 템플릿에 전달할 데이터 설정
		Context context = new Context();
		context.setVariable("test_key", msg);
		// 메일 내용 설정 : 템플릿 프로세스
		String html = templateEngine.process("page/sendMail", context);
		helper.setText(html, true);

		// 메일 보내기
		javaMailSender.send(message);
	}
}