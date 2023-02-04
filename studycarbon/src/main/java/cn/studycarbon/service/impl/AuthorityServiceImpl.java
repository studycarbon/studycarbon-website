package cn.studycarbon.service.impl;

import cn.studycarbon.domain.Authority;
import cn.studycarbon.repository.AuthorityRepository;
import cn.studycarbon.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    // 根据id获取对应的角色权限
    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findById(id).get();
    }
}
