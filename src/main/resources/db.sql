# 创建数据库tx
CREATE DATABASE tx CHARACTER SET utf8 COLLATE utf8_general_ci;

# 创建user表
CREATE TABLE `user`  (
                         `id` int(11) NOT NULL,
                         `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
                         `age` int(10) NULL DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', 12);
INSERT INTO `user` VALUES (2, '李四', 19);