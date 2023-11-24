package com.legacy.application.site.main.service;

import javax.annotation.Resource;

import com.legacy.application.site.main.vo.MenuVO;
import org.springframework.stereotype.Service;

import com.legacy.application.site.main.mapper.MainMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("mainService")
public class MainService{
	
	@Resource(name = "mainMapper")
	MainMapper mainMapper;



	public List<MenuVO> getMenuList() throws Exception{
		return mainMapper.getMenuList();
	}
}
