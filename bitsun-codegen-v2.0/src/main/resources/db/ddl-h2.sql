DROP table if EXISTS  "demo1";
CREATE TABLE "demo1" (
  "id" int8 NOT NULL,
  "local_date" date,
  "local_time" time(6),
  "local_date_time" timestamp(6),
  "retail_price" NUMERIC(18,3),
  "purchase_price" NUMERIC(18,3),
  "wholesale_price" NUMERIC(18,3),
  "remarks" varchar(255) ,
  "app_id" varchar(255) NOT NULL,
  "deleted" bool NOT NULL,
  "create_user" varchar(255) NOT NULL,
  "create_time" timestamp(0) NOT NULL,
  "modify_user" varchar(255) NOT NULL,
  "modify_time" timestamp(0) NOT NULL,
  CONSTRAINT "demo1_pkey" PRIMARY KEY ("id")
)
;

COMMENT ON COLUMN "demo1"."id" IS 'pk';

COMMENT ON COLUMN "demo1"."local_date" IS '日期';

COMMENT ON COLUMN "demo1"."local_time" IS '时间';

COMMENT ON COLUMN "demo1"."local_date_time" IS '日期时间';

COMMENT ON COLUMN "demo1"."retail_price" IS '零售价';

COMMENT ON COLUMN "demo1"."purchase_price" IS '进货价';

COMMENT ON COLUMN "demo1"."wholesale_price" IS '批发价';

COMMENT ON COLUMN "demo1"."remarks" IS '备注';

COMMENT ON COLUMN "demo1"."app_id" IS '应用ID';

COMMENT ON COLUMN "demo1"."deleted" IS '逻辑删除标志';

COMMENT ON COLUMN "demo1"."create_user" IS '创建人';

COMMENT ON COLUMN "demo1"."create_time" IS '创建时间';

COMMENT ON COLUMN "demo1"."modify_user" IS '修改人';

COMMENT ON COLUMN "demo1"."modify_time" IS '修改时间';

COMMENT ON TABLE "demo1" IS '示例表';