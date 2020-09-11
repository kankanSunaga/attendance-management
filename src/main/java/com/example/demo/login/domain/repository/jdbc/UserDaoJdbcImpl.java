package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    // Userテーブルにデータを1件insert.
    @Override
    public int insertOne(User user) throws DataAccessException {

        //１件登録
        int rowNumber = jdbc.update("INSERT INTO user(userName,"
                + " email,"
                + " password,"
                + " role,"
                + " permission"
                + " frozen)"
                + " VALUES(?, ?, ?, ?, ?)",
                //申請日時の登録処理追記してください
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isPermission(),
        		user.isFrozen());
        		//申請日時の登録処理追記してください
        
        return rowNumber;
    }
    
  //Userテーブルから未承認の数を取得
    @Override
    public int countPermission() throws DataAccessException{
    	//未承認の数を取得
    	int countPermission =jdbc.queryForObject("SELECT COUNT(*) FROM user WHERE permission　='FALSE'　and frozen = 'FALSE'",Integer.class);
		System.out.println(countPermission);
    	return countPermission;
    }
    
    //Userテーブルから未承認データを取得する
    public List<User> selectPermission() throws DataAccessException{
    	//DBから未承認のユーザーデータを取得
    	List<Map<String,Object>> getList = jdbc.queryForList("SELECT * FROM user WHERE permission　= 'FALSE' and frozen = 'FALSE'");
    	
    	//結果返却用の変数
    	List<User> userList = new ArrayList<>();
    	
    	//取得データを返却用の変数
    	for(Map<String,Object> map:getList){
    		User user = new User();
    		
    		//作成したインスタンスにデータをセット
    		user.setUserId((int)map.get("UserId"));
    		user.setUserName((String)map.get("UserName"));
            user.setEmail((String)map.get("Email"));
            user.setPassword((String)map.get("Password"));
            user.setRole((String)map.get("Role"));
            user.setPermission((boolean)map.get("Permission"));
    		user.setFrozen((boolean)map.get("Frozen"));
    		user.setRequested_at((String)map.get("Requested_at"));
    		
    		//結果返却用のListに追加
    		userList.add(user);
    	}
    	
    	return userList;
    	
    }
    //Userテーブルから未承認ユーザー１件取得
    public User selectOne(int userId) throws DataAccessException{
    	//１件取得
    	Map<String, Object>map = jdbc.queryForMap("SELECT * FROM user WHERE userId= ?",userId);
    	//結果返却用の変数
    	User user = new User();
    	//取得したデータ結果返却用の変数にセットしていく
		user.setUserId((int)map.get("UserId"));
		user.setUserName((String)map.get("UserName"));
		user.setEmail((String)map.get("Email"));
		user.setPassword((String)map.get("Password"));
		user.setRole((String)map.get("Role"));
		user.setPermission((boolean)map.get("Permission"));
		user.setFrozen((boolean)map.get("Frozen"));
		user.setRequested_at((String)map.get("Requested_at"));
		
		return user;    	
    }
  ///Userテーブルの承認ステータス　未承認→承認に変更
    @Override
    public int updatePermission(User user) throws DataAccessException{
    	int rowNumber = jdbc.update("UPDATE user SET permission = 'TRUE' WHERE userId= ?",user.getUserId());    	
    	return rowNumber;
    }
  ///Userテーブルの凍結ステータス　利用中→利用不可に変更
    public int updateFrozen(User user) throws DataAccessException{
    	int rowNumber = jdbc.update("UPDATE user SET frozen = 'TRUE' WHERE userId= ?",user.getUserId());
    	return rowNumber;
    }
}
