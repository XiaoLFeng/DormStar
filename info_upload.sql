USE dormstar;

TRUNCATE ds_info;

INSERT INTO ds_info (value, data, commit) VALUES
                                              ('title', '201京海市保护伞', '网站标题'),
                                              ('sub_title', '我们的宿舍', '网站副标题'),
                                              ('register', true, '是否允许注册'),
                                              ('autoLogin', true, '是否允许自动登录'),
                                              ('schoolLoginAddress', 'http://10.1.99.100:801/', '校园登录IP地址')