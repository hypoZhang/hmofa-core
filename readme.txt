
exception  -- 顶级，不依赖任何第三方库和模块

lang	-- 依赖 exception ， 原则上不依赖第三方库


resources   -- 依赖  exception、lang ， 可依赖第三方库


I18n	-- 依赖  exception、lang ， 可依赖第三方库


===========================================

exception -- 异常基础包，所有异常基础


lang	-- 一些通用的 功能类和接口定义


resources  -- 数据资源 （主要指文件，包括但不限于 本地磁盘文件、网络文件） 


