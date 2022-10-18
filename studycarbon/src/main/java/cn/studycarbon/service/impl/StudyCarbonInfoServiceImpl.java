package cn.studycarbon.service.impl;

import cn.studycarbon.dao.StudyCarbonInfoDao;
import cn.studycarbon.domain.StudyCarbonInfo;
import cn.studycarbon.service.StudyCarbonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyCarbonInfoServiceImpl implements StudyCarbonInfoService {

    @Autowired
    private StudyCarbonInfoDao studyCarbonInfoDao;

    @Override
    public StudyCarbonInfo getInfo() {
        return studyCarbonInfoDao.getInfo();
    }
}
