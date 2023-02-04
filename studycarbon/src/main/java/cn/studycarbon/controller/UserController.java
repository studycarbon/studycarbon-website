package cn.studycarbon.controller;

import cn.studycarbon.Application;
import cn.studycarbon.domain.Authority;
import cn.studycarbon.domain.User;
import cn.studycarbon.service.AuthorityService;
import cn.studycarbon.service.UserService;
import cn.studycarbon.util.ConstraintViolationExceptionHandler;
import cn.studycarbon.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UserController {
    // 日志
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    // 用户服务
    @Autowired
    private UserService userService;

    // 权限服务
    @Autowired
    private AuthorityService authorityService;

    // 查询所有用户
    /**
     *  @RequestParam 用于将请求参数映射到功能处理方法的参数上。
     *  详情请参考博客：https://www.cnblogs.com/wangchuanfu/p/5913310.html
     *
     * public String queryUserName(@RequestParam String userName);
     *  localhost:8080/.../?userName=zhangshan
     *  请求中包含username参数（如/requestparam1?userName=zhangshan），则自动传入
     *
     * @RequestParam注解主要有哪些参数:
     *  value：参数名字，即入参的请求参数名字，如username表示请求的参数区中的名字为username的参数的值将传入；
     *  required：是否必须，默认是true，表示请求中一定要有相应的参数，否则将报404错误码；
     *  defaultValue：默认值，表示如果请求中没有同名参数时的默认值，默认值可以是SpEL表达式，如“#{systemProperties['java.vm.version']}”
     *
     * public String queryUserName(@RequestParam(value="username", required=true, defaultValue="zhangshan") String username)
     *
     * 在传递参数的时候如果是url?userName=zhangsan&userName=wangwu时，其实在实际roleList参数入参的数据为“zhangsan,wangwu”，
     * 即多个数据之间使用“，”分割；我们应该使用如下方式来接收多个请求参数：
     * public String requestparam8(@RequestParam(value="userName") String []  userNames)
     * 或者是
     * public String requestparam8(@RequestParam(value="list") List<String> list)
     *
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {
        logger.info("get users list: async<{}>, pageIndex<{}>, pageSize<{}>, name<{}>", async, pageIndex, pageSize, name);

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<User> page = userService.listUsersByNameLike(name, pageable);

        // 当前所在页面数据列表
        List<User> list = page.getContent();
        model.addAttribute("page", page);
        model.addAttribute("userList", list);
        return new ModelAndView(async == true ? "users/list::#mainContainerRepleace" : "users/list", "userModel", model);
    }

    // 获取form表单页面
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        //System.out.println("===================================================list===================================================");
        User user = new User(null, null, null);
        model.addAttribute("user", user);
        //System.out.println(user);
        return new ModelAndView("users/add", "userModel", model);
    }

    //新建用户
    @PostMapping
    public ResponseEntity<Response> create(User user, Long authorityId) {
        logger.info("create user =>");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(authorityId));
        user.setAuthorities(authorities);

        // 之前getId返回值为long发现不能和null进行比较
        // 之后getId返回值为Long发现可以和null进行比较
        if (user.getId() == null) {
            // 加密密码
            user.setEncodePassword(user.getPassword());
        } else {
            // 判断密码是否做了变更
            User originalUser = userService.getUserById(user.getId());
            String rawPassword = originalUser.getPassword();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePasswd = encoder.encode(user.getPassword());
            boolean isMatch = encoder.matches(rawPassword, encodePasswd);
            if (!isMatch) {
                // 给此密码加密
                user.setEncodePassword(user.getPassword());
            } else {
                // 不用给此密码加密，保持原状
                user.setPassword(user.getPassword());
            }
        }

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }

        return ResponseEntity.ok().body(new Response(true, "处理成功", user));
    }

    // 删除用户
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    // 获取修改用户的界面，及数据
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("users/edit", "userModel", model);
    }
}
