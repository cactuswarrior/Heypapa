package com.ssafy.heypapa.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 나의 퀴즈 모델 정의.
 */

@Entity
@Getter
@Setter
public class MyQuiz extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "quiz_id")
	private Quiz quiz; // 퀴즈 id
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // 유저 id
	
	private boolean check; // 퀴즈 확인용
	private boolean like; // 퀴즈 좋아요
	
}