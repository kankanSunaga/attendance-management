/*一般ユーザーテーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(1, 'いいちゅかはやちょ', 'itsuka@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-09-01 23:25:07');

INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(3, '富田', 'tomita@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_GENERAL', 'false', 'false', '2020-09-01 23:25:07');


/*管理者テーブル*/
INSERT INTO user (userId, userName, email, password, role, permission, frozen, requestedAt)
VALUES(2, '管理太郎', 'kanri@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'ROLE_ADMIN', 'true', 'false', '2020-09-01 23:25:07');


/*契約テーブル1-1*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(1, 170, '09:00', '01:00', '18:00', '2020-09-01','LIM', '2021-08-31', 1); 

/*契約テーブル1-2*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(2, 170, '09:00', '01:00', '18:00', '2021-09-01','KAN', '2022-08-31', 1);

/*契約テーブル3-1*/
INSERT INTO contract (contractId, contractTime, startTime,breakTime,endTime,startDate,officeName, endDate, userId)
VALUES(3, 170, '07:00', '01:00', '18:00', '2020-09-01','YUE', '2021-08-31', 3); 


/*月のステータステーブル*/
INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
 VALUES(1, 2020, 8, 'false', 'false',1);

 INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
 VALUES(2, 2020, 9, 'true', 'false',1);
 
 INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
 VALUES(3, 2020, 10, 'false', 'true',1);
 
 INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
 VALUES(4, 2020, 11, 'false', 'false',1);
 
 INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
 VALUES(6, 2021, 8, 'false', 'false',1);
 
 INSERT INTO month (monthId, year, month, deadlineStatus,requestStatus,contractId)
 VALUES(5, 2020, 9, 'false', 'true',3);
 

/*仕事詳細テーブル1-1（2020年9月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(1, '2020-09-01', '2020-09-01 09:00:00', '12:00:00', '2020-09-01 17:00:00', 420, 1, 2);

/*仕事詳細テーブル1-2（2020年9月3日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(2, '2020-09-03', '2020-09-03 09:00:00', '12:00:00', '2020-09-03 17:00:00', 420, 1, 2);

/*仕事詳細テーブル1-3（2020年10月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(3, '2020-10-01', '2020-10-01 09:00:00', '12:00:00', '2020-10-01 17:00:00', 420, 1, 3);

/*仕事詳細テーブル1-3（2020年10月2日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(10, '2020-10-02', '2020-10-02 09:00:00', '12:00:00', '2020-10-02 17:00:00', 420, 1, 3);

/*仕事詳細テーブル1-4（2020年11月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(4, '2020-11-01', '2020-11-01 09:00:00', '12:00:00', '2020-11-01 17:00:00', 420, 1, 4);

/*仕事詳細テーブル1-5（2021年8月30日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(5, '2021-08-30', '2021-08-30 09:00:00', '12:00:00', '2021-08-30 17:00:00', 420, 1, 1);


/*仕事詳細テーブル2-1（2021年9月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(6, '2020-09-01', '2020-09-01 09:00:00', '12:00:00', '2020-09-01 17:00:00', 420, 2, 5);

/*仕事詳細テーブル2-2（2021年10月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(7, '2020-10-01', '2020-10-01 09:00:00', '12:00:00', '2020-10-01 17:00:00', 420, 2, 6);


/*仕事詳細テーブル3-1（2020年9月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(8, '2020-09-01', '2020-09-01 09:00:00', '12:00:00', '2020-09-01 17:00:00', 420, 3, 7);

/*仕事詳細テーブル3-2（2020年10月1日）*/
INSERT INTO workTime (workTimeId, workDay, startTime, breakTime, endTime, workTimeMinute, contractId, monthId)
VALUES(9, '2020-10-01', '2020-10-01 09:00:00', '12:00:00', '2020-10-01 17:00:00', 420, 3, 8);