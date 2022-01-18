-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        5.7.35-log - MySQL Community Server (GPL)
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- pichoteldb 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `pichoteldb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pichoteldb`;

-- 테이블 pichoteldb.alarm 구조 내보내기
CREATE TABLE IF NOT EXISTS `alarm` (
  `al_no` int(11) NOT NULL AUTO_INCREMENT,
  `al_type` char(1) DEFAULT NULL COMMENT '알림유형 (1: 새 댓글, 2: 등업, 3: 이벤트 당첨, 4: 게시글 신고, 5: 댓글 신고)',
  `al_read` tinyint(1) DEFAULT '0',
  `al_content` text COMMENT '알림내용',
  `al_url` text COMMENT '알림 랜딩 페이지',
  `mb_no` int(11) NOT NULL COMMENT '회원번호',
  PRIMARY KEY (`al_no`),
  KEY `FK_member_TO_alarm` (`mb_no`),
  CONSTRAINT `FK_member_TO_alarm` FOREIGN KEY (`mb_no`) REFERENCES `member` (`mb_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='알림';

-- 테이블 데이터 pichoteldb.alarm:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;

-- 테이블 pichoteldb.attach 구조 내보내기
CREATE TABLE IF NOT EXISTS `attach` (
  `atta_no` int(11) NOT NULL AUTO_INCREMENT,
  `post_no` int(11) NOT NULL COMMENT '게시글 번호',
  `atta_sysname` text COMMENT '첨부파일 저장 이름',
  `atta_realname` text COMMENT '첨부파일 실제 저장 이름',
  `atta_size` int(11) DEFAULT NULL COMMENT '첨부파일 사이즈',
  `atta_type` int(11) DEFAULT NULL COMMENT '파일 유형(1: 이미지, 2: 동영상, 3: 텍스트)',
  PRIMARY KEY (`atta_no`),
  KEY `FK_post_TO_attach` (`post_no`),
  CONSTRAINT `FK_post_TO_attach` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='첨부파일';

-- 테이블 데이터 pichoteldb.attach:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `attach` DISABLE KEYS */;
/*!40000 ALTER TABLE `attach` ENABLE KEYS */;

-- 테이블 pichoteldb.board 구조 내보내기
CREATE TABLE IF NOT EXISTS `board` (
  `bo_no` int(11) NOT NULL AUTO_INCREMENT,
  `bo_group_no` int(11) NOT NULL COMMENT '게시판 그룹 번호',
  `bo_name` varchar(255) DEFAULT NULL COMMENT '게시판 이름',
  `bo_type` enum('basic','review') DEFAULT NULL COMMENT '게시판 유형',
  `bo_use_photo` tinyint(1) DEFAULT '0',
  `bo_use_video` tinyint(1) DEFAULT '0',
  `bo_use_fupdown` tinyint(1) DEFAULT '0',
  `bo_use_comment` tinyint(1) DEFAULT '0',
  `bo_datetime` datetime DEFAULT NULL COMMENT '게시판 생성 일시',
  `bo_post` int(11) DEFAULT NULL COMMENT '게시글 갯수',
  `bo_grade` int(4) NOT NULL COMMENT '게시판 접근 등급',
  PRIMARY KEY (`bo_no`),
  KEY `FK_board_group_TO_board` (`bo_group_no`),
  KEY `FK_membergrade_TO_board` (`bo_grade`),
  CONSTRAINT `FK_board_group_TO_board` FOREIGN KEY (`bo_group_no`) REFERENCES `board_group` (`bo_group_no`),
  CONSTRAINT `FK_membergrade_TO_board` FOREIGN KEY (`bo_grade`) REFERENCES `membergrade` (`mg_grade`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='게시판';

-- 테이블 데이터 pichoteldb.board:~9 rows (대략적) 내보내기
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` (`bo_no`, `bo_group_no`, `bo_name`, `bo_type`, `bo_use_photo`, `bo_use_video`, `bo_use_fupdown`, `bo_use_comment`, `bo_datetime`, `bo_post`, `bo_grade`) VALUES
	(1, 1, '해안가 인근 숙소 추천', 'basic', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(2, 1, '조용한 숙소 추천', 'basic', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(3, 2, '호텔 리뷰', 'review', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 4),
	(4, 2, '민박 리뷰', 'review', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(5, 2, '에어비엔비 숙소 리뷰', 'review', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(6, 2, '펜션 리뷰', 'review', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(7, 3, '이벤트', 'basic', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(15, 1, '우리 동네 숙소', 'basic', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1),
	(16, 1, '삭제 테스트용 게시판', 'basic', 1, 1, 1, 1, '2021-11-30 17:04:45', 0, 1);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;

-- 테이블 pichoteldb.board_group 구조 내보내기
CREATE TABLE IF NOT EXISTS `board_group` (
  `bo_group_no` int(11) NOT NULL AUTO_INCREMENT,
  `bo_group_name` varchar(255) DEFAULT NULL COMMENT '게시판 그룹 이름',
  PRIMARY KEY (`bo_group_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='게시판 그룹';

-- 테이블 데이터 pichoteldb.board_group:~3 rows (대략적) 내보내기
/*!40000 ALTER TABLE `board_group` DISABLE KEYS */;
INSERT INTO `board_group` (`bo_group_no`, `bo_group_name`) VALUES
	(1, '숙소 톡톡'),
	(2, '숙소 리뷰'),
	(3, '일반 게시판');
/*!40000 ALTER TABLE `board_group` ENABLE KEYS */;

-- 테이블 pichoteldb.comment 구조 내보내기
CREATE TABLE IF NOT EXISTS `comment` (
  `co_no` int(11) NOT NULL AUTO_INCREMENT,
  `post_no` int(11) NOT NULL COMMENT '게시글 번호',
  `co_mb_no` int(11) NOT NULL COMMENT '작성자 회원번호',
  `co_content` text COMMENT '내용',
  `co_class` int(11) NOT NULL DEFAULT '0' COMMENT '댓글 계층',
  `co_order` int(11) NOT NULL DEFAULT '0' COMMENT '댓글과 대댓글 순서',
  `co_p_no` int(11) NOT NULL DEFAULT '0' COMMENT '댓글 그룹',
  `co_p_mb_no` int(11) NOT NULL DEFAULT '0' COMMENT '대댓글 회원pk',
  `co_isblind` tinyint(1) NOT NULL DEFAULT '0',
  `co_datetime` datetime DEFAULT NULL COMMENT '댓글 작성일',
  PRIMARY KEY (`co_no`),
  KEY `FK_member_TO_comment` (`co_mb_no`),
  CONSTRAINT `FK_member_TO_comment` FOREIGN KEY (`co_mb_no`) REFERENCES `member` (`mb_no`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='댓글';

-- 테이블 데이터 pichoteldb.comment:~29 rows (대략적) 내보내기
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`co_no`, `post_no`, `co_mb_no`, `co_content`, `co_class`, `co_order`, `co_p_no`, `co_p_mb_no`, `co_isblind`, `co_datetime`) VALUES
	(5, 2, 1, '1', 0, 0, 0, 0, 0, '2021-12-03 10:07:39'),
	(6, 2, 1, '2', 0, 0, 0, 0, 0, '2021-12-03 10:10:53'),
	(7, 2, 1, '3', 0, 0, 0, 0, 0, '2021-12-03 10:13:53'),
	(8, 2, 1, '2.5', 0, 0, 0, 0, 0, '2021-12-03 10:14:23'),
	(17, 1, 4, 'ㄷㄷ', 0, 0, 0, 0, 0, '2021-12-18 16:10:23'),
	(18, 1, 30, 'ㄷㄷ', 0, 0, 0, 0, 0, '2021-12-18 16:10:23'),
	(19, 1, 1, 'ㅇㅇㅇ', 0, 0, 0, 0, 0, '2021-12-18 16:38:55'),
	(20, 23, 1, '거주지 제한이 없다는게 무슨 말인지 여쭤봐도 될까요? 이전에는 거주지 제한이 있었는데 그 거주지 제한이 없어졌다는 건가요?', 0, 0, 20, 0, 0, '2021-12-18 17:03:18'),
	(21, 23, 3, '공고문 어디서확인할수있나요?', 1, 1, 20, 0, 0, '2021-12-18 17:03:18'),
	(22, 23, 1, '서울시 공고문입니다.. 수정이 안되니 날짜만 봐주세요', 1, 2, 20, 0, 0, '2021-12-18 17:03:18'),
	(23, 23, 4, '전국공통인건가여??', 0, 0, 23, 0, 0, '2021-12-18 17:03:18'),
	(24, 23, 28, 'gggg', 1, 3, 20, 0, 0, '2021-12-18 17:03:18'),
	(48, 23, 4, '전국공통인건가여??', 0, 0, 48, 0, 0, '2021-12-18 17:03:18'),
	(49, 9, 1, 'ddd', 0, 0, 49, 0, 0, '2021-12-19 03:01:23'),
	(50, 9, 1, 'fff', 0, 0, 50, 0, 0, '2021-12-19 03:01:24'),
	(51, 9, 1, '응응', 0, 0, 51, 0, 0, '2021-12-19 03:01:36'),
	(52, 23, 1, 'eee', 0, 0, 52, 0, 0, '2021-12-19 03:03:08'),
	(53, 23, 4, '구리만요', 1, 1, 23, 0, 0, '2021-12-18 17:03:18'),
	(54, 23, 1, '좋아요!', 1, 1, 52, 0, 0, '2021-12-19 15:52:43'),
	(55, 23, 1, '그래요!', 1, 2, 52, 0, 0, '2021-12-19 15:53:01'),
	(56, 23, 1, '정말요!', 1, 3, 52, 0, 0, '2021-12-19 15:53:13'),
	(57, 23, 1, '그러게 마이에요', 1, 4, 52, 0, 0, '2021-12-19 15:53:27'),
	(62, 1, 1, '내용입니다~~', 0, 0, 0, 0, 0, '2021-12-20 02:11:48'),
	(63, 35, 1, '응응', 0, 0, 63, 0, 0, '2021-12-20 02:25:35'),
	(64, 35, 1, 'ㅇㅇㅇ', 1, 1, 63, 0, 0, '2021-12-20 02:25:41'),
	(65, 35, 1, 'ㄱㄱㄱ', 1, 2, 63, 1, 0, '2021-12-20 02:25:44'),
	(66, 2, 1, 'dd', 0, 0, 66, 0, 0, '2021-12-20 02:48:47'),
	(70, 1, 1, '내용입니다~~', 0, 0, 0, 0, 0, '2021-12-20 02:49:45'),
	(71, 2, 1, 'dd', 0, 0, 71, 0, 0, '2021-12-20 03:06:15');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;

-- 테이블 pichoteldb.event 구조 내보내기
CREATE TABLE IF NOT EXISTS `event` (
  `post_no` int(11) NOT NULL COMMENT '게시글 번호',
  `al_no` int(11) NOT NULL COMMENT '알림번호',
  `ev_subject` varchar(255) DEFAULT NULL COMMENT '이벤트 이름',
  `ev_datetime` datetime DEFAULT NULL COMMENT '당첨일시',
  PRIMARY KEY (`post_no`),
  KEY `FK_alarm_TO_event` (`al_no`),
  CONSTRAINT `FK_alarm_TO_event` FOREIGN KEY (`al_no`) REFERENCES `alarm` (`al_no`),
  CONSTRAINT `FK_post_TO_event` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='추첨된 리뷰 게시글 ';

-- 테이블 데이터 pichoteldb.event:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;

-- 테이블 pichoteldb.gradeup 구조 내보내기
CREATE TABLE IF NOT EXISTS `gradeup` (
  `gu_no` int(11) NOT NULL AUTO_INCREMENT,
  `mb_no` int(11) NOT NULL COMMENT '회원번호',
  `gu_bef_grade` int(4) DEFAULT NULL COMMENT '신청시 회원등급',
  `gu_aft_grade` int(4) DEFAULT NULL COMMENT '신청등급',
  `gu_datetime` datetime DEFAULT NULL COMMENT '신청일시',
  `gu_state` char(1) DEFAULT '1',
  PRIMARY KEY (`gu_no`),
  KEY `FK_member_TO_gradeup` (`mb_no`),
  CONSTRAINT `FK_member_TO_gradeup` FOREIGN KEY (`mb_no`) REFERENCES `member` (`mb_no`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='등업 신청';

-- 테이블 데이터 pichoteldb.gradeup:~9 rows (대략적) 내보내기
/*!40000 ALTER TABLE `gradeup` DISABLE KEYS */;
INSERT INTO `gradeup` (`gu_no`, `mb_no`, `gu_bef_grade`, `gu_aft_grade`, `gu_datetime`, `gu_state`) VALUES
	(2, 2, 2, 3, '2021-12-02 17:26:21', '2'),
	(3, 3, 1, 3, '2021-12-02 17:26:21', '2'),
	(11, 4, 1, 4, '2021-12-02 17:26:21', '3'),
	(12, 6, 1, 2, '2021-12-02 17:26:21', '1'),
	(13, 28, 1, 2, '2021-12-02 17:26:21', '1'),
	(14, 2, 1, 2, '2021-12-02 17:26:21', '1'),
	(15, 29, 1, 3, '2021-12-03 11:41:46', '1'),
	(19, 30, 1, 3, '2021-12-03 14:45:10', '2'),
	(20, 34, 1, 3, '2021-12-03 16:09:56', '2');
/*!40000 ALTER TABLE `gradeup` ENABLE KEYS */;

-- 테이블 pichoteldb.logrecord 구조 내보내기
CREATE TABLE IF NOT EXISTS `logrecord` (
  `log_no` int(11) NOT NULL AUTO_INCREMENT,
  `mb_ip` varchar(255) DEFAULT NULL COMMENT '회원 아이피',
  `mb_id` varchar(255) DEFAULT NULL COMMENT '회원 아이디',
  `mb_log` datetime DEFAULT NULL COMMENT '회원 로그일자',
  PRIMARY KEY (`log_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='임시방편';

-- 테이블 데이터 pichoteldb.logrecord:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `logrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `logrecord` ENABLE KEYS */;

-- 테이블 pichoteldb.member 구조 내보내기
CREATE TABLE IF NOT EXISTS `member` (
  `mb_no` int(11) NOT NULL AUTO_INCREMENT,
  `mb_id` varchar(255) NOT NULL COMMENT '아이디',
  `mb_pwd` varchar(255) NOT NULL COMMENT '비밀번호',
  `mb_name` varchar(255) NOT NULL COMMENT '이름',
  `mb_nick` varchar(255) DEFAULT NULL COMMENT '닉네임',
  `mb_gender` enum('F','M') DEFAULT NULL COMMENT '성별',
  `mb_hp` varchar(255) DEFAULT NULL COMMENT '휴대전화번호',
  `mb_birth` date NOT NULL COMMENT '생년월일',
  `mb_datetime` datetime DEFAULT NULL COMMENT '가입 일시',
  `mb_state` char(1) DEFAULT '1',
  `mb_board` int(11) DEFAULT '0',
  `mb_visit` int(11) DEFAULT '0',
  `mb_comment` int(11) DEFAULT '0',
  `mb_grade` int(11) DEFAULT '1',
  PRIMARY KEY (`mb_no`),
  KEY `FK_membergrade_TO_member` (`mb_grade`),
  CONSTRAINT `FK_membergrade_TO_member` FOREIGN KEY (`mb_grade`) REFERENCES `membergrade` (`mg_grade`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='회원';

-- 테이블 데이터 pichoteldb.member:~15 rows (대략적) 내보내기
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` (`mb_no`, `mb_id`, `mb_pwd`, `mb_name`, `mb_nick`, `mb_gender`, `mb_hp`, `mb_birth`, `mb_datetime`, `mb_state`, `mb_board`, `mb_visit`, `mb_comment`, `mb_grade`) VALUES
	(1, 'airbnb1@java.com', '1234', '홍길동', '여행조야', 'M', '010-1313-1313', '1999-01-01', '2021-10-11 09:01:11', '1', 5, 72, 5, 1),
	(2, 'airbnb2@java.com', '1234', '나철수', '여행돌이', 'F', '010-1313-1313', '1999-02-02', '2021-10-12 09:02:11', '1', 0, 1, 0, 3),
	(3, 'airbnb3@java.com', '1234', '한지민', '트리퍼', 'M', '010-2000-2000', '1999-03-03', '2021-10-13 09:01:11', '2', 0, 0, 0, 3),
	(4, 'airbnb4@java.com', '1234', '권지용', '여미여미', 'F', '010-1313-1313', '1999-04-04', '2021-10-15 09:01:11', '1', 0, 0, 0, 4),
	(5, 'admin@java.com', '1234', '최고관리자', '최고관리자', 'M', '010-1313-1313', '2020-11-11', '2001-10-12 09:02:11', '1', 0, 27, 0, 5),
	(6, 'block@java.com', '1234', '나탈퇴', '나탈퇴', 'F', '010-1313-1313', '2002-10-22', '2001-10-12 09:02:11', '2', 0, 0, 0, 2),
	(7, 'block2@java.com', '1234', '나강퇴', '나강퇴', 'M', '010-1313-1313', '1980-10-30', '2001-10-12 09:02:11', '1', 0, 0, 0, 1),
	(27, 'same@java.com', '1234', '나철수', '이름생일중복', 'M', '010-1313-1313', '1999-02-02', '2001-10-12 09:02:11', '1', 0, 0, 0, 1),
	(28, 'jo@java.com', 'QWEQWE!@#', '장준혁', '여행조아222', 'M', '010-2000-2000', '2021-12-15', '2021-11-13 09:26:20', '1', 0, 1, 0, 1),
	(29, 'm@java.com', '1234', '한예슬', '인생몰까', 'M', '010-2000-2000', '2021-12-15', '2021-11-04 09:26:20', '1', 0, 7, 0, 3),
	(30, 'm2@java.com', '1234', '장승현', '호야슈가', 'M', '010-2000-2000', '2021-12-15', '2021-11-05 09:26:20', '1', 0, 2, 0, 3),
	(31, 'm3@java.com', '1234', '현아', '안경잽이', 'M', '010-2000-2000', '2021-12-15', '2021-11-06 09:26:20', '2', 0, 2, 0, 2),
	(32, '5126537@naver.com', 'QQQQ!!!', '소진', '여행조아여', 'F', '010-2020-2020', '2021-12-17', '2021-12-03 14:26:16', '1', 0, 0, 0, 1),
	(33, 'airbnb6@java.com', 'QWE!@#!@#', '김호호', '여행조아2323', 'F', '010-2000-2000', '2021-12-10', '2021-12-03 14:57:05', '1', 0, 1, 0, 2),
	(34, 'h3969@naver.com', 'QWE!@#QWE', '김윤호', '여행조아', 'M', '010-2255-2255', '2021-12-22', '2021-12-03 16:09:29', '3', 0, 1, 0, 3);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;

-- 테이블 pichoteldb.membergrade 구조 내보내기
CREATE TABLE IF NOT EXISTS `membergrade` (
  `mg_grade` int(4) NOT NULL COMMENT '등급 순위',
  `mg_name` varchar(255) DEFAULT NULL COMMENT '등급명',
  `mg_type` tinyint(1) DEFAULT NULL COMMENT '등업방식(0:자동, 1:수동)',
  `mg_board` int(11) DEFAULT NULL COMMENT '등업기준_게시물수',
  `mg_com` int(11) DEFAULT NULL COMMENT '등업기준_댓글수',
  `mg_visit` int(11) DEFAULT NULL COMMENT '등업기준_방문일수',
  `mg_use` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`mg_grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사이트 회원 등급';

-- 테이블 데이터 pichoteldb.membergrade:~5 rows (대략적) 내보내기
/*!40000 ALTER TABLE `membergrade` DISABLE KEYS */;
INSERT INTO `membergrade` (`mg_grade`, `mg_name`, `mg_type`, `mg_board`, `mg_com`, `mg_visit`, `mg_use`) VALUES
	(1, '시작멤버', 0, 1, 1, 1, 1),
	(2, '일반멤버', 0, 2, 2, 2, 1),
	(3, '열심멤버', 0, 3, 3, 3, 1),
	(4, '우수멤버', 0, 4, 4, 4, 1),
	(5, '최고관리자', 0, 5, 5, 5, 0);
/*!40000 ALTER TABLE `membergrade` ENABLE KEYS */;

-- 테이블 pichoteldb.memberstate 구조 내보내기
CREATE TABLE IF NOT EXISTS `memberstate` (
  `ms_no` int(11) NOT NULL AUTO_INCREMENT,
  `mb_no` int(11) NOT NULL COMMENT '회원번호',
  `ms_reason` text COMMENT '사유',
  `ms_datetime` datetime DEFAULT NULL COMMENT '탈퇴일자',
  PRIMARY KEY (`ms_no`),
  KEY `FK_member_TO_memberstate` (`mb_no`),
  CONSTRAINT `FK_member_TO_memberstate` FOREIGN KEY (`mb_no`) REFERENCES `member` (`mb_no`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='회원 탈퇴 정보';

-- 테이블 데이터 pichoteldb.memberstate:~1 rows (대략적) 내보내기
/*!40000 ALTER TABLE `memberstate` DISABLE KEYS */;
INSERT INTO `memberstate` (`ms_no`, `mb_no`, `ms_reason`, `ms_datetime`) VALUES
	(8, 34, '사유', '2021-12-03 16:11:14');
/*!40000 ALTER TABLE `memberstate` ENABLE KEYS */;

-- 테이블 pichoteldb.note 구조 내보내기
CREATE TABLE IF NOT EXISTS `note` (
  `note_no` int(11) NOT NULL AUTO_INCREMENT,
  `note_con` text COMMENT '쪽지 내용',
  PRIMARY KEY (`note_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='쪽지';

-- 테이블 데이터 pichoteldb.note:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
/*!40000 ALTER TABLE `note` ENABLE KEYS */;

-- 테이블 pichoteldb.noteindex 구조 내보내기
CREATE TABLE IF NOT EXISTS `noteindex` (
  `note_no` int(11) NOT NULL COMMENT '쪽지 번호',
  `note_get_mb_no` int(11) NOT NULL COMMENT '수신회원번호',
  `note_send_mb_no` int(11) NOT NULL COMMENT '발신회원번호',
  `note_getmb_del_state` tinyint(1) DEFAULT '0',
  `note_getmb_save_state` tinyint(1) DEFAULT '0',
  `note_getmb_read_state` tinyint(1) DEFAULT '0',
  `note_sendmb_del_state` tinyint(1) DEFAULT '0',
  `note_sendmb_save_state` tinyint(1) DEFAULT '0',
  `note_datetime` datetime DEFAULT NULL COMMENT '발송날짜',
  `note_identify_send_get_state` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`note_no`,`note_get_mb_no`,`note_send_mb_no`),
  KEY `FK_member_TO_noteindex` (`note_send_mb_no`),
  KEY `FK_member_TO_noteindex2` (`note_get_mb_no`),
  CONSTRAINT `FK_member_TO_noteindex` FOREIGN KEY (`note_send_mb_no`) REFERENCES `member` (`mb_no`),
  CONSTRAINT `FK_member_TO_noteindex2` FOREIGN KEY (`note_get_mb_no`) REFERENCES `member` (`mb_no`),
  CONSTRAINT `FK_note_TO_noteindex` FOREIGN KEY (`note_no`) REFERENCES `note` (`note_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='쪽지정보';

-- 테이블 데이터 pichoteldb.noteindex:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `noteindex` DISABLE KEYS */;
/*!40000 ALTER TABLE `noteindex` ENABLE KEYS */;

-- 테이블 pichoteldb.post 구조 내보내기
CREATE TABLE IF NOT EXISTS `post` (
  `post_no` int(11) NOT NULL AUTO_INCREMENT,
  `bo_no` int(11) NOT NULL COMMENT '게시판 번호',
  `po_subject` text COMMENT '게시글 제목',
  `po_content` text COMMENT '게시글 내용',
  `po_mb_no` int(11) NOT NULL COMMENT '회원번호',
  `po_tag` text COMMENT '태그',
  `po_views` int(11) DEFAULT NULL COMMENT '조회수',
  `po_comment` int(11) DEFAULT NULL COMMENT '댓글수',
  `po_datetime` datetime DEFAULT NULL COMMENT '게시글 작성일',
  `po_isblind` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`post_no`),
  KEY `FK_board_TO_post` (`bo_no`),
  KEY `FK_member_TO_post` (`po_mb_no`),
  CONSTRAINT `FK_board_TO_post` FOREIGN KEY (`bo_no`) REFERENCES `board` (`bo_no`),
  CONSTRAINT `FK_member_TO_post` FOREIGN KEY (`po_mb_no`) REFERENCES `member` (`mb_no`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='게시글';

-- 테이블 데이터 pichoteldb.post:~11 rows (대략적) 내보내기
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`post_no`, `bo_no`, `po_subject`, `po_content`, `po_mb_no`, `po_tag`, `po_views`, `po_comment`, `po_datetime`, `po_isblind`) VALUES
	(1, 3, '정말 좋았어요 ', '친절하고 객실 깨끗하고 온천 좋고', 7, '#숙소리뷰 #추천숙소', 0, 0, '2021-11-30 17:08:04', 0),
	(2, 3, '올데이라운지는 이용하지 마세요', '올데이 라운지 3만원 주고 이용했지만 정말 별로였어요', 2, '#숙소리뷰 #추천숙소', 0, 0, '2021-11-30 18:05:30', 0),
	(28, 4, '추가합니다', 'asd', 1, '#여름여행 #여름휴가', 0, 0, '2021-12-02 17:02:57', 0),
	(33, 2, 'asdfsvasv', 'asdvsdafewf', 1, '#여름여행 #여름휴가', 0, 0, '2021-12-02 19:33:03', 0),
	(34, 4, 'asdfasdf', 'asdfasdf', 1, '#여름여행 #여름휴가', 0, 0, '2021-12-02 20:22:58', 0),
	(36, 1, 'wwefwfewef', 'fefwe', 1, '', 0, 0, '2021-12-03 10:01:10', 0),
	(37, 1, 'dd', 'dd', 1, 'dd', 0, 0, '2021-12-20 02:38:40', 0),
	(38, 1, 'd', 'd', 1, 'd', 0, 0, '2021-12-20 02:38:50', 0),
	(39, 1, 'd', 'd', 1, 'd', 0, 0, '2021-12-20 02:39:43', 0),
	(40, 1, 'h', 'h', 1, 'h', 0, 0, '2021-12-20 02:43:43', 0),
	(41, 1, 'd', 'd', 1, 'd', 0, 0, '2021-12-20 02:43:53', 0);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

-- 테이블 pichoteldb.report 구조 내보내기
CREATE TABLE IF NOT EXISTS `report` (
  `repo_no` int(11) NOT NULL AUTO_INCREMENT,
  `mb_no` int(11) NOT NULL COMMENT '회원번호',
  `post_no` int(11) DEFAULT NULL COMMENT '게시글 번호',
  `co_no` int(11) DEFAULT NULL COMMENT '댓글 번호',
  `repo_datetime` datetime DEFAULT NULL COMMENT '신고 접수 일자',
  PRIMARY KEY (`repo_no`),
  KEY `FK_post_TO_report` (`post_no`),
  KEY `FK_comment_TO_report` (`co_no`),
  KEY `FK_member_TO_report` (`mb_no`),
  CONSTRAINT `FK_comment_TO_report` FOREIGN KEY (`co_no`) REFERENCES `comment` (`co_no`),
  CONSTRAINT `FK_member_TO_report` FOREIGN KEY (`mb_no`) REFERENCES `member` (`mb_no`),
  CONSTRAINT `FK_post_TO_report` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='신고접수';

-- 테이블 데이터 pichoteldb.report:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;

-- 테이블 pichoteldb.review 구조 내보내기
CREATE TABLE IF NOT EXISTS `review` (
  `post_no` int(11) NOT NULL COMMENT '게시글 번호',
  `roo_no` int(11) DEFAULT NULL COMMENT '숙소코드번호',
  `re_rate_loc` int(4) DEFAULT NULL COMMENT '별점후기_위치',
  `re_rate_clean` int(4) DEFAULT NULL COMMENT '별점후기_위생',
  `re_rate_comu` int(4) DEFAULT NULL COMMENT '별점후기_의사소통',
  `re_rate_chip` int(4) DEFAULT NULL COMMENT '별점후기_가성비',
  `re_vsdate` date DEFAULT NULL COMMENT '숙소 방문 일자',
  `re_push_pl` text COMMENT '주변추천',
  `re_push_npl` text COMMENT '비추',
  PRIMARY KEY (`post_no`),
  KEY `FK_room_TO_review` (`roo_no`),
  CONSTRAINT `FK_post_TO_review` FOREIGN KEY (`post_no`) REFERENCES `post` (`post_no`),
  CONSTRAINT `FK_room_TO_review` FOREIGN KEY (`roo_no`) REFERENCES `room` (`roo_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='리뷰 게시글';

-- 테이블 데이터 pichoteldb.review:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;

-- 테이블 pichoteldb.room 구조 내보내기
CREATE TABLE IF NOT EXISTS `room` (
  `roo_no` int(11) NOT NULL AUTO_INCREMENT,
  `roo_code` int(11) NOT NULL COMMENT '숙소코드',
  `roo_address` text COMMENT '주소',
  `roo_sysname` text COMMENT '이미지 저장 이름',
  `roo_realname` text COMMENT '이미지 실제 저장 이름',
  PRIMARY KEY (`roo_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='숙소';

-- 테이블 데이터 pichoteldb.room:~1 rows (대략적) 내보내기
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`roo_no`, `roo_code`, `roo_address`, `roo_sysname`, `roo_realname`) VALUES
	(1, 1, '1', '1', '1');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
