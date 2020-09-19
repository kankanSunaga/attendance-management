/*一般ユーザーテーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-9-01 23:25:07');

INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(3, '富田', 'tomita@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-9-01 23:25:07');

/*管理者テーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(2, '管理太郎', 'kanri@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_ADMIN', 'true', 'false', '2020-9-01 23:25:07');

INSERT INTO workDetail (workDetailId, contractTime, startTime,breakTime,endTime,startDate,officeName)
VALUES(1, 170, '09:00', '01:00', '18:00', '2020-9-01','LIM'); 

/*仕事詳細テーブル（9月1日）*/
INSERT INTO workTime (workTimeId,workDay,startTime,breakTime,endTime,workTimeMinute)
VALUES(1, '2020-9-01', '2020-9-01 09:00:00', '12:00:00', '2020-9-01 17:00:00', 420);

/*仕事詳細テーブル（9月2日）*/
INSERT INTO workTime (workTimeId,workDay,startTime,breakTime,endTime,workTimeMinute)
VALUES(2, '2020-9-02', '2020-9-02 09:00:00', '12:00:00', '2020-9-02 17:00:00', 420);

/*仕事詳細テーブル（10月1日）*/
INSERT INTO workTime (workTimeId,workDay,startTime,breakTime,endTime,workTimeMinute)
VALUES(3, '2020-10-02', '2020-10-02 09:00:00', '12:00:00', '2020-10-02 17:00:00', 420);