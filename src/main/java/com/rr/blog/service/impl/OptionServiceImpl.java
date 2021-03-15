package com.rr.blog.service.impl;

import com.rr.blog.entity.Options;
import com.rr.blog.mapper.OptionsMapper;
import com.rr.blog.service.OptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OptionServiceImpl implements OptionService {
    @Autowired
    private OptionsMapper optionsMapper;
    @Override
    public Options getOptions() {
        return optionsMapper.getOptions();
    }

    @Override
    public void insertOptions(Options options) {
        optionsMapper.insert(options);

    }

    @Override
    public void updateOptions(Options options) {
        optionsMapper.update(options);
    }
}
