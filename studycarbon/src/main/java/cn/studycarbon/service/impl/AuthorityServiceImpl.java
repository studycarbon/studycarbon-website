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

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findById(id).get();
    }
}
