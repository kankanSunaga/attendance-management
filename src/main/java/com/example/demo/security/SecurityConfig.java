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
	private static final String USER_SQL = "SELECT email, password, true"
			+ " FROM user"
			+ " WHERE email =?";

	// ユーザーのロールを取得する
	private static final String ROLE_SQL = "SELECT email, role"
			+ " FROM user"
			+ " WHERE email =?";

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/webjars/**", "/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/css/**").permitAll() // cssへのアクセス許可
				.antMatchers("/login/**").permitAll() // ログインページへの直リンクの許可
				.antMatchers("/signup/**").permitAll() // ユーザー登録ページへの直リンク許可
				.antMatchers("/forgotPassword/**").permitAll().anyRequest().authenticated(); // その他の直リンク禁止

		http.formLogin().loginProcessingUrl("/login") // ログイン処理のパス
				.loginPage("/login") // ログイン処理のパス
				.failureUrl("/login") // ログイン失敗時の遷移先
				.usernameParameter("email") // ログインページのユーザーメールアドレス
				.passwordParameter("password") // ログインページのパスワード
				.defaultSuccessUrl("/session", true); // ログイン成功後の遷移先

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutUrl("/logout")
				.logoutSuccessUrl("/login");

		// H2コンソール使用の許可(デプロイ時に削除)
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(USER_SQL)
				.authoritiesByUsernameQuery(ROLE_SQL).passwordEncoder(passwordEncoder());
	}
}