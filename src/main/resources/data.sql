/*一般ユーザーテーブル*/
INSERT INTO m_user (user_id, user_name, email, password, role)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', 'password', 'ROLE_GENERAL');

/*管理者テーブル*/
INSERT INTO m_user (user_id, user_name, email, password, role)
VALUES(1, '管理太郎', 'kanri@xxx.co.jp', 'password', 'ROLE_ADMIN')
