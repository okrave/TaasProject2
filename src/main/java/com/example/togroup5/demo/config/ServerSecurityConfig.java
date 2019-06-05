//package com.example.togroup5.demo.config;
//
//import com.example.togroup5.demo.servicies.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//
//    //@Autowired
//    //private AppOAuth2UserService appOAuth2UserService;
//
//    /**
//     * Utilizzata dall'implementazione di default di authenticationManager() per ottenere un
//     * AuthenticationManager, In-Memory is the easiest way to implement authentication in Spring Security,
//     * but it doesn't really have any value outside of experiments, prototypes or maybe testing.
//     * Production systems usually rely either on a database, an LDAP server or any other specific resource to store the users information.
//     * <p>
//     * In-memory authentication is the simplest form, and requires that the credentials for all users be specified in the code itself. This is impractical in all but the simplest cases.
//     * <p>
//     * In JDBC based authentication user_app’s authentication and authorization information are stored in database
//     */
//   // @Override
//   // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /*
//         * Non stiamo altro che creando gli utenti che possono accedere ai contenuti web. passwordEncoder lo utilizziamo per criptare la password
//         * In caso usassimo il database allora dovremmo ricordarci di criptare prima di salvare
//         *
//         * */
//      //  auth.inMemoryAuthentication()
//        //////    .withUser("user1").password(passwordEncoder().encode("password")).roles("USER")
//              //  .and()
//                //.withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
//    //}
//
//
//    /*In questa configurazione definiamo quali user_app e quali ruoli possono accedere alle pagine
//     * in antMatcher() l'ordine è importante dovremmo specificare i più generali prima*/
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        /*http.csrf().disable() //Disabilita il controllo contro attacchi csrf
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/anonymous*").anonymous()
//                .antMatchers("/login*").permitAll()
//                .anyRequest().authenticated(); // Per qualsiasi altra pagina è richiesta l'autenticazione
//                .and()
//                .formLogin() // corrisponde al login
//                .loginPage("/login.html").permitAll() // Se non lo specifichiamo spring ci dara una pagina di login fatta da lui stesso
//                    .loginProcessingUrl("/perform_login") //The default URL where the Spring Login will POST to trigger the authentication process is /login which used to be /j_spring_security_check
//                    .defaultSuccessUrl("/index.html",true) //After a successful Login process, the user_app is redirected to a page – which by default is the root of the web application.
//                    .failureUrl("/index.html?error=true") //Same as with the Login Page, the Login Failure Page is autogenerated by Spring Security at /login?error by default.
//                .and() //finisce il login
//                .logout().permitAll()
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler());*/
//        http
//                .authorizeRequests()
//                    /*.antMatchers(
//                        "/js/**",
//                        "/css/**",
//                        "/img/**",
//                        "/webjars/**").permitAll()
//                    .antMatchers("/registration","/resthome","/userList","/listGroup","/listGroupByCreator","/listGroupRest","/searchGroup").permitAll()
//                    .antMatchers("/", "/home","/login","/logout").permitAll()
//                    .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
//                    .antMatchers("/user_app/**").hasAnyRole("USER")
//                    .antMatchers("/userInfo").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
//                    .anyRequest().authenticated()
//                    .and()*/
//                    .anyRequest().permitAll()
//                    .and()
//                    //.oauth2Login()
//
//
//                .formLogin()
//                    .loginProcessingUrl("/j_spring_security_check")
//                    .loginPage("/login")
//                    .failureUrl("/login?error=true")
//                    .usernameParameter("username")//
//                    .passwordParameter("password")
//                    .and()
//
//
//
//
//
//                /*
//                * These two advanced attributes (delete cookies and invalidate http session) control the session invalidation as well as a list of cookies to be deleted when the user_app logs out.
//                * As such, invalidateHttpSession allows the session to be set up so that it’s not invalidated when logout occurs (it’s true by default).
//                * The deleteCookies method is simple as well:
//                * */
//
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
//                .and()
//                .exceptionHandling().accessDeniedPage("/403");
//                //.and()
//                //.rememberMe().tokenRepository(this.persistentTokenRepository()) // Config Remember me
//                //.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
//        /*
//        * An user_app accesses a website and logs in. Then he/she turns off the browser and accesses the website at some time (for example, on the next day),
//        * and he/she has to log in again, which causes unnecessary trouble.
//        * The " Remember Me" option allows the website to " remember" the user_app's information to automatically log in when the user_app visits the website the next time.
//        * When the user_app logs in an application with the " Remember Me" option, the Spring will save the last login information, and  token.
//        * The Token is an encrypted string that contains necessary information  for the  Spring to automatically log in when the user_app visits the website next time.
//        * */
//
//
//        //Gestione della sessione utente
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(1);
//
//
//    }
//
//
//    //Token stred in table (Persistent_logins)
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
//        db.setDataSource(dataSource);
//        return db;
//    }
//
//    /*Authentificazione per capire se un utente è nel db o no*/
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        //Setting service to find User in database, and setting passwordEncoder
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
