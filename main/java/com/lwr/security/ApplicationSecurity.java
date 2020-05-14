package com.lwr.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lwr.security.filter.MyUsernamePasswordAuthenticationFilter;
import com.lwr.security.handler.LoginFailHandler;
import com.lwr.service.UserService;
import com.lwr.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurity.class);

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        //放开静态资源
        webSecurity.ignoring()
                    .antMatchers("/public/**","/static/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略

        http.csrf(csrf -> csrf.disable())
            .headers().frameOptions().sameOrigin()
            .and()
            .authorizeRequests()
            .antMatchers("/common/**").permitAll()
            .antMatchers("/home/user").hasAnyRole("USER")
            .antMatchers("/home/admin").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint())//匿名用户无权限，一般跳转登录
            .accessDeniedHandler(new AccessDeniedHandlerImpl())//登录用户无权限，报403
            .and()
            .logout().logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .and()
            .addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic();
//        http.httpBasic();自动添加一个Basic***Filter到Filter Chain
        //http.formLogin()自动添加一个UsernamePasswordAuthenticationFilter到Filter Chain
//        http.formLogin().loginPage("/tologin").permitAll(); 
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder,UserService userService) throws Exception {
        authBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        authBuilder.eraseCredentials(false);
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        LoginUrlAuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/tologin");
        authenticationEntryPoint.setUseForward(true);
        return authenticationEntryPoint;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    protected MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter myAuthFilter = new MyUsernamePasswordAuthenticationFilter();
        myAuthFilter.setAuthenticationManager(authenticationManagerBean());
        myAuthFilter.setAuthenticationFailureHandler(loginFailHandler());
        myAuthFilter.setFilterProcessesUrl("/doLogin");
        return myAuthFilter;
        
    }
    
    @Bean
    LoginFailHandler loginFailHandler() {
        return new LoginFailHandler("/tologin",true);
    }
    
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {    //用户登录实现
//        return new UserDetailsService() {
//            @Autowired
//            private UserDao userDao;
//
//            @Override
//            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//                List<User> list = userDao.findByName(s);
//                User user;
//                if(list.size()>0 && list.get(0) != null) {
//                    user = list.get(0);
//                }else {
//                    throw new UsernameNotFoundException("Username " + s + " not found");
//                }
//                //查role 表
//                List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//                return new org.springframework.security.core.userdetails.User(user.getName(),
//                        user.getPassword(), true, true, true, true, authorities);
//            }
//        };
//    }
    
    /*
     * LogoutSuccessHandler bean,类似的，返回的是security定义好接口或抽象类的实现类或者子类
     */
//    @Bean
//    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
//        return new LogoutSuccessHandler() {
//            @Override
//            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                try {
//                    SecurityUser user = (SecurityUser) authentication.getPrincipal();
//                    logger.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
//                } catch (Exception e) {
//                    logger.info("LOGOUT EXCEPTION , e : " + e.getMessage());
//                }
//                httpServletResponse.sendRedirect("/login");
//            }
//        };
//    }
//
//    @Bean
//    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
//        return new SavedRequestAwareAuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                User userDetails = (User) authentication.getPrincipal();
//                logger.info("USER : " + userDetails.getUsername() + " LOGIN SUCCESS !  ");
//                super.onAuthenticationSuccess(request, response, authentication);
//            }
//        };
//    }
    
//    @Autowired
//    public void showBeans(ApplicationContext ApplicationContext) {//please keep comment this otherwise making some mistake
//        Object bean2 = ApplicationContext.getBean("springSecurityFilterChain");
//        if(bean2!=null)
//        System.out.println(bean2.getClass().getName());
//        String[] beanDefinitionNames = ApplicationContext.getBeanDefinitionNames();
//        for (String bean : beanDefinitionNames) {
//            System.out.println(bean);
//        }
//    }
}
