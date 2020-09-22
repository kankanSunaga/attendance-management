package com.example.demo.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//セキュリティ設定用クラス

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private DataSource dataSource;
	
	// メールアドレスとパスワードを取得する
	private static final String USER_SQL = "SELECT"
			+ " email,"
			+ " password,"
			+ " true"
			+ " FROM"
			+ " user"
			+ " WHERE"
			+ " email =?";
	
	// ユーザーのロールを取得する
	private static final String ROLE_SQL = "SELECT"
			+ " email,"
			+ " role"
			+ " FROM"
			+ " user"
			+ " WHERE"
			+ " email =?";
		
	@Override
	public void configure(WebSecurity web) throws Exception {

		// 静的リソースへのアクセスにはセキュリティを適用しない
		web.ignoring().antMatchers("/webjars/**","/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// ログイン不要ページの設定
		http.authorizeRequests()
				.antMatchers("/css/**").permitAll() // cssへのアクセス許可
				.antMatchers("/login/**").permitAll() // ログインページへの直リンクの許可
				.antMatchers("/signup/**").permitAll() // ユーザー登録ページへの直リンク許可
				.anyRequest().authenticated(); // その他の直リンク禁止
		
		// ログイン処理
		http.formLogin()
				.loginProcessingUrl("/login") // ログイン処理のパス
				.loginPage("/login") // ログイン処理のパス
				.failureUrl("/login") // ログイン失敗時の遷移先
				.usernameParameter("email") // ログインページのユーザーメールアドレス
				.passwordParameter("password") // ログインページのパスワード
				.defaultSuccessUrl("/workDetail", true); // ログイン成功後の遷移先
		
		// ログアウト処理
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login");
		
//		// CSRF対策を無効に設定（一時的）
//		http.csrf().disable();
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//ログイン処理時のユーザー情報をDBから取得する
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(USER_SQL)
			.authoritiesByUsernameQuery(ROLE_SQL)
			.passwordEncoder(passwordEncoder());
		
	}
}