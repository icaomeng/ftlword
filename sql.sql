/*
 Navicat Premium Data Transfer

 Source Server         : 本地人防
 Source Server Type    : PostgreSQL
 Source Server Version : 90507
 Source Host           : localhost:5432
 Source Catalog        : word
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90507
 File Encoding         : 65001

 Date: 25/03/2021 15:32:20
*/


-- ----------------------------
-- Table structure for t_company
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_company";
CREATE TABLE "public"."t_company" (
  "uuid" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default",
  "web_url" varchar(255) COLLATE "pg_catalog"."default",
  "create_user" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" varchar(30) COLLATE "pg_catalog"."default",
  "dist" varchar(255) COLLATE "pg_catalog"."default",
  "range" varchar(255) COLLATE "pg_catalog"."default",
  "about" varchar(10000) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_company"."uuid" IS '主键';
COMMENT ON COLUMN "public"."t_company"."name" IS '公司名称';
COMMENT ON COLUMN "public"."t_company"."web_url" IS '网址';
COMMENT ON COLUMN "public"."t_company"."create_user" IS '创始人';
COMMENT ON COLUMN "public"."t_company"."create_time" IS '成立时间';
COMMENT ON COLUMN "public"."t_company"."dist" IS '公司地址';
COMMENT ON COLUMN "public"."t_company"."range" IS '经营范围';
COMMENT ON COLUMN "public"."t_company"."about" IS '简介';

-- ----------------------------
-- Records of t_company
-- ----------------------------
INSERT INTO "public"."t_company" VALUES ('bac9b34e28a2440b8e1c7969b7a6d25a', '阿里巴巴网络技术有限公司', 'http://www.alibaba.com/', '马云', '1999年9月9日', '中国杭州', '电子商务、网上支付、B2B网上交易市场及云计算业务', '2019年9月1日，2019中国服务业企业500强榜单在济南发布，阿里巴巴集团控股有限公司排名第24位。 [6]  9月6日，阿里巴巴集团宣布20亿美元全资收购网易考拉，领投网易云音乐7亿美元融资。 [7]  10月，2019福布斯全球数字经济100强榜位列10位。 [8]  10月23日， 2019《财富》未来50强榜单公布，阿里巴巴集团排名第11。 [9]  “一带一路”中国企业100强榜单排名第5位。 [10]  11月16日，胡润研究院发布《2019胡润全球独角兽活跃投资机构百强榜》，阿里巴巴排名第7位。 [11]  12月，阿里巴巴集团入选2019中国品牌强国盛典榜样100品牌。 [12]');

-- ----------------------------
-- Table structure for t_company_attach
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_company_attach";
CREATE TABLE "public"."t_company_attach" (
  "uuid" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "attach_path" varchar(255) COLLATE "pg_catalog"."default",
  "com_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."t_company_attach"."uuid" IS '附件主键';
COMMENT ON COLUMN "public"."t_company_attach"."attach_path" IS '附件存储路径';
COMMENT ON COLUMN "public"."t_company_attach"."com_id" IS '公司id';

-- ----------------------------
-- Records of t_company_attach
-- ----------------------------
INSERT INTO "public"."t_company_attach" VALUES ('d0543fd2b1c246a7972c6be0eeac28ae', '/word/bac9b34e28a2440b8e1c7969b7a6d25a/b623e4f61d5f4c17b55046693f40b2f0/4.jpg', 'bac9b34e28a2440b8e1c7969b7a6d25a');
INSERT INTO "public"."t_company_attach" VALUES ('d1e1200228dc432f841113df265690dc', '/word/bac9b34e28a2440b8e1c7969b7a6d25a/e00dd17b6de84a63a0ca464080e59bed/3.jpg', 'bac9b34e28a2440b8e1c7969b7a6d25a');
INSERT INTO "public"."t_company_attach" VALUES ('16d96f475ae24eac9094a3faaaba1e3a', '/word/bac9b34e28a2440b8e1c7969b7a6d25a/04b5c390dd044857a0d0da00e7cdb89d/2.jpg', 'bac9b34e28a2440b8e1c7969b7a6d25a');
INSERT INTO "public"."t_company_attach" VALUES ('115d2b1839e14a139e40bf1bf450c898', '/word/bac9b34e28a2440b8e1c7969b7a6d25a/c1cb77d0d01c4516bfc44d7b414e43bb/1.jpg', 'bac9b34e28a2440b8e1c7969b7a6d25a');

-- ----------------------------
-- Primary Key structure for table t_company_attach
-- ----------------------------
ALTER TABLE "public"."t_company_attach" ADD CONSTRAINT "t_company_attach_pkey" PRIMARY KEY ("uuid");
