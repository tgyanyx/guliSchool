use guli;
CREATE TABLE `edu_teacher` (
  `id` char(19) NOT NULL COMMENT '讲师ID',
  `name` varchar(20) NOT NULL COMMENT '讲师姓名',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '讲师简介',
  `career` varchar(500) DEFAULT NULL COMMENT '讲师资历,一句话说明讲师',
  `level` int(10) unsigned NOT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='讲师';

-- ----------------------------
-- Records of edu_teacher
-- ----------------------------
INSERT INTO `edu_teacher` VALUES ('1', '张三', '近年主持国家自然科学基金（6项）、江苏省重大科技成果转化项目（5项）、江苏省产学研前瞻性联合研究项目（3项）、省工业科技支撑、省高技术、省自然科学基金等省部级及其企业的主要科研项目40多个，多个项目在企业成功转化，产生了较好的经济、社会和环境效益。积极开展产学研科技合作，并与省内16家企业建立了江苏省研究生工作站，其中6家为江苏省优秀研究生工作站', '高级', '1', 'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg', '0', '0', '2019-10-30 14:18:46', '2019-11-12 13:36:36');
INSERT INTO `edu_teacher` VALUES ('1189389726308478977', '晴天', '高级讲师简介', '高级讲师资历', '2', 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/de47ee9b-7fec-43c5-8173-13c5f7f689b2.png', '1', '0', '2019-10-30 11:53:03', '2019-10-30 11:53:03');
INSERT INTO `edu_teacher` VALUES ('1189390295668469762', '李刚', '高级讲师简介', '高级讲师', '2', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '2', '0', '2019-10-30 11:55:19', '2019-11-12 13:37:52');
INSERT INTO `edu_teacher` VALUES ('1189426437876985857', '王二', '高级讲师简介', '高级讲师', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '0', '0', '2019-10-30 14:18:56', '2019-11-12 13:37:35');
INSERT INTO `edu_teacher` VALUES ('1189426464967995393', '王五', '高级讲师简介', '高级讲师', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '0', '0', '2019-10-30 14:19:02', '2019-11-12 13:37:18');
INSERT INTO `edu_teacher` VALUES ('1192249914833055746', '李四', '高级讲师简介', '高级讲师', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '0', '0', '2019-11-07 09:18:25', '2019-11-12 13:37:01');
INSERT INTO `edu_teacher` VALUES ('1192327476087115778', '1222-12-12', '1111', '11', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '0', '0', '2019-11-07 14:26:37', '2019-11-11 16:26:26');
INSERT INTO `edu_teacher` VALUES ('1195337453429129218', 'test333', 'sdfsdf', 'sdfdf', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '0', '0', '2019-11-15 21:47:12', '2020-08-04 23:45:31');
INSERT INTO `edu_teacher` VALUES ('1289906631454273538', 'hahaha', 'string', 'string', '0', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '0', '0', '2020-08-02 20:51:21', '2020-08-02 21:07:03');
INSERT INTO `edu_teacher` VALUES ('1290646470801776642', '张武', '我很好', '一般', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '1', '0', '2020-08-04 21:51:12', '2020-08-04 21:51:12');
INSERT INTO `edu_teacher` VALUES ('1290647121648709633', '王麻子', '理工教授', '理工', '1', 'https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/11/08/e44a2e92-2421-4ea3-bb49-46f2ec96ef88.png', '1', '0', '2020-08-04 21:53:48', '2020-08-04 21:53:48');
INSERT INTO `edu_teacher` VALUES ('1290670160830558210', '哈嘿嘿', 'qqqq', '是是是', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/74e33ab5bf8d46848cb5df9943a02c56file.png', '0', '0', '2020-08-04 23:25:20', '2020-08-05 15:16:24');
INSERT INTO `edu_teacher` VALUES ('1290907563440754690', '测试头像上传', '222', '222', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/25f411209c8b44b9b003482b6265c3c9file.png', '1', '0', '2020-08-05 15:08:42', '2020-08-05 15:08:42');
INSERT INTO `edu_teacher` VALUES ('1292097031057219585', '龙洋', '男，曾任重庆市城管委员会会长、重庆工商大学融智学院保安一把手。', '重庆工商大学融智学院保安', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/08/ceacfa9cbe504bc8a9291d9bbe40e236file.png', '0', '0', '2020-08-08 21:55:13', '2020-08-08 21:55:13');
INSERT INTO `edu_teacher` VALUES ('1294175384794947586', '廖大姐', '重庆大学研究生、本科生，现在任职鱼洞街道清洁卫生', '重庆大学研究生', '1', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/14/3d66cd734fa44cf988dcb88a2c11785bfile.png', '0', '0', '2020-08-14 15:33:51', '2020-08-14 15:33:51');
INSERT INTO `edu_teacher` VALUES ('2', '周杰伦', '著名歌星，擅长音乐制作', '重庆交通大学', '1', 'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg', '0', '0', '2020-08-02 16:54:17', '2020-08-02 16:54:21');
INSERT INTO `edu_teacher` VALUES ('66', '廖晓悦', '清华大学经济学博士后，曾任中央银行行长、清华大学经管学院院长。', '清华大学', '2', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/08/80ec96616b0344ffbc5e9d9fe4e03914file.png', '0', '0', '2020-08-03 16:44:36', '2020-08-08 21:51:10');
INSERT INTO `edu_teacher` VALUES ('999', '热巴', '高级影星简介', '北京大学', '0', 'http://edu-longyang.oss-cn-beijing.aliyuncs.com/2020/08/05/74e33ab5bf8d46848cb5df9943a02c56file.png', '0', '0', '2020-07-27 17:18:04', '2020-08-04 17:18:08');
