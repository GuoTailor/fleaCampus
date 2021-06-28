/*
 Navicat Premium Data Transfer

 Source Server         : localhost_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 130002
 Source Host           : localhost:5432
 Source Catalog        : flea_campus
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 130002
 File Encoding         : 65001

 Date: 28/06/2021 17:37:07
*/


-- ----------------------------
-- Sequence structure for fc_area_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_area_id_seq";
CREATE SEQUENCE "public"."fc_area_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_comment_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_comment_id_seq";
CREATE SEQUENCE "public"."fc_comment_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_like_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_like_id_seq";
CREATE SEQUENCE "public"."fc_like_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_post_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_post_id_seq";
CREATE SEQUENCE "public"."fc_post_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_reply_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_reply_id_seq";
CREATE SEQUENCE "public"."fc_reply_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_role_id_seq";
CREATE SEQUENCE "public"."fc_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_school_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_school_id_seq";
CREATE SEQUENCE "public"."fc_school_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_user_id_seq";
CREATE SEQUENCE "public"."fc_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for fc_user_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."fc_user_role_id_seq";
CREATE SEQUENCE "public"."fc_user_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for fc_area
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_area";
CREATE TABLE "public"."fc_area" (
  "id" int4 NOT NULL DEFAULT nextval('fc_area_id_seq'::regclass),
  "school_id" int4 NOT NULL,
  "area" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now()
)
;
COMMENT ON COLUMN "public"."fc_area"."school_id" IS '学校id';
COMMENT ON COLUMN "public"."fc_area"."area" IS '校区名字';
COMMENT ON COLUMN "public"."fc_area"."create_time" IS '创建时间';

-- ----------------------------
-- Records of fc_area
-- ----------------------------
INSERT INTO "public"."fc_area" VALUES (1, 1, '无', '2021-05-06 16:37:35.136034');

