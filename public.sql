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

 Date: 23/04/2021 09:43:26
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

-- ----------------------------
-- Table structure for fc_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."fc_user";
CREATE TABLE "public"."fc_user" (
  "id" int4 NOT NULL DEFAULT nextval('fc_user_id_seq'::regclass),
  "username" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
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

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_area_id_seq"
OWNED BY "public"."fc_area"."id";
SELECT setval('"public"."fc_area_id_seq"', 3, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_role_id_seq"
OWNED BY "public"."fc_role"."id";
SELECT setval('"public"."fc_role_id_seq"', 4, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_school_id_seq"
OWNED BY "public"."fc_school"."id";
SELECT setval('"public"."fc_school_id_seq"', 3, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_user_id_seq"
OWNED BY "public"."fc_user"."id";
SELECT setval('"public"."fc_user_id_seq"', 3, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."fc_user_role_id_seq"
OWNED BY "public"."fc_user_role"."id";
SELECT setval('"public"."fc_user_role_id_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table fc_area
-- ----------------------------
ALTER TABLE "public"."fc_area" ADD CONSTRAINT "fc_area_pkey" PRIMARY KEY ("id");

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
-- Foreign Keys structure for table fc_user
-- ----------------------------
ALTER TABLE "public"."fc_user" ADD CONSTRAINT "fc_user_school_area_id_fkey" FOREIGN KEY ("school_area_id") REFERENCES "public"."fc_area" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table fc_user_role
-- ----------------------------
ALTER TABLE "public"."fc_user_role" ADD CONSTRAINT "fc_user_role_role_id_fkey" FOREIGN KEY ("role_id") REFERENCES "public"."fc_role" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE "public"."fc_user_role" ADD CONSTRAINT "fc_user_role_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."fc_user" ("id") ON DELETE CASCADE ON UPDATE CASCADE;
