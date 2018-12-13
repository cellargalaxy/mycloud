CREATE TABLE `authorization` (
  `authorization_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '授权id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `permission` varchar(16) NOT NULL COMMENT '权限',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`authorization_id`),
  UNIQUE KEY `uk_user_id_permission` (`user_id`,`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权表';


CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` char(68) NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


CREATE TABLE `own` (
  `own_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '所属id',
  `own_uuid` varchar(36) NOT NULL COMMENT '所属uuid',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `file_length` bigint(20) NOT NULL COMMENT '文件长度',
  `content_type` varchar(32) NOT NULL COMMENT '文件类型',
  `file_name` varchar(256) NOT NULL COMMENT '文件名',
  `file_id` int(11) DEFAULT NULL COMMENT '文件id',
  `sort` varchar(32) NOT NULL DEFAULT '<defalut>' COMMENT '分类',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`own_id`),
  UNIQUE KEY `uk_own_uuid` (`own_uuid`),
  KEY `idx_user_id_sort` (`user_id`,`sort`),
  KEY `idx_file_id` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='所属表';




CREATE TABLE `file_info` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `md5` char(32) NOT NULL COMMENT 'MD5',
  `file_length` bigint(20) NOT NULL COMMENT '文件长度',
  `content_type` varchar(32) NOT NULL COMMENT '文件类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`file_id`),
  UNIQUE KEY `uk_md5` (`md5`),
  KEY `idx_content_type` (`content_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';



CREATE TABLE `own_expire` (
  `own_id` int(11) NOT NULL COMMENT '所属id',
  `expire_time` datetime NOT NULL COMMENT '所属过期时间',
  PRIMARY KEY (`own_id`),
  KEY `idx_expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件过期表';