-- ----------------------------
-- Table structure for fc_comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_comment";
CREATE TABLE "public"."fc_comment" (
  "id" int4 NOT NULL DEFAULT nextval('fc_comment_id_seq'::regclass),
  "user_id" int4 NOT NULL,
  "post_id" int4 NOT NULL,
  "replys" int4 NOT NULL DEFAULT 0,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now(),
  "flag" int2 NOT NULL DEFAULT 1,
  "likes" int4 NOT NULL DEFAULT 0,
  "top_order" int4 NOT NULL DEFAULT 0,
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."fc_comment"."user_id" IS '评论用户';
COMMENT ON COLUMN "public"."fc_comment"."post_id" IS '评论帖子';
COMMENT ON COLUMN "public"."fc_comment"."replys" IS '回复数';
COMMENT ON COLUMN "public"."fc_comment"."content" IS '评论内容';
COMMENT ON COLUMN "public"."fc_comment"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."fc_comment"."flag" IS '0:隐藏，1:展示';
COMMENT ON COLUMN "public"."fc_comment"."likes" IS '点赞数';
COMMENT ON COLUMN "public"."fc_comment"."top_order" IS '置顶顺序，越大排序越靠前';
COMMENT ON COLUMN "public"."fc_comment"."remark" IS '隐藏说明，用于违规提示';

-- ----------------------------
-- Records of fc_comment
-- ----------------------------
INSERT INTO "public"."fc_comment" VALUES (2, 7, 3, 0, '啊实打实的', '2021-05-14 16:00:55.622442', 1, 0, 0, NULL);
INSERT INTO "public"."fc_comment" VALUES (4, 7, 3, 2, 'nmka', '2021-05-14 16:07:41.790965', 1, 0, 0, NULL);

-- ----------------------------
-- Table structure for fc_like
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_like";
CREATE TABLE "public"."fc_like" (
  "id" int4 NOT NULL DEFAULT nextval('fc_like_id_seq'::regclass),
  "user_id" int4 NOT NULL,
  "post_id" int4 NOT NULL,
  "comment_id" int4,
  "reply_id" int4,
  "status" int2 NOT NULL DEFAULT 1
)
;
COMMENT ON COLUMN "public"."fc_like"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."fc_like"."post_id" IS '帖子id';
COMMENT ON COLUMN "public"."fc_like"."comment_id" IS '评论id';
COMMENT ON COLUMN "public"."fc_like"."reply_id" IS '回复id';
COMMENT ON COLUMN "public"."fc_like"."status" IS '点赞状态0--取消赞   1--有效赞';

-- ----------------------------
-- Records of fc_like
-- ----------------------------

-- ----------------------------
-- Table structure for fc_post
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_post";
CREATE TABLE "public"."fc_post" (
  "id" int4 NOT NULL DEFAULT nextval('fc_post_id_seq'::regclass),
  "user_id" int4 NOT NULL,
  "title" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "release_time" timestamp(6),
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "likes" int4 NOT NULL DEFAULT 0,
  "comments" int4 NOT NULL DEFAULT 0,
  "collects" int4 NOT NULL DEFAULT 0,
  "flag" int4 NOT NULL DEFAULT 1,
  "top_order" int4 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT now(),
  "location" varchar(255) COLLATE "pg_catalog"."default",
  "imgs" varchar(255) COLLATE "pg_catalog"."default",
  "coordinate" varchar(64) COLLATE "pg_catalog"."default",
  "browses" int4 NOT NULL DEFAULT 0
)
;
COMMENT ON COLUMN "public"."fc_post"."user_id" IS '发布者id';
COMMENT ON COLUMN "public"."fc_post"."title" IS '标题';
COMMENT ON COLUMN "public"."fc_post"."content" IS '内容';
COMMENT ON COLUMN "public"."fc_post"."state" IS '状态 draft：草稿，normal：发布，timing：定时发布';
COMMENT ON COLUMN "public"."fc_post"."release_time" IS '定时发布时间';
COMMENT ON COLUMN "public"."fc_post"."type" IS '帖子类型
buy：买
sell:卖
confess：表白
game: 游戏
other：其他';
COMMENT ON COLUMN "public"."fc_post"."likes" IS '点赞数';
COMMENT ON COLUMN "public"."fc_post"."comments" IS '评论数';
COMMENT ON COLUMN "public"."fc_post"."collects" IS '收藏数';
COMMENT ON COLUMN "public"."fc_post"."flag" IS '0：违规，1：有效';
COMMENT ON COLUMN "public"."fc_post"."top_order" IS '置顶顺序，越大排序越靠前';
COMMENT ON COLUMN "public"."fc_post"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."fc_post"."location" IS '位置';
COMMENT ON COLUMN "public"."fc_post"."imgs" IS '图片地址，空格分割';
COMMENT ON COLUMN "public"."fc_post"."coordinate" IS '经纬度坐标，逗号分割';
COMMENT ON COLUMN "public"."fc_post"."browses" IS '浏览数';

-- ----------------------------
-- Records of fc_post
-- ----------------------------
INSERT INTO "public"."fc_post" VALUES (3, 7, 'testTitle', 'content', 'NORMAL', NULL, 'buy', 0, 0, 0, 1, 0, '2021-05-14 10:38:05.880483', NULL, NULL, NULL, 0);
INSERT INTO "public"."fc_post" VALUES (2, 7, 'testTitle', 'content', 'NORMAL', NULL, 'buy', 0, 7, 0, 1, 0, '2021-05-14 10:33:41.888674', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for fc_reply
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_reply";
CREATE TABLE "public"."fc_reply" (
  "id" int4 NOT NULL DEFAULT nextval('fc_reply_id_seq'::regclass),
  "comment_id" int4 NOT NULL,
  "reply_id" int4,
  "reply_type" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" int4 NOT NULL,
  "to_uid" int4 NOT NULL,
  "likes" int4 NOT NULL DEFAULT 0,
  "create_time" timestamp(6) NOT NULL DEFAULT now(),
  "flag" int2 NOT NULL DEFAULT 1,
  "post_id" int4 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."fc_reply"."comment_id" IS '评论id';
COMMENT ON COLUMN "public"."fc_reply"."reply_id" IS '回复目标id';
COMMENT ON COLUMN "public"."fc_reply"."reply_type" IS '回复类型comment：回复的评论，reply：回复的回复';
COMMENT ON COLUMN "public"."fc_reply"."content" IS '回复内容';
COMMENT ON COLUMN "public"."fc_reply"."user_id" IS '回复的用户id';
COMMENT ON COLUMN "public"."fc_reply"."to_uid" IS '目标用户id';
COMMENT ON COLUMN "public"."fc_reply"."likes" IS '点赞数';
COMMENT ON COLUMN "public"."fc_reply"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."fc_reply"."flag" IS '0:隐藏，1:展示';
COMMENT ON COLUMN "public"."fc_reply"."post_id" IS '帖子id';
COMMENT ON COLUMN "public"."fc_reply"."remark" IS '隐藏说明，用于违规提示';

-- ----------------------------
-- Records of fc_reply
-- ----------------------------

-- ----------------------------
-- Table structure for fc_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_role";
CREATE TABLE "public"."fc_role" (
  "id" int4 NOT NULL DEFAULT nextval('fc_role_id_seq'::regclass),
  "name" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "name_zh" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."fc_role"."name" IS '角色名';
COMMENT ON COLUMN "public"."fc_role"."name_zh" IS '角色名中文';

-- ----------------------------
-- Records of fc_role
-- ----------------------------
INSERT INTO "public"."fc_role" VALUES (1, 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO "public"."fc_role" VALUES (2, 'ROLE_ADMIN', '管理员');
INSERT INTO "public"."fc_role" VALUES (3, 'ROLE_USER', '用户');

-- ----------------------------
-- Table structure for fc_school
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_school";
CREATE TABLE "public"."fc_school" (
  "id" int4 NOT NULL DEFAULT nextval('fc_school_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now()
)
;
COMMENT ON COLUMN "public"."fc_school"."name" IS '学校名';
COMMENT ON COLUMN "public"."fc_school"."create_time" IS '创建时间';

-- ----------------------------
-- Records of fc_school
-- ----------------------------
INSERT INTO "public"."fc_school" VALUES (1, '无学校', '2021-05-06 16:37:15.896839');

-- ----------------------------
-- Table structure for fc_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_user";
CREATE TABLE "public"."fc_user" (
  "id" int4 NOT NULL DEFAULT nextval('fc_user_id_seq'::regclass),
  "username" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "signature" varchar(255) COLLATE "pg_catalog"."default",
  "photo" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "sex" int2 NOT NULL DEFAULT 0,
  "exp" int4 NOT NULL DEFAULT 0,
  "score" int4 NOT NULL DEFAULT 0,
  "horoscope" varchar(8) COLLATE "pg_catalog"."default",
  "school_area_id" int4 NOT NULL,
  "grade" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "specialty" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL DEFAULT now()
)
;
COMMENT ON COLUMN "public"."fc_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."fc_user"."password" IS '密码';
COMMENT ON COLUMN "public"."fc_user"."signature" IS '个性签名';
COMMENT ON COLUMN "public"."fc_user"."photo" IS '头像url地址';
COMMENT ON COLUMN "public"."fc_user"."phone" IS '手机';
COMMENT ON COLUMN "public"."fc_user"."sex" IS '性别：0：未知，1：男，2：女';
COMMENT ON COLUMN "public"."fc_user"."exp" IS '经验';
COMMENT ON COLUMN "public"."fc_user"."score" IS '积分';
COMMENT ON COLUMN "public"."fc_user"."horoscope" IS '星座';
COMMENT ON COLUMN "public"."fc_user"."school_area_id" IS '学校区域id';
COMMENT ON COLUMN "public"."fc_user"."grade" IS '年纪';
COMMENT ON COLUMN "public"."fc_user"."specialty" IS '专业';
COMMENT ON COLUMN "public"."fc_user"."create_time" IS '创建时间';

-- ----------------------------
-- Records of fc_user
-- ----------------------------
INSERT INTO "public"."fc_user" VALUES (7, 'test', '$2a$10$huOW4zo4R9NrvkFApohxf.nfknGKhD90qflNMUrQ08idKLPFFua1i', NULL, NULL, NULL, 0, 0, 1, NULL, 1, '1', '未知', '2021-05-13 17:48:19.654484');

-- ----------------------------
-- Table structure for fc_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_user_role";
CREATE TABLE "public"."fc_user_role" (
  "id" int4 NOT NULL DEFAULT nextval('fc_user_role_id_seq'::regclass),
  "user_id" int4 NOT NULL,
  "role_id" int4 NOT NULL
)
;
COMMENT ON COLUMN "public"."fc_user_role"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."fc_user_role"."role_id" IS '角色id';

-- ----------------------------
-- Records of fc_user_role
-- ----------------------------
INSERT INTO "public"."fc_user_role" VALUES (4, 7, 3);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_area_id_seq"
OWNED BY "public"."fc_area"."id";
SELECT setval('"public"."fc_area_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_comment_id_seq"
OWNED BY "public"."fc_comment"."id";
SELECT setval('"public"."fc_comment_id_seq"', 5, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_like_id_seq"
OWNED BY "public"."fc_like"."id";
SELECT setval('"public"."fc_like_id_seq"', 3, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_post_id_seq"
OWNED BY "public"."fc_post"."id";
SELECT setval('"public"."fc_post_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_reply_id_seq"
OWNED BY "public"."fc_reply"."id";
SELECT setval('"public"."fc_reply_id_seq"', 6, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_role_id_seq"
OWNED BY "public"."fc_role"."id";
SELECT setval('"public"."fc_role_id_seq"', 5, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_school_id_seq"
OWNED BY "public"."fc_school"."id";
SELECT setval('"public"."fc_school_id_seq"', 3, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_user_id_seq"
OWNED BY "public"."fc_user"."id";
SELECT setval('"public"."fc_user_id_seq"', 8, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_user_role_id_seq"
OWNED BY "public"."fc_user_role"."id";
SELECT setval('"public"."fc_user_role_id_seq"', 5, true);

-- ----------------------------
-- Primary Key structure for table fc_area
-- ----------------------------
ALTER TABLE "public"."fc_area" ADD CONSTRAINT "fc_area_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_comment
-- ----------------------------
ALTER TABLE "public"."fc_comment" ADD CONSTRAINT "fc_comment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_like
-- ----------------------------
ALTER TABLE "public"."fc_like" ADD CONSTRAINT "fc_like_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_post
-- ----------------------------
ALTER TABLE "public"."fc_post" ADD CONSTRAINT "fc_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_reply
-- ----------------------------
ALTER TABLE "public"."fc_reply" ADD CONSTRAINT "fc_reply_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_role
-- ----------------------------
ALTER TABLE "public"."fc_role" ADD CONSTRAINT "fc_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_school
-- ----------------------------
ALTER TABLE "public"."fc_school" ADD CONSTRAINT "fc_school_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_user
-- ----------------------------
ALTER TABLE "public"."fc_user" ADD CONSTRAINT "fc_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table fc_user_role
-- ----------------------------
ALTER TABLE "public"."fc_user_role" ADD CONSTRAINT "fc_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table fc_area
-- ----------------------------
ALTER TABLE "public"."fc_area" ADD CONSTRAINT "fc_area_school_id_fkey" FOREIGN KEY ("school_id") REFERENCES "public"."fc_school" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_comment
-- ----------------------------
ALTER TABLE "public"."fc_comment" ADD CONSTRAINT "fc_comment_post_id_fkey" FOREIGN KEY ("post_id") REFERENCES "public"."fc_post" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_comment" ADD CONSTRAINT "fc_comment_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."fc_user" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_like
-- ----------------------------
ALTER TABLE "public"."fc_like" ADD CONSTRAINT "fc_like_comment_id_fkey" FOREIGN KEY ("comment_id") REFERENCES "public"."fc_comment" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_like" ADD CONSTRAINT "fc_like_post_id_fkey" FOREIGN KEY ("post_id") REFERENCES "public"."fc_post" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_like" ADD CONSTRAINT "fc_like_reply_id_fkey" FOREIGN KEY ("reply_id") REFERENCES "public"."fc_reply" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_like" ADD CONSTRAINT "fc_like_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."fc_user" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_post
-- ----------------------------
ALTER TABLE "public"."fc_post" ADD CONSTRAINT "fc_post_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."fc_user" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_reply
-- ----------------------------
ALTER TABLE "public"."fc_reply" ADD CONSTRAINT "fc_reply_comment_id_fkey" FOREIGN KEY ("comment_id") REFERENCES "public"."fc_comment" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_reply" ADD CONSTRAINT "fc_reply_reply_id_fkey" FOREIGN KEY ("reply_id") REFERENCES "public"."fc_reply" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_user
-- ----------------------------
ALTER TABLE "public"."fc_user" ADD CONSTRAINT "fc_user_school_area_id_fkey" FOREIGN KEY ("school_area_id") REFERENCES "public"."fc_area" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_user_role
-- ----------------------------
ALTER TABLE "public"."fc_user_role" ADD CONSTRAINT "fc_user_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."fc_role" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_user_role" ADD CONSTRAINT "fc_user_role_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."fc_user" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
