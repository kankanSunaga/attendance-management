/*一般ユーザーテーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requested_at)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', 'password', 'ROLE_GENERAL', 'false', 'false', '2020-9-01 23:25:07');

/*管理者テーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requested_at)
VALUES(2, '管理太郎', 'kanri@xxx.co.jp', 'password', 'ROLE_ADMIN', 'true', 'false', '2020-9-01 23:25:07');